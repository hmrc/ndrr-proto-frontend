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
import uk.gov.hmrc.ndrrprotofrontend.models.{Enumerable, UTROptions, UniqueTaxReferenceOption, VoaRadioButtons, VoaRadioName, VoaRadios}
import uk.gov.hmrc.ndrrprotofrontend.views.html.ConfirmUTRView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.Future

class ConfirmUTRController @Inject()(mcc: MessagesControllerComponents, view: ConfirmUTRView) extends FrontendController(mcc) with Common {

  //TODO leave this in until we find out how we can make it more generic
  private val button1: VoaRadioButtons = VoaRadioButtons("register.confirmUTRDetails.radioButtonOne","radioButtonOne")
  private val button2: VoaRadioButtons = VoaRadioButtons("register.confirmUTRDetails.radioButtonTwo","radioButtonTwo")
  private val button3: VoaRadioButtons = VoaRadioButtons("register.confirmUTRDetails.radioButtonThree","radioButtonThree")
  val radios: VoaRadios = VoaRadios(VoaRadioName("utrButtons"),Seq(button1,button2, button3))

  def show()(): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(view(UniqueTaxReferenceOption.form(),makeSummaryList(Seq(createUtrRow("******890"))))))
  }

  def submit(): Action[AnyContent] =
    Action.async { implicit request =>
      UniqueTaxReferenceOption.form()
        .bindFromRequest()
        .fold(
          formWithErrors => Future.successful(BadRequest(view(formWithErrors,makeSummaryList(Seq(createUtrRow("******890")))))),
          utrOption =>  {
            utrOption.value match {
              case Some(provideUtr) if provideUtr == UTROptions.PROVIDE_UTR => Future.successful(Redirect(routes.RegistrationCheckAnswersController.show.url))
              case Some(provideNino) if provideNino == UTROptions.PROVIDE_NINO => Future.successful(Redirect(routes.NationalInsuranceNumberController.onPageLoad.url))
              case Some(noUtr) if noUtr == UTROptions.NO_UTR => Future.successful(Redirect(routes.RegistrationCheckAnswersController.show.url))
              case _ => Future.successful(Redirect(routes.RegistrationCheckAnswersController.show.url))
            }
          }
        )

    }



}
