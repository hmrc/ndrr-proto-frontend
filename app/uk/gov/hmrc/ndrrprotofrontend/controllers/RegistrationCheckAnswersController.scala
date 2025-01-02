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

import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.ndrrprotofrontend.models.{ContactDetails, PhoneNumber, Postcode, Reference, SubmissionDetails, VoaAddress, _}
import uk.gov.hmrc.ndrrprotofrontend.views.html.RegistrationCheckAnswersView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.Future

class RegistrationCheckAnswersController @Inject()(
                                          mcc: MessagesControllerComponents,
                                          registrationCheckAnswersView: RegistrationCheckAnswersView,
                                          )
  extends FrontendController(mcc) with Common {
  def show(): Action[AnyContent] = Action.async { implicit request =>
    def testSubmitData(): SubmissionDetails = {
      SubmissionDetails(requestTitleKey = "submit.panel.registration.title",
        requestRefDescriptionKey = "submit.panel.registration.submissionID",
        userEmail = Some(email),
        reference = Reference("1ZDBB57"))
    }

    Future.successful(
      Ok(
        registrationCheckAnswersView(
          userAnswers:ContactDetails,
          contactSummaryTable =
            makeSummaryList(Seq(
              contactName(userAnswers.contactName),
              emailAddress(userAnswers.emailAddress),
              phoneNumber(userAnswers.phoneNumber.value),
              address(userAnswers.address.toString))),
          taxpayerReferenceSummary =
            makeSummaryListRow(Seq(
              utr(userAnswers.utr),
              ))
        )
      )
    )
  }
}
