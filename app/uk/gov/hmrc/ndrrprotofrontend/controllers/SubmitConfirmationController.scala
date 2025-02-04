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
import uk.gov.hmrc.ndrrprotofrontend.models.NavBarPageContents.CreateNavBar
import uk.gov.hmrc.ndrrprotofrontend.models.{Link, Reference, SubmissionDetails, _}
import uk.gov.hmrc.ndrrprotofrontend.views.html.SubmitConfirmation
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

  @Singleton
class SubmitConfirmationController @Inject()(
                                             mcc: MessagesControllerComponents,
                                             submitConfirmation: SubmitConfirmation)
extends FrontendController(mcc) with Common {

  val show: Action[AnyContent] = Action.async { implicit request =>
    def testSubmitData(): SubmissionDetails  = {
      SubmissionDetails(requestTitleKey = "submit.panel.remove.title",
      requestRefDescriptionKey = "submissionID",
      reference = Reference("PL1ZDBB57"))
    }
    Future.successful(
      Ok(
          submitConfirmation(
            navigationBarContent = CreateNavBar(contents = NavBarContents(homePage = Some(true), profileAndSettingsPage=  Some(false), signOutPage = Some(true)), currentPage = (NavBarCurrentPage(homePage = true)), notifications = Some(1)),
            submissionDetails = testSubmitData,
          propertiesTable = makeAVisibleTable()
        )
      )
    )
  }
}


