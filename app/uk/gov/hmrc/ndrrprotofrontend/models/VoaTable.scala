/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ndrrprotofrontend.models
import play.api.i18n._
import uk.gov.hmrc.govukfrontend.views.Aliases.Table
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.table.{HeadCell, TableRow}


final case class VoaTable(headings: Seq[MessageKey], rows: Seq[VoaRow]) {
  private def columnEntries(columnEntry: ColumnEntry): TableRow = {
    val text: Text = columnEntry match {
      case Reference(value) => Text(value)
      case address: VoaAddress => Text(address.toString)
      case TrnNumber(value) => Text(value.toString)
    }
    TableRow(content = text)
  }

  private def toHeadCell(tableHeading: MessageKey)(implicit messages: Messages): HeadCell = {
    val headingText = tableHeading.key
    HeadCell(content = Text(Messages(headingText)))
  }

  private def tableRows(entries: VoaRow) : Seq[TableRow] = {
    entries.columnEntries.map(columnEntries)
  }

  def buildTable()(implicit messages: Messages): Table = {
    Table(
     rows = rows.map(tableRows),
      head = headings match {
        case header if header.isEmpty => None
        case header => Some(header.map(toHeadCell))
      }
    )
  }
}