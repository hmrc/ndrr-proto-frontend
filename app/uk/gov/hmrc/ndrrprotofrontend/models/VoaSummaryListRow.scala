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
import uk.gov.hmrc.govukfrontend.views.Aliases.{Actions, CardTitle, Key, SummaryList, SummaryListRow, Text, Value}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.{ActionItem, Card}

case class VoaSummaryListRow (titleMessageKey: String, value: Seq[String], changeLink: Option[Link])

object VoaSummaryListRow {

  def buildSummaryList(voaSummaryListRow: Seq[VoaSummaryListRow], voaCard: Option[VoaCard] = None)(implicit messages: Messages): SummaryList = {
    SummaryList(
      voaSummaryListRow.map{ voaSummaryListRow =>
        voaSummaryListRow.value match {
          case seqOfString if seqOfString.nonEmpty => SummaryListRow(
            key     = Key(content = Text(Messages(voaSummaryListRow.titleMessageKey))),
            value   = Value(content = HtmlContent(seqOfString.mkString("</br>"))),
            actions = voaSummaryListRow.changeLink match {
              case Some(changeLink) => Some(
                Actions(items = Seq(ActionItem(
                  href               = changeLink.href.url,
                  content            = Text(Messages(changeLink.messageKey)),
                  visuallyHiddenText = changeLink.visuallyHiddenMessageKey,
                  attributes         = Map(
                    "id" -> changeLink.linkId
                  )
                )))
              )
              case None => None
            }
          )
          case _ => SummaryListRow(
            key   = Key(content = Text(Messages(voaSummaryListRow.titleMessageKey))),
            value = voaSummaryListRow.changeLink match {
              case Some(link) => Value(HtmlContent(s"""<a id="${link.linkId}" href="${link.href.url}" class="govuk-link">${messages(link.messageKey)}</a>"""))
              case None       => Value()
            }
          )
        }
      },
      voaCard match {
        case Some(card) =>
          Some(
            Card(
              title = Some(CardTitle(content = Text(card.titleMessageKey))),
              actions = card.headingLink.map { links =>
                Actions(
                  items = links.map { linkOption =>
                      ActionItem(
                        href = linkOption.href.url,
                        content = Text(Messages(linkOption.messageKey)),
                        visuallyHiddenText = Some(linkOption.visuallyHiddenMessageKey.getOrElse(""))
                      )
                  }
                )
              }
            )
          )
        case None => None
      }
    )
  }
}
