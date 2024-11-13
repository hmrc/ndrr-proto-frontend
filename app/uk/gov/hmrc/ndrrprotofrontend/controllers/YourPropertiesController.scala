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

import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.govukfrontend.views.Aliases
import uk.gov.hmrc.ndrrprotofrontend.models.{MessageKey, Postcode, Reference, VoaRow, TableBuilder, VoaAddress,  VoaTable}
import uk.gov.hmrc.ndrrprotofrontend.views.html.{HelloWorldPage, YourPropertiesView}
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class YourPropertiesController @Inject()(
  mcc: MessagesControllerComponents,
  yourPropertiesView: YourPropertiesView)
    extends FrontendController(mcc) {

  private val data = Seq(
    Seq("Unit 1, New Road Trading Estate, New Road, Brixham, Devon, TQ5 9BF", "873321005112", "Approved", "Edwin and Son and Son"),
    Seq("5 Brixham Marina, Berry Head Road, Brixham, Devon, TQ5 9BW", "321929991849999963A", "Approved", "Karens Company")
  )

  private val headers = Seq(
    "Address", "Local authority reference", "Status", "Appointed agents"
  )

  private val table: Aliases.Table = TableBuilder.buildTable(tableBuilder = TableBuilder(
    tableRows = TableBuilder.createRows(data),headCells = TableBuilder.createHeadCells(headers), caption = None))

  def yourProperties: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(yourPropertiesView(table)))
  }

  def makeAVisibleTable() = throwAway().toVisibleTable()

  def throwAway(): VoaTable = {
    val headers = Seq(MessageKey("voa.address.title"), MessageKey("voa.reference.title"))

    val address0 = VoaAddress(
      line1 = "99",
      line2 = Some("Wibble Rd"),
      town = "Worthing",
      county = Some("West Sussex"),
      postcode = Postcode("BN110AA"))

    val reference0 = Reference("987765JK99")

    val address1 = VoaAddress(
      line1 = "87a",
      line2 = Some("High St"),
      town = "Hythe",
      county = Some("Kent"),
      postcode = Postcode("HY270AA")
    )

    val reference1 = Reference("WillIAm")

    val row0 = VoaRow(Seq(address0, reference0))
    val row1 = VoaRow(Seq(address1, reference1))

    VoaTable(headers, Seq(row0, row1))

  }

}
