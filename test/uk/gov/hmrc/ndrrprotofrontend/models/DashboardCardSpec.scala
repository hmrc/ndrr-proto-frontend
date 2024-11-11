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

import play.api.i18n.{Messages, MessagesApi}
import play.api.mvc.{AnyContentAsEmpty, Cookie}
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.views.Aliases.{Actions, CardTitle, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.ActionItem
import uk.gov.hmrc.ndrrprotofrontend.testSupport.BaseSpec

class DashboardCardSpec extends BaseSpec {
  def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/dashboard")
  val fakeGetRequestInWelsh: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/dashboard").withCookies(Cookie("PLAY_LANG", "cy"))

  lazy val cardTittleKey = "home.messagesCard.none"
  lazy val cardCaption = "home.messagesCard.caption"

  "Card" - {
    "Given a Title key will generate a card with a title" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, None, None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(titleKey = Some(CardTitle(content = Text("You have no new messages"))))
    }
    "Given a Title key and Caption Key will generate a card with a title and caption" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, Some(cardCaption), None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey = Some(CardTitle(content = Text("You have no new messages"))),
        captionKey =Some(CardCaption(content = Text("Read secure messages from us."))),
        None)
    }
    "Given a Title Key, Caption Key and Action generate a card with a title, caption and action" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, Some(cardCaption),
        Some(Seq(
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
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey =  Some(CardTitle(content = Text("You have no new messages"))),
        captionKey =  Some(CardCaption(content = Text("Read secure messages from us."))),
        links = Some(Actions(classes = "", items = Seq(
          ActionItem(
            href       = "http://SomeLink1",
            attributes     = Map("id" -> "direct-debit-link-both-primary"),
            content = Text(Messages("You have {0} new message")),
          ),ActionItem(
            href       = "http://SomeLink2",
            attributes     = Map("id" -> "direct-debit-link-both-primary"),
            content = Text(Messages("Go to your messages")),
          )
        ))))
    }

    "Given a Title key will generate a card with a [WELSH] title" in{
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, None, None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(titleKey = Some(CardTitle(content = Text("You have no new messages [WELSH]"))))
    }
    "Given a Title key and Caption Key will generate a card with a [WELSH] title and caption" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, Some(cardCaption), None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey = Some(CardTitle(content = Text("You have no new messages [WELSH]"))),
        captionKey =Some(CardCaption(content = Text("Read secure messages from us. [WELSH]"))),
        None)
    }
    "Given a Title Key, Caption Key and Action generate a [WELSH] card with a title, caption and action" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard = DashboardCard(cardTittleKey, Some(cardCaption),
        Some(Seq(
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
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey =  Some(CardTitle(content = Text("You have no new messages [WELSH]"))),
        captionKey =  Some(CardCaption(content = Text("Read secure messages from us. [WELSH]"))),
        links = Some(Actions(classes = "", items = Seq(
          ActionItem(
            href       = "http://SomeLink1",
            attributes     = Map("id" -> "direct-debit-link-both-primary"),
            content = Text(Messages("You have {0} new message [WELSH]")),
          ),ActionItem(
            href       = "http://SomeLink2",
            attributes     = Map("id" -> "direct-debit-link-both-primary"),
            content = Text(Messages("Go to your messages [WELSH]")),
          )
        ))))
    }
  }
}

