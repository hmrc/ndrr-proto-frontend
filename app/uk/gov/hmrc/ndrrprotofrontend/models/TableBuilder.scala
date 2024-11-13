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

import uk.gov.hmrc.govukfrontend.views.Aliases.{HeadCell, Table, TableRow, Text}

final case class TableBuilder(
                               tableRows: Seq[Seq[TableRow]],
                               headCells: Option[Seq[HeadCell]],
                               caption: Option[String] = None,
                               captionClass: String = "",
                               firstCellHeader: Boolean = false
                             )

object TableBuilder {

  def buildTable(tableBuilder: TableBuilder): Table = {
    Table(
      rows = tableBuilder.tableRows,
      head = tableBuilder.headCells,
      caption = tableBuilder.caption,
      captionClasses = tableBuilder.captionClass,
      firstCellIsHeader = tableBuilder.firstCellHeader
    )
  }

  def createRows(data: Seq[Seq[String]]): Seq[Seq[TableRow]] =
    data.map(_.map(cell => TableRow(content = Text(cell))))

  def createHeadCells(headers: Seq[String]): Option[Seq[HeadCell]] =
    Some(headers.map(header => HeadCell(content = Text(header))))
}
