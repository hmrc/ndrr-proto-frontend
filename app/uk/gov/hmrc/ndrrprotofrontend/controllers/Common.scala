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
import play.api.mvc.Call
import uk.gov.hmrc.govukfrontend.views.Aliases
import uk.gov.hmrc.govukfrontend.views.Aliases.Table
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.Radios
import uk.gov.hmrc.ndrrprotofrontend.models.{DateOfBirth, FullName, Link, MessageKey, PhoneNumber, Postcode, Reference, TrnNumber, VoaAddress, VoaCard, VoaRadioName, VoaRow, VoaSummaryListRow, VoaTable}
import uk.gov.hmrc.ndrrprotofrontend.models.VoaSummaryListRow.buildSummaryList

trait Common {

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

  def makeSummaryList(row: Seq[VoaSummaryListRow], card: Option[VoaCard] = None)(implicit messages: Messages): Aliases.SummaryList = buildSummaryList(row,card)

  val address1 = VoaAddress(
    line1 = "87a",
    line2 = Some("High St"),
    town = "Hythe",
    county = Some("Kent"),
    postcode = Postcode("HY270AA")
  )
  val fullName: FullName = FullName("Olivia Jane Cunningham")
  val email:String = "olivia@thecornershop.co.uk."
  val dateOfBirth: DateOfBirth = DateOfBirth(5,"January", 1978)
  val card = VoaCard("Contact Info",Some(Seq(link1,link2)))
  def createUtrRow(utr: String) = VoaSummaryListRow("Self Assessment Unique Taxpayer Reference",Seq(utr),None)
  private lazy val link1 = Link(Call(method = "GET",url = "some-href"), linkId =  "linkId", messageKey ="voa.contactInformation.enterContact")
  private lazy val link2: Link = Link(Call(method = "GET",url = "some-href"), linkId =  "linkId", messageKey ="voa.contactInformation.enterContact")
  val mobileNumber: PhoneNumber = PhoneNumber("07943009506")
  def makeAVisibleTable()(implicit messages: Messages): Table = createVoaTable().buildTable()
  def makeSummaryListRow(row: Seq[VoaSummaryListRow])(implicit messages: Messages): Aliases.SummaryList = buildSummaryList(row,None)
  def contactName (name: String)    =  VoaSummaryListRow("registration.checkAnswers.contactDetails.table.name",Seq(name),Some(Link(Call(method = "GET",url = routes.ContactNameController.onPageLoad.url),messageKey = "voa.change.link", linkId = "name-Id")))
  def emailAddress(email:String)    =  VoaSummaryListRow("registration.checkAnswers.contactDetails.table.emailAddress",Seq(email),Some(Link(Call(method = "GET",url = uk.gov.hmrc.ndrrprotofrontend.controllers.routes.EmailController.onPageLoad.url),messageKey = "voa.change.link", linkId = "email-Id")))
  def phoneNumber (number: String)  =  VoaSummaryListRow("registration.checkAnswers.contactDetails.table.phoneNumber",Seq(number),Some(Link(Call(method = "GET",url = uk.gov.hmrc.ndrrprotofrontend.controllers.routes.PhoneNumberController.onPageLoad.url),messageKey = "voa.change.link", linkId = "phone-number-Id")))
  def address     (address: String) =  VoaSummaryListRow("registration.checkAnswers.contactDetails.table.address",Seq(address),Some(Link(Call(method = "GET",url = uk.gov.hmrc.ndrrprotofrontend.controllers.addressLookupFrontend.routes.AddressLookupController.startAddressLookup().url),messageKey = "voa.change.link", linkId = "address-Id")))
  def utr         (utr: String)     =  VoaSummaryListRow("registration.checkAnswers.taxReferenceNumber.table",Seq(utr),Some(Link(Call(method = "GET",url = "controllers.routes.registration.email.url"),messageKey = "voa.change.link", linkId = "utr-Id")))
}
