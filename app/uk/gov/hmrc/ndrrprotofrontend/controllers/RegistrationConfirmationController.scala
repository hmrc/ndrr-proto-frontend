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
import uk.gov.hmrc.ndrrprotofrontend.models.{Reference, SubmissionDetails, _}
import uk.gov.hmrc.ndrrprotofrontend.views.html.registrationConfirmationView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class RegistrationConfirmationController @Inject()(
                                              mcc: MessagesControllerComponents,
                                              registrationConfirmationView: registrationConfirmationView)
  extends FrontendController(mcc) {
  val show: Action[AnyContent] = Action.async { implicit request =>
    def testSubmitData(): SubmissionDetails = {
      SubmissionDetails(requestTitleKey = "submit.panel.registration.title",
        requestRefDescriptionKey = "submit.panel.registration.submissionID",
        userEmail = Some("olivia@thecornershop.co.uk."),
        reference = Reference("1ZDBB57"))
    }

    Future.successful(
      Ok(
        registrationConfirmationView(
          submissionDetails = testSubmitData()
        )
      )
    )
  }
}




