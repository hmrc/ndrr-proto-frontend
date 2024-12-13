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
import uk.gov.hmrc.ndrrprotofrontend.models.enumsforforms.UtrOptionFormValue
import uk.gov.hmrc.ndrrprotofrontend.models.enumsforforms.UtrOptionFormValue.UTROptionsFromFormValue
import uk.gov.hmrc.ndrrprotofrontend.models.forms.UniqueTaxReferenceForm
import uk.gov.hmrc.ndrrprotofrontend.models.{Enumerable, UTROptions, VoaRadioButtons, VoaRadioName, VoaRadios}
import uk.gov.hmrc.ndrrprotofrontend.views.html.ConfirmUTRView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.Future

class ConfirmUTRController @Inject()(mcc: MessagesControllerComponents, view: ConfirmUTRView) extends FrontendController(mcc) with Common {

  //TODO leave this in until we find out how we can make it more generic
  private val button1: VoaRadioButtons = VoaRadioButtons("selectChange.provideUtr", UTROptions.ProvideUTR)
  private val button2: VoaRadioButtons = VoaRadioButtons("selectChange.provideNino", UTROptions.ProvideUTRLater)
  private val button3: VoaRadioButtons = VoaRadioButtons("selectChange.provideUtrLater", UTROptions.ProvideNino)
  val radios: VoaRadios = VoaRadios(VoaRadioName("utrOption"), Seq(button1, button2, button3))
  val form: Form[UtrOptionFormValue] = UniqueTaxReferenceForm.form


  def show(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(view(form, makeSummaryList(Seq(createUtrRow("******890"))),  buildRadios(form,radios))))
  }

  def submit(): Action[AnyContent] = Action.async { implicit request =>
     form
       .bindFromRequest()
       .fold(
         formWithErrors => Future.successful(BadRequest(view(formWithErrors, makeSummaryList(Seq(createUtrRow("******890"))),buildRadios(form,radios)))),
         utrOption => {
           UTROptionsFromFormValue(utrOption) match {
             case UTROptions.ProvideUTR => Future.successful(Redirect(routes.RegistrationCheckAnswersController.show))
             case UTROptions.ProvideNino => Future.successful(Redirect(routes.NationalInsuranceNumberController.onPageLoad))
             case UTROptions.ProvideUTRLater => Future.successful(Redirect(routes.RegistrationCheckAnswersController.show))
           }
         }
       )
   }
}
