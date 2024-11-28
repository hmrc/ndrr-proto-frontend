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
import play.api.mvc.{AnyContentAsEmpty, Call, Cookie}
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.views.Aliases.{CardTitle, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.ActionItem
import uk.gov.hmrc.ndrrprotofrontend.testSupport.BaseSpec
import uk.gov.hmrc.ndrrprotofrontend.models._

class DashboardCardSpec extends BaseSpec {
  def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/dashboard")
  val fakeGetRequestInWelsh: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/dashboard").withCookies(Cookie("PLAY_LANG", "cy"))

  lazy val cardTittleKey = "home.propertiesCard.title"
  lazy val cardCaptionKey = "home.propertiesCard.caption"
  lazy val cardCaption2Key = "home.propertiesCard.caption2"
  lazy val cardLinkMessage1 = "home.propertiesCard.addProperty"
  lazy val cardLinkMessage2 = "home.propertiesCard.addProperty"

  lazy val cardTittleKeyExpected = "Properties"
  lazy val cardCaptionKeyExpected = "Add and manage properties you have a connection with."
  lazy val cardCaption2KeyExpected = "You must tell us within 60 days of becoming the ratepayer. Do this by adding the property to your account."
  lazy val cardLinkMessage1Expected = "Add a property"
  lazy val cardLinkMessage2Expected = "Add a property"

  lazy val cardTittleKeyExpectedWelsh = "Properties[WELSH]"
  lazy val cardCaptionKeyExpectedWelsh = "Add and manage properties you have a connection with.[WELSH]"
  lazy val cardCaption2KeyExpectedWelsh = "You must tell us within 60 days of becoming the ratepayer. Do this by adding the property to your account.[WELSH]"
  lazy val cardLinkMessage1ExpectedWelsh = "Add a property[WELSH]"
  lazy val cardLinkMessage2ExpectedWelsh = "Add a property[WELSH]"

  "Card" - {
    "Given a Title key will generate a card with a title" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard =
        DashboardCard(
        titleKey = cardTittleKey,
        captionKey = None,
        links = None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(titleKey = Some(CardTitle(content = Text(cardTittleKeyExpected))))
    }
    "Given a Title key and Caption Key will generate a card with a title and caption" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard =
        DashboardCard(
          titleKey =cardTittleKey,
          captionKey = Some(cardCaptionKey),
          links = None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey = Some(CardTitle(content = Text(cardTittleKeyExpected))),
        captionKey =Some(CardCaption(content = Text(cardCaptionKeyExpected))),
        None)
    }
    "Given a Title Key, Caption Key and Action generate a card with a title, caption and action" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard =
        DashboardCard(
          titleKey = cardTittleKey,
          captionKey = Some(cardCaptionKey),
          links =
        Some(Seq(
          Link(
            href       = Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage1,
        ),Link(
            href       = Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage2,
        ))
      ))
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey =  Some(CardTitle(content = Text(cardTittleKeyExpected))),
        captionKey =  Some(CardCaption(content = Text(cardCaptionKeyExpected))),
        links = Some(Links(classes = "", links = Seq(
          Link(
            href       = Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage1,
          ),Link(
            href       = Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage2,
          )
        ))))
    }

    "Given a Title Key, Caption Key, Second Caption Key and Action generate a card with a title, caption and action" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)
      val dashboardCard: DashboardCard =
        DashboardCard(
          titleKey = cardTittleKey,
          captionKey = Some(cardCaptionKey),
          captionKey2 = Some(cardCaption2Key),
          links =
            Some(Seq(
              Link(
                href       = Call(method = "GET",url = "some-href"),
                linkId     = "direct-debit-link-both-primary",
                messageKey = cardLinkMessage1,
              ))
            ))
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey =  Some(CardTitle(content = Text(cardTittleKeyExpected))),
        captionKey =  Some(CardCaption(content = Text(cardCaptionKeyExpected))),
        captionKey2 =  Some(CardCaption(content = Text(cardCaption2KeyExpected))),
        links = Some(Links(classes = "", links = Seq(
          Link(
            href       =  Call(method = "GET",url = "some-href"),
            linkId     =  "direct-debit-link-both-primary",
            messageKey    =  cardLinkMessage1,
          )
        ))))
    }

    "Given a Title key will generate a card with a [WELSH] title" in{
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard =
        DashboardCard(
          titleKey = cardTittleKey,
          captionKey = None,
          links =  None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(titleKey = Some(CardTitle(content = Text(cardTittleKeyExpectedWelsh))))
    }
    "Given a Title key and Caption Key will generate a card with a [WELSH] title and caption" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard =
        DashboardCard(
          titleKey = cardTittleKey,
          captionKey =  Some(cardCaptionKey),
          links = None)
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey = Some(CardTitle(content = Text(cardTittleKeyExpectedWelsh))),
        captionKey =Some(CardCaption(content = Text(cardCaptionKeyExpectedWelsh))),
        None)
    }
    "Given a Title Key, Caption Key and Action generate a [WELSH] card with a title, caption and action" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequestInWelsh)
      val dashboardCard: DashboardCard = DashboardCard(
        titleKey = cardTittleKey,
        captionKey = Some(cardCaptionKey),
        links = Some(Seq(
          Link(
            href       =  Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage1,
          ),Link(
            href       =  Call(method = "GET",url = "some-href-2"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage2,
          ))
        ))
      val result = DashboardCard.card(dashboardCard)

      result shouldBe Card(
        titleKey =  Some(CardTitle(content = Text(cardTittleKeyExpectedWelsh))),
        captionKey =  Some(CardCaption(content = Text(cardCaptionKeyExpectedWelsh))),
        links = Some(Links(classes = "", links = Seq(
          Link(
            href       =  Call(method = "GET",url = "some-href"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage1,
          ),Link(
            href       =  Call(method = "GET",url = "some-href-2"),
            linkId     = "direct-debit-link-both-primary",
            messageKey = cardLinkMessage2,
          )
        ))))
    }
  }
}

