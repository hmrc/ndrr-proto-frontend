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
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.govukfrontend.views.Aliases._
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
    Future.successful(Ok(yourPropertiesView(makeAVisibleTable())))
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

}
