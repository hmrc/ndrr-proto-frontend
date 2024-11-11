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

package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.{Text, Actions}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{ActionItem, CardTitle}

final case class DashboardCard(
                                titleKey: String,
                                caption: Option[String],
                                links: Option[Seq[ActionItem]]
                              )(implicit messages: Messages)

object DashboardCard {

  def testData()(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = YourMessages(hasMessages = true, unreadMessageCount = 1L).titleKey,
    caption =  Some("home.messagesCard.caption"),
    links = Some(Seq(
      ActionItem(
      href       = "http://SomeLink1",
      attributes     = Map("id" -> "direct-debit-link-both-primary"),
      content = Text(Messages("home.messagesCard.single")),
    ),ActionItem(
      href       = "http://SomeLink2",
      attributes     = Map("id" -> "direct-debit-link-both-primary"),
      content = Text(Messages("home.messagesCard.viewAllMessages")),
    ))
    ))
  }

  def card(dashboardCard: DashboardCard)(implicit messages: Messages): Card = {
    Card(
      titleKey = Some(CardTitle(content = Text(Messages(dashboardCard.titleKey)))),
      captionKey = dashboardCard.caption match{
          case Some(caption) => Some(CardCaption(content = Text(Messages(caption))))
          case _ => None
        },
      links = dashboardCard.links match {
        case Some(link) => Some(Actions(classes = "",items = link))
        case None => None
      }
    )
  }

}
