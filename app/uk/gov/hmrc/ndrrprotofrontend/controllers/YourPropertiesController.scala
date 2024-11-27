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
import play.api.mvc.{Action, AnyContent, Call, MessagesControllerComponents}
import uk.gov.hmrc.govukfrontend.views.Aliases
import uk.gov.hmrc.govukfrontend.views.Aliases._
import uk.gov.hmrc.ndrrprotofrontend.models.VoaSummaryListRow.buildSummaryList
import uk.gov.hmrc.ndrrprotofrontend.models._
import uk.gov.hmrc.ndrrprotofrontend.views.html.YourPropertiesView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class YourPropertiesController @Inject()(
  mcc: MessagesControllerComponents,
  yourPropertiesView: YourPropertiesView)
    extends FrontendController(mcc) {

  def yourProperties: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(yourPropertiesView(makeAVisibleTable(), makeSummaryList(Seq(row1,row2,row3,row4), Some(card)))))
  }

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

  private def makeSummaryList(row: Seq[VoaSummaryListRow], card: Option[VoaCard] = None)(implicit messages: Messages): Aliases.SummaryList = buildSummaryList(row,card)
  val address1 = VoaAddress(
    line1 = "87a",
    line2 = Some("High St"),
    town = "Hythe",
    county = Some("Kent"),
    postcode = Postcode("HY270AA")
  )
  val fullName: FullName = FullName("Sarah",None,"Philips")
  val dateOfBirth: DateOfBirth = DateOfBirth(5,"January", 1978)
  val row1: VoaSummaryListRow = VoaSummaryListRow("voa.name.title",Seq(fullName.toString),Some(Link(Call("GET", "some-href"), "linkId", "voa.change.link")))
  val row2 = VoaSummaryListRow("voa.dob.title",Seq(dateOfBirth.toString),Some(Link(Call("GET", "some-href"), "linkId", "voa.change.link")))
  val row3 = VoaSummaryListRow("voa.address.title",Seq(address1.line1,address1.town,address1.postcode.toString),Some(Link(Call("GET", "some-href"), "linkId", "voa.change.link")))
  val row4 = VoaSummaryListRow("voa.contactInformation.title",Seq.empty,Some(Link(Call("GET", "some-href"), "linkId", "voa.contactInformation.enterContact")))
  val card = VoaCard("Contact Info",Some(Seq(link1,link2)))
  private lazy val link1 = Link(Call("GET", "some-href"), "linkId", "voa.contactInformation.enterContact")
  private lazy val link2: Link = Link(Call("GET", "some-href"), "linkId", "voa.contactInformation.enterContact")
}
