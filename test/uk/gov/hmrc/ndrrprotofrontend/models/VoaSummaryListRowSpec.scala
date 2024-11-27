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
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.views.Aliases.SummaryList
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Empty, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{Key, SummaryListRow, Value}
import uk.gov.hmrc.ndrrprotofrontend.BaseSpec

class VoaSummaryListRowSpec extends BaseSpec {
  def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/your-properties")
  "buildSummaryListRow" - {

    "will generate a minimum SummaryListRow from a minimum VoaSummaryListRow" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)

      val voaSummaryListRow: VoaSummaryListRow = VoaSummaryListRow("", Seq.empty, None)
      val result = VoaSummaryListRow.buildSummaryList(Seq(voaSummaryListRow))
      result shouldBe SummaryList(Seq(SummaryListRow(Key(Text("")), Value(Empty))))

    }

    "will generate a minimum SummaryListRow with a key from English messages if the key is set in VoaSummaryListRow" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)

      val voaSummaryListRow: VoaSummaryListRow = VoaSummaryListRow("voa.dob.title", Seq.empty, None)
      val result = VoaSummaryListRow.buildSummaryList(Seq(voaSummaryListRow))
      result shouldBe SummaryList(Seq(SummaryListRow(Key(Text("Date of birth")), Value(Empty))))
    }

    "will generate a SummaryListRow with a value if the value is set in CheckYourAnswersRow" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)

      val voaSummaryListRow: VoaSummaryListRow = VoaSummaryListRow("voa.dob.title", Seq("5 January 1978"), None)
      val result = VoaSummaryListRow.buildSummaryList(Seq(voaSummaryListRow))
      result shouldBe SummaryList(Seq(SummaryListRow(Key(Text("Date of birth")), Value(HtmlContent("5 January 1978")))))
    }

    "will generate a SummaryListRow with a separated lines if the value is set as multiple strings in CheckYourAnswersRow" in {
      implicit val messages: Messages = messagesApi.preferred(fakeGetRequest)

      val voaSummaryListRow: VoaSummaryListRow = VoaSummaryListRow("voa.address.title", Seq("Line1", "Line2"), None)
      val result = VoaSummaryListRow.buildSummaryList(Seq(voaSummaryListRow))
      result shouldBe SummaryList(Seq(SummaryListRow(Key(Text("Address")), Value(HtmlContent("Line1</br>Line2")))))
    }

  }

}
