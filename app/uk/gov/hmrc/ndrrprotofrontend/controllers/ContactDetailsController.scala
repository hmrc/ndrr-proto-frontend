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
import uk.gov.hmrc.ndrrprotofrontend.models.{DashboardCard, Link, NavigationBarContent}
import uk.gov.hmrc.ndrrprotofrontend.views.html.ContactDetailsView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class ContactDetailsController @Inject()(
                                          mcc: MessagesControllerComponents,
                                          contactDetailsView: ContactDetailsView)
  extends FrontendController(mcc) {

  private def testNavBar: NavigationBarContent = NavigationBarContent(
    AccountHome = Some(Link(url = "/ndrr-proto-frontend/dashboard", Some("nav.home"))),
    NavigationButtons = Some(Seq(Link(url = "/ndrr-proto-frontend/dashboard", Some("nav.home")))))

  val show: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(
      Ok(
        contactDetailsView(navigationBarContent = testNavBar)
      )
    )
  }
}
