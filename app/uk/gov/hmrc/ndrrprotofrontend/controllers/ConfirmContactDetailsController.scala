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
import uk.gov.hmrc.ndrrprotofrontend.models.VoaSummaryListRow.buildSummaryList
import uk.gov.hmrc.ndrrprotofrontend.models.{EmailAddress, FullName, PhoneNumber, Postcode, VoaAddress, VoaBullet, VoaCard, VoaSummaryListRow}
import uk.gov.hmrc.ndrrprotofrontend.views.html.ConfirmContactDetailsView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.ndrrprotofrontend.models._

import javax.inject.Inject
import scala.concurrent.Future

class ConfirmContactDetailsController @Inject()(
                                                 mcc: MessagesControllerComponents,
                                                 view: ConfirmContactDetailsView
                                                 )
  extends FrontendController(mcc) {

  private def makeSummaryList(row: Seq[VoaSummaryListRow], card: Option[VoaCard] = None)(implicit messages: Messages): Aliases.SummaryList = buildSummaryList(row,card)
  val fullName: FullName = FullName("Olivia ",Some("Jane"), "Cunningham")
  val email: EmailAddress = EmailAddress("olivia@thecornershop.co.uk")
  val phoneNumber : PhoneNumber = PhoneNumber("07700900000")
  val address1 = VoaAddress(
    line1 = "5 Brixham Marina",
    line2 = Some("Berry Head Road"),
    town = "Brixham",
    county = Some("Devon"),
    postcode = Postcode("TQ59BW")
  )
  val row1 = VoaSummaryListRow("Contact name",Seq.empty,Some(Link(Call(method = "GET",url = "some-href"), linkId ="linkId", messageKey ="voa.contactInformation.enterContact")))


  def confirmYourContactDetails: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(view(makeSummaryList(Seq(row1)))))
  }





}
