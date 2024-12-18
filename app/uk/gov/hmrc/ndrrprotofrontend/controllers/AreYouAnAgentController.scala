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

import play.api.data.Form
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.ndrrprotofrontend.models.VoaRadios.buildRadios
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController
import uk.gov.hmrc.ndrrprotofrontend.views.html.AreYouAnAgentView
import uk.gov.hmrc.ndrrprotofrontend.models.enumsforforms.YesNoFormValue
import uk.gov.hmrc.ndrrprotofrontend.models.enumsforforms.YesNoFormValue.YesNoOptionsFromFormValue
import uk.gov.hmrc.ndrrprotofrontend.models.forms.AreYouAnAgentForm
import uk.gov.hmrc.ndrrprotofrontend.models.{ VoaRadioButtons, VoaRadioName, VoaRadios, YesNoOptions}

import javax.inject.Inject
import scala.concurrent.Future

class AreYouAnAgentController @Inject()(
                                               mcc: MessagesControllerComponents,
                                               areYouAnAgentView: AreYouAnAgentView)
  extends FrontendController(mcc) {

  private val button1: VoaRadioButtons = VoaRadioButtons("registration.areYouAnAgent.yes", YesNoOptions.Yes)
  private val button2: VoaRadioButtons = VoaRadioButtons("registration.areYouAnAgent.no", YesNoOptions.No)
  val radios: VoaRadios = VoaRadios(VoaRadioName("yesNoOption"), Seq(button1, button2))
  val form: Form[YesNoFormValue] = AreYouAnAgentForm.form

      val show: Action[AnyContent] = Action.async { implicit request =>
        Future.successful(
          Ok(
            areYouAnAgentView(form, buildRadios(form,radios))
          )
        )
      }

  def submit(): Action[AnyContent] = Action.async { implicit request =>
    form
      .bindFromRequest()
      .fold(
        formWithErrors => Future.successful(BadRequest(areYouAnAgentView(formWithErrors, buildRadios(form,radios)))),
        yesNoOption => {
          YesNoOptionsFromFormValue(yesNoOption) match {
            case _ => Future.successful(Redirect(routes.ConfirmContactDetailsController.show))
          }
        }
      )
  }
}