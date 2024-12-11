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

package uk.gov.hmrc.ndrrprotofrontend.controllers.addressLookupFrontend

import play.api.Logger
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.ndrrprotofrontend.config.AppConfig
import uk.gov.hmrc.ndrrprotofrontend.connectors.AddressLookupConnector
import uk.gov.hmrc.ndrrprotofrontend.connectors.httpParsers.ResponseHttpParser.HttpResult
import uk.gov.hmrc.ndrrprotofrontend.models.UkAddress
import uk.gov.hmrc.ndrrprotofrontend.models.alf.init._
import uk.gov.hmrc.ndrrprotofrontend.models.alf.{AlfAddress, AlfResponse}
import uk.gov.hmrc.ndrrprotofrontend.utilities.AddressHelper
import uk.gov.hmrc.play.bootstrap.frontend.controller.FrontendController

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AddressLookupController @Inject()(
                            mcc: MessagesControllerComponents,
                            addressLookupConnector: AddressLookupConnector,
                            frontendAppConfig: AppConfig)(implicit ec: ExecutionContext)
  extends FrontendController(mcc) with AddressHelper with I18nSupport{

  val logger: Logger = Logger(this.getClass)

  def addressChecker(address: AlfAddress, alfId: String): UkAddress = {
    val ukAddress: UkAddress = UkAddress(address.lines, address.postcode.getOrElse(""), alfId = Some(alfId))

    if (ukAddress.lines.isEmpty && ukAddress.postCode == "" && address.organisation.isEmpty) {
      throw new RuntimeException("Not Found (Alf has returned an empty address and organisation name)")
    } else {
      ukAddress
    }
  }

  def initJourney(journeyConfig: JourneyConfig)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[HttpResult[String]] = {
    addressLookupConnector.initJourney(journeyConfig)
  }

  def getAddress(alfId: String)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[AlfResponse] = {
    addressLookupConnector.getAddress(alfId).map {
      case Right(addressResponse) => addressResponse
      case Left(error) => throw new Exception(s"Error returned from ALF for $alfId ${error.status} ${error.message} for ${hc.requestId}")
    }
  }

  def startAddressLookup(): Action[AnyContent] = Action.async { implicit request =>
    initJourneyAndReturnOnRampUrl().flatMap{ url =>
      Future.successful(Redirect(url))
    }.recover {
      case ex: Exception => Redirect(uk.gov.hmrc.ndrrprotofrontend.controllers.routes.RegistrationCheckAnswersController.show)
    }
  }

  def initJourneyAndReturnOnRampUrl(ngrId: String = generateId)(implicit hc: HeaderCarrier, ec: ExecutionContext, messages: Messages): Future[String] = {
    val journeyConfig: JourneyConfig = createJourneyConfig(ngrId)
    initJourney(journeyConfig).map {
      case Right(onRampUrl) => onRampUrl
      case Left(error) => throw new Exception(s"Failed to init ALF ${error.message} with status ${error.status} for ${hc.requestId}")
    }
  }

  def createJourneyConfig(ngrId: String)(implicit messages: Messages): JourneyConfig = {
    JourneyConfig(
      version = frontendAppConfig.AddressLookupConfig.version,
      options = JourneyOptions(
        continueUrl = frontendAppConfig.AddressLookupConfig.ContactAddress.offRampUrl(ngrId),
        homeNavHref = None,
        signOutHref = None,
        accessibilityFooterUrl = Some(frontendAppConfig.accessibilityFooterUrl),
        phaseFeedbackLink = None,
        deskProServiceName = None,
        showPhaseBanner = Some(false),
        alphaPhase = None,
        includeHMRCBranding = Some(true),
        ukMode = Some(true),
        selectPageConfig = Some(SelectPageConfig(
          proposalListLimit = Some(frontendAppConfig.AddressLookupConfig.selectPageConfigProposalLimit),
          showSearchAgainLink = Some(true))),
        showBackButtons = Some(true),
        disableTranslations = Some(true),
        allowedCountryCodes = None,
        confirmPageConfig = Some(ConfirmPageConfig(
          showSearchAgainLink = Some(true),
          showSubHeadingAndInfo = Some(true),
          showChangeLink = Some(true),
          showConfirmChangeText = Some(true))),
        timeoutConfig = None,
        pageHeadingStyle = Some("govuk-heading-l")),
      labels = Some(
        JourneyLabels(
          en = Some(LanguageLabels(
            appLevelLabels = Some(AppLevelLabels(
              navTitle = Some(messages("service.name")),
              phaseBannerHtml = None)),
            selectPageLabels = None,
            lookupPageLabels = Some(
              LookupPageLabels(
                title = Some(messages("addressLookupFrontend.contactDetails.lookupPageLabels.title")),
                heading = Some(messages("addressLookupFrontend.contactDetails.lookupPageLabels.title")),
                postcodeLabel = Some(messages("addressLookupFrontend.contactDetails.lookupPageLabels.postcodeLabel")))),
            editPageLabels = Some(
              EditPageLabels(
                title = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.title")),
                heading = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.title")),
                line1Label = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.line1Label")),
                line2Label = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.line2Label")),
                line3Label = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.line3Label")),
                townLabel = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.townLabel")),
                postcodeLabel = Some(messages("addressLookupFrontend.contactDetails.editPageLabels.postcodeLabel")),
               )),
            confirmPageLabels = None,
            countryPickerLabels = None)))
      ),
      requestedVersion = None)
  }
}
