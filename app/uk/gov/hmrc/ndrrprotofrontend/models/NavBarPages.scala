/*
 * Copyright 2025 HM Revenue & Customs
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

import play.api.mvc.Call
import uk.gov.hmrc.ndrrprotofrontend.controllers.routes

case class NavButton(
                      fieldName: String,
                      call: Call,
                      messageKey: String,
                      linkId: String,
                      notification: Option[Int],
                      selected: Boolean,
                    )

case class NavBarCurrentPage(
                              homePage: Boolean = false,
                              messagesPage: Boolean = false,
                              profileAndSettingsPage: Boolean = false,
                              signOutPage: Boolean = false,
                            )

case class NavBarContents(
                           homePage: Option[Boolean] = None,
                           messagesPage: Option[Boolean] = None,
                           profileAndSettingsPage: Option[Boolean] = None,
                           signOutPage: Option[Boolean] = None
                         )

case class NewNavigationBarContent(
                                 accountHome: Option[NavButton],
                                 navigationButtons: Option[Seq[NavButton]]
                               )

object NavBarPageContents {
  val Selected: Option[Boolean] = Some(true)

  def CreateNavBar(contents: NavBarContents, currentPage: NavBarCurrentPage, notifications: Option[Int] = None): NewNavigationBarContent = {

    // Define buttons
    val homePageButton     = NavButton(fieldName = "HomePage", call = Call("GET", routes.DashboardController.show.url), messageKey = "nav.home", linkId = "Home", selected = currentPage.homePage, notification = None)
    val messagesPageButton = NavButton(fieldName = "MessagesPage",call = Call("GET", "/messages"),messageKey = "nav.messages",linkId = "Messages",selected = currentPage.messagesPage, notification = notifications)
    val profilePageButton   = NavButton(fieldName = "ProfileAndSettingsPage",call = Call("GET", routes.ProfileAndSettingsController.show.url),messageKey = "nav.profileAndSettings",linkId = "Profile",selected = currentPage.profileAndSettingsPage, notification = None)
    val signOutPageButton  = NavButton(fieldName = "SignOutPage",call = Call("GET", "/signout"),messageKey = "nav.signOut",linkId = "SignOut",selected = currentPage.signOutPage, notification = None)

    // Map fields to their NavButton equivalents
    val buttonMapping = Seq(
      "homePage"          -> (contents.homePage, homePageButton),
      "messagesPage"      -> (contents.messagesPage, messagesPageButton),
      "profileAndSettings" -> (contents.profileAndSettingsPage, profilePageButton),
      "signOutPage"       -> (contents.signOutPage, signOutPageButton)
    )

    // Filter buttons based on their corresponding content value
    val filteredButtons = buttonMapping.collect {
      case (_, (Some(true), button)) => button
    }

    // Ensure HomePage is always first if it exists
    val (homePageButtons, otherButtons) = filteredButtons.partition(_.fieldName == "HomePage")
    val sortedButtons = homePageButtons ++ otherButtons

    // Create the NavigationBarContent
    NewNavigationBarContent(
      accountHome = homePageButtons.headOption,
      navigationButtons = if (sortedButtons.size > 1) Some(sortedButtons.tail) else None
    )
  }
}
