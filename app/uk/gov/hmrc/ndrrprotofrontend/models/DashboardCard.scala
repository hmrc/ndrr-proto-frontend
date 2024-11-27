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
import uk.gov.hmrc.ndrrprotofrontend.models.Link
import uk.gov.hmrc.govukfrontend.views.Aliases.{Actions, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Empty
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{ActionItem, CardTitle}
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

final case class DashboardCard(
                                titleKey: String,
                                captionKey:  Option[String] = None,
                                captionKey2: Option[String] = None,
                                tag: Option[String] = None,
                                links: Option[Seq[ActionItem]] = None
                              )(implicit messages: Messages)

object DashboardCard {

  def reportChangeCard()(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = "home.reportChangeCard.title",
    captionKey =  Some("home.reportChangeCard.caption"),
    captionKey2 =  Some("home.reportChangeCard.caption2"),
    links = Some(Seq(
      ActionItem(
        href       = "http://SomeLink1",
        attributes     = Map("id" -> "report-change-link1"),
        content = Text(Messages("home.reportChangeCard.link1")),
      ),ActionItem(
        href       = "http://SomeLink1",
        attributes     = Map("id" -> "report-change-link2"),
        content = Text(Messages("home.reportChangeCard.link2")),
      ))
    ))
  }

  def propertiesCard(link:Link, tag: Option[String] = None)(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = "home.propertiesCard.title",
    captionKey =  Some("home.propertiesCard.caption"),
    captionKey2 =  Some("home.propertiesCard.caption2"),
    tag = tag,
    links = Some(Seq(
      ActionItem(
        href       = link.url,
        attributes     = Map("id" -> "propertiesCard-addProperty"),
        content = Text(link.messageKey)
      ))
    ))
  }

  def testDataMessageCard()(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = YourMessages(hasMessages = true, unreadMessageCount = 1L).titleKey,
    captionKey =  Some("home.messagesCard.caption"),
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
  def testDataHelpCard()(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = "home.helpAndGuidanceCard.title",
    links = Some(Seq(
      ActionItem(
        href       = "http://SomeLink1",
        attributes     = Map("id" -> "direct-debit-link-both-primary"),
        content = Text(Messages("home.helpAndGuidanceCard.link1")),
      ),ActionItem(
        href       = "http://SomeLink2",
        attributes     = Map("id" -> "direct-debit-link-both-primary"),
        content = Text(Messages("home.helpAndGuidanceCard.link2")),
      ),ActionItem(
        href       = "http://SomeLink2",
        attributes     = Map("id" -> "direct-debit-link-both-primary"),
        content = Text(Messages("home.helpAndGuidanceCard.link3")),
      ),ActionItem(
        href       = "http://SomeLink2",
        attributes     = Map("id" -> "direct-debit-link-both-primary"),
        content = Text(Messages("home.helpAndGuidanceCard.link4")),
      ),ActionItem(
        href       = "http://SomeLink2",
        attributes     = Map("id" -> "direct-debit-link-both-primary"),
        content = Text(Messages("home.helpAndGuidanceCard.link5")),
      ))
    ))
  }

  def testData()(implicit messages: Messages): DashboardCard = { DashboardCard(
    titleKey = YourMessages(hasMessages = true, unreadMessageCount = 1L).titleKey,
    captionKey =  Some("home.messagesCard.caption"),
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
      captionKey = dashboardCard.captionKey match{
          case Some(caption) => Some(CardCaption(content = Text(Messages(caption))))
          case _ => None
        },
      captionKey2 = dashboardCard.captionKey2 match{
        case Some(caption2) => Some(CardCaption(content = Text(Messages(caption2))))
        case _ => None
      },
      tag = dashboardCard.tag match{
        case Some(tag) => Some(Tag(content = Text(Messages(tag))))
        case None => None
      },
      links = dashboardCard.links match {
        case Some(link) => Some(Actions(classes = "",items = link))
        case None => None
      }
    )
  }

}
