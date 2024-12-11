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
import uk.gov.hmrc.ndrrprotofrontend.views.html.ConfirmContactDetailsView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.ndrrprotofrontend.models.{EmailAddress, FullName, PhoneNumber, Postcode, VoaAddress, VoaBullet, VoaCard, VoaSummaryListRow, _}

import javax.inject.Inject
import scala.concurrent.Future

class ConfirmContactDetailsController @Inject()(
                                                 mcc: MessagesControllerComponents,
                                                 view: ConfirmContactDetailsView
                                               )
  extends FrontendController(mcc) with Common {

  def confirmYourContactDetails: Action[AnyContent] = Action.async { implicit request =>

    Future.successful(Ok(view(makeSummaryList(Seq(
      contactName(fullName.value),
      emailAddress(email), phoneNumber(mobileNumber.value), address(address1.toString))),fullName.value)))
  }





}
