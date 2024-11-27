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

import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.views.Aliases.TableRow
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.table.{HeadCell, Table}
import uk.gov.hmrc.ndrrprotofrontend.BaseSpec



class VoaTableSpec extends BaseSpec{
  def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/your-properties")
  "Voa table" - {
    "will generate a minimum table from a minimum VoaTable" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)

      val voaTable : VoaTable = VoaTable(
        Seq.empty, Seq.empty
      )

      voaTable.buildTable() shouldBe  Table(List(), None, None, "", firstCellIsHeader = false, "", Map())
    }
    "will generate a minimum table with a header from the english messages if the key is set in the voa table" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val voaTable : VoaTable = VoaTable(
        Seq(MessageKey("voa.address.title")), Seq.empty
      )
      voaTable.buildTable() shouldBe Table(Seq.empty, head = Some(Seq(HeadCell(content = Text("Address")))))

    }

    "will generate a Table with a header and row value if set in the voa table" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val address0 = VoaAddress(
        line1 = "99",
        line2 = Some("Wibble Rd"),
        town = "Worthing",
        county = Some("West Sussex"),
        postcode = Postcode("BN110AA"))
      val tableHeading = Seq(MessageKey("voa.address.title"))
      val row0 = VoaRow(Seq(address0))
      val voaTable: VoaTable = VoaTable(
        headings = tableHeading, rows = Seq(row0)
      )
      voaTable.buildTable() shouldBe Table(Seq(Seq(TableRow(content = Text("99, Wibble Rd, Worthing, West Sussex, BN110AA")))), head = Some(Seq(HeadCell(content = Text("Address")))))
    }

    "will generate a Table with two headers and two row values if set in the voa table" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val address0 = VoaAddress(
        line1 = "99",
        line2 = Some("Wibble Rd"),
        town = "Worthing",
        county = Some("West Sussex"),
        postcode = Postcode("BN110AA"))

      val reference0 = Reference("987765JK99")

      val address1 = VoaAddress(
        line1 = "87a",
        line2 = Some("High St"),
        town = "Hythe",
        county = Some("Kent"),
        postcode = Postcode("HY270AA")
      )

      val reference1 = Reference("WillIAm")
      val tableHeadings = Seq(MessageKey("voa.address.title"), MessageKey("voa.reference.title"))
      val row0 = VoaRow(Seq(address0, reference0))
      val row1 = VoaRow(Seq(address1, reference1))
      val voaTable: VoaTable = VoaTable(
        headings = tableHeadings, rows = Seq(row0, row1)
      )
      voaTable.buildTable() shouldBe Table(Seq(Seq(TableRow(Text("99, Wibble Rd, Worthing, West Sussex, BN110AA")), TableRow(Text("987765JK99"))),
        Seq(TableRow(Text("87a, High St, Hythe, Kent, HY270AA")),
          TableRow(Text("WillIAm")))),
        Some(Seq(HeadCell(Text("Address")),
          HeadCell(Text("Reference")))))

    }

  }

}
