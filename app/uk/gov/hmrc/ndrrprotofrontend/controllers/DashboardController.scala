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
import uk.gov.hmrc.auth.delegation.Link
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.ndrrprotofrontend.models.{Card, DashboardCard}
import uk.gov.hmrc.ndrrprotofrontend.views.html.DashboardView
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

  @Singleton
  class DashboardController @Inject()(
                                       mcc: MessagesControllerComponents,
                                       dashboardView: DashboardView)
    extends FrontendController(mcc) {

    lazy val testUser: String = "Jake Reid"

    private def hasPropertyCheck(userProperty: Option[Int])(implicit messages: Messages):Seq[Card] = {
      userProperty match {
        case Some(properties) => Seq(
          DashboardCard.card(DashboardCard.propertiesCard(
            link = Link(
              text = Some("home.propertiesCard.manageProperties"),
              url = "/ndrr-proto-frontend/what-you-will-need"))),
          DashboardCard.card(DashboardCard.reportChangeCard()),
        )
        case _ => Seq(
          DashboardCard.card(
            DashboardCard.propertiesCard(
              link = Link(
                text = Some("home.propertiesCard.addProperty"),
                url = "/ndrr-proto-frontend/what-you-will-need"),
                tag = Some("home.propertiesCard.tag"))
          )
        )
      }
    }

    val show: Action[AnyContent] = Action.async { implicit request =>
      Future.successful(
        Ok(
          dashboardView(
            user = testUser,
            cards = hasPropertyCheck(Some(1))
          )
        )
      )
    }
}
