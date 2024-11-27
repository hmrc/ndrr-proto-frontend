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

package uk.gov.hmrc.ndrrprotofrontend.controllers

import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases
import uk.gov.hmrc.govukfrontend.views.Aliases.Table
import uk.gov.hmrc.ndrrprotofrontend.models.VoaSummaryListRow.buildSummaryListRow
import uk.gov.hmrc.ndrrprotofrontend.models.{DateOfBirth, FullName, Link, MessageKey, Postcode, Reference, TrnNumber, VoaAddress, VoaRow, VoaSummaryListRow, VoaTable}

trait Common {
  def makeAVisibleTable()(implicit messages: Messages): Table = createVoaTable().buildTable()

  def createVoaTable(): VoaTable = {
    val headers = Seq(MessageKey("voa.address.title"), MessageKey("voa.reference.title"), MessageKey("voa.trn.title"))

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

    val row0 = VoaRow(Seq(address0, reference0, TrnNumber(75)))
    val row1 = VoaRow(Seq(address1, reference1, TrnNumber(75)))

    VoaTable(headers, Seq(row0, row1))

  }

  def makeSummaryListRow(row: Seq[VoaSummaryListRow])(implicit messages: Messages): Seq[Aliases.SummaryListRow] = row.map(rows => buildSummaryListRow(rows))

  val address1 = VoaAddress(
    line1 = "87a",
    line2 = Some("High St"),
    town = "Hythe",
    county = Some("Kent"),
    postcode = Postcode("HY270AA")
  )
  val email:String = "olivia@thecornershop.co.uk."
  val fullName: FullName = FullName("Sarah","Philips")
  val dateOfBirth: DateOfBirth = DateOfBirth(5,"January", 1978)

  def contactName(name: String) =   VoaSummaryListRow("registration.checkAnswers.contactDetails.table.name",Seq(name),Some(Link(url ="some-href",messageKey = "voa.change.link", linkId = "name-Id")))
  def emailAddress(email:String) =  VoaSummaryListRow("registration.checkAnswers.contactDetails.table.emailAddress",Seq(email),Some(Link(url ="/ndrr-proto-frontend/registration/email",messageKey = "voa.change.link", linkId = "email-Id")))
  def phoneNumber(number: String) =   VoaSummaryListRow("registration.checkAnswers.contactDetails.table.phoneNumber",Seq(number),Some(Link(url ="/ndrr-proto-frontend/registration/phone-number",messageKey = "voa.change.link", linkId = "phone-number-Id")))
  def address(address: String) =       VoaSummaryListRow("registration.checkAnswers.contactDetails.table.address",Seq(address),Some(Link(url ="/ndrr-proto-frontend/registration/address",messageKey = "voa.change.link", linkId = "address-Id")))
  def utr(utr: String) =       VoaSummaryListRow("registration.checkAnswers.taxReferenceNumber.table",Seq(utr),Some(Link(url ="some-href",messageKey = "voa.change.link", linkId = "utr-Id")))

  val row1 = VoaSummaryListRow("voa.name.title",Seq(fullName.toString),Some(Link(url ="some-href",messageKey = "nav.messages", linkId = "linkId")))
  val row2 = VoaSummaryListRow("voa.dob.title",Seq(dateOfBirth.toString),Some(Link(url ="some-href",messageKey = "nav.messages", linkId = "linkId")))
  val row3 = VoaSummaryListRow("voa.address.title",Seq(address1.line1,address1.town,address1.postcode.toString),Some(Link(url ="some-href",messageKey = "nav.messages", linkId = "linkId")))
  val row4 = VoaSummaryListRow("voa.contactInformation.title",Seq.empty,Some(Link(url ="some-href",messageKey = "nav.messages", linkId = "linkId")))
}
