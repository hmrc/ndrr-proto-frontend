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

case class YourMessages(
                       titleKey: String = "home.messagesCard.title",
                       hasMessages: Boolean,
                       unreadMessageCount: Long
                       ){
  def messagesCountLink(unreadMessageCount: Long): String = {
    unreadMessageCount match {
      case unreadMessageCount if unreadMessageCount == 0L => "home.messagesCard.none"
      case unreadMessageCount if unreadMessageCount == 1L => "home.messagesCard.single"
      case unreadMessageCount if unreadMessageCount > 1L =>  "home.messagesCard.multiple"
    }
  }
}