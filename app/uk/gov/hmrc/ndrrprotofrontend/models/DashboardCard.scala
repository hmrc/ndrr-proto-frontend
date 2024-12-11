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
import play.api.mvc.Call
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Empty
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{ActionItem, CardTitle}
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

final case class DashboardCard(
                                titleKey: String,
                                captionKey:  Option[String] = None,
                                captionKey2: Option[String] = None,
                                captionKey3: Option[String] = None,
                                voaReference: Option[String] = None,
                                tag: Option[String] = None,
                                links: Option[Seq[Link]] = None
                              )(implicit messages: Messages)

object DashboardCard {
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
      captionKey3 = dashboardCard.captionKey3 match{
        case Some(caption3) => Some(CardCaption(content = Text(Messages(caption3))))
        case _ => None
      },
      voaReference = dashboardCard.voaReference match{
        case Some(voaReference) => Some(CardCaption(content = Text(Messages(voaReference))))
        case _ => None
      },
      tag = dashboardCard.tag match{
        case Some(tag) => Some(Tag(content = Text(Messages(tag))))
        case None => None
      },
      links = dashboardCard.links match {
        case Some(link) => Some(Links(classes = "", links = link))
        case None => None
      }
    )
  }
}
