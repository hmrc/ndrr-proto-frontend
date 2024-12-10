/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ndrrprotofrontend.controllers.addressLookupFrontend

import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.ndrrprotofrontend.models._
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendBaseController

import javax.inject.Inject
import scala.annotation.unused
import scala.concurrent.ExecutionContext

class RampOffController @Inject()(
                                   addressLookupController: AddressLookupController,
                                   val controllerComponents: MessagesControllerComponents)(implicit val ex: ExecutionContext) extends FrontendBaseController {

  def contactAddressOffRamp(@unused ngrId: String, alfId: String, mode: Mode = NormalMode): Action[AnyContent] = Action.async {
    implicit request =>
      for {
        alfResponse <- addressLookupController.getAddress(alfId)
        ukAddress = addressLookupController.addressChecker(alfResponse.address, alfId)
        optTradingName = alfResponse.address.organisation
      } yield {
        val redirectUrl = if (mode == NormalMode) {
          uk.gov.hmrc.ndrrprotofrontend.controllers.routes.RegistrationCheckAnswersController.show
        } else {
          uk.gov.hmrc.ndrrprotofrontend.controllers.routes.RegistrationCheckAnswersController.show
        }
        Redirect(redirectUrl)
      }
  }
}
