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
import uk.gov.hmrc.ndrrprotofrontend.models.Email
import uk.gov.hmrc.ndrrprotofrontend.models.Email.form
import uk.gov.hmrc.ndrrprotofrontend.views.html.EmailView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.Future

class EmailController @Inject()(
                                       mcc: MessagesControllerComponents,
                                       emailView: EmailView)
  extends FrontendController(mcc) {

  def onPageLoad: Action[AnyContent] = Action { implicit request =>
      Ok(emailView(form))
  }

  def submit(): Action[AnyContent] =
    Action.async { implicit request =>
      Email.form()
        .bindFromRequest()
        .fold(
          formWithErrors => Future.successful(BadRequest(emailView(formWithErrors))),
          email => {
            Future.successful(Redirect(routes.RegistrationCheckAnswersController.show))
          }
        )
    }
}
