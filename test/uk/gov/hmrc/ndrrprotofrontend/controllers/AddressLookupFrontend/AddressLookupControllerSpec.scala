/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ndrrprotofrontend.controllers.AddressLookupFrontend

import org.mockito.ArgumentMatchers
import org.mockito.Mockito.{mock, when}
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import play.api.test.{DefaultAwaitTimeout, FutureAwaits}
import uk.gov.hmrc.ndrrprotofrontend.BaseSpec
import uk.gov.hmrc.ndrrprotofrontend.connectors.AddressLookupConnector
import uk.gov.hmrc.ndrrprotofrontend.controllers.addressLookupFrontend.AddressLookupController
import uk.gov.hmrc.ndrrprotofrontend.models.alf.init._
import uk.gov.hmrc.ndrrprotofrontend.models.alf.{AlfAddress, AlfResponse}
import uk.gov.hmrc.ndrrprotofrontend.models.core.ErrorModel

import scala.concurrent.Future

class AddressLookupControllerSpec extends BaseSpec with FutureAwaits with DefaultAwaitTimeout {

  val mockALFConnector = mock(classOf[AddressLookupConnector])
  val service = new AddressLookupController(
    mcc = mcc,
    addressLookupConnector = mockALFConnector,
    frontendAppConfig = appConfig
  )
  val organisation = "homes ltd"
  val addressLine1 = "line 1"
  val addressLine2 = "line 2"
  val addressLine3 = "line 3"
  val addressLine4 = "line 4"
  val postcode = "aa1 1aa"
  val countryCode = "UK"
  val customerAddressMax: AlfResponse = AlfResponse(
    AlfAddress(
      Some(organisation),
      List(addressLine1, addressLine2, addressLine3, addressLine4),
      Some(postcode),
      Some(countryCode)
    ))

  val offRampBaseUrl = "http://localhost:9001/ndrr-proto-frontend/off-ramp"

  "getAddress" - {
    "return an address when Connector returns success" in {
      when(mockALFConnector.getAddress("123456789")(hc, ec)).thenReturn(Future.successful(Right(customerAddressMax)))

      val res = service.getAddress("123456789")

      whenReady(res) { result =>
        result mustBe customerAddressMax
      }
    }
    "return an exception when Connector returns error" in {
      when(mockALFConnector.getAddress("123456789")(hc, ec)).thenReturn(Future.successful(Left(ErrorModel(1, "foo"))))

      val res = intercept[Exception](await(service.getAddress("123456789")))
      res.getMessage mustBe "Error returned from ALF for 123456789 1 foo for None"
    }
  }

  "initJourney" - {
    "should return response from connector" in {
      val journeyConfig = JourneyConfig(1, JourneyOptions(""), None, None)
      when(mockALFConnector.initJourney(ArgumentMatchers.eq(journeyConfig))(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(Future.successful(Right("foo")))

      whenReady(service.initJourney(journeyConfig)) {
        res => res mustBe Right("foo")
      }
    }
  }
    "initJourneyAndReturnOnRampUrl" - {
      s"should return a journey config for Address Contact Details" in {
        val exampleNGRIdWeGenerate = "Foobar"
        val res = service.createJourneyConfig( exampleNGRIdWeGenerate)(implicitly)
          val expected: JourneyConfig = JourneyConfig(
            version = 2,
            options = JourneyOptions(
              continueUrl = s"$offRampBaseUrl/${""}contact-address/$exampleNGRIdWeGenerate",
              homeNavHref = None,
              signOutHref = None,
              accessibilityFooterUrl = Some("localhost/accessibility-statement/ndrr-proto-frontend"),
              phaseFeedbackLink = None,
              deskProServiceName = None,
              showPhaseBanner = Some(false),
              alphaPhase = None,
              includeHMRCBranding = Some(true),
              ukMode = Some(true),
              selectPageConfig = Some(SelectPageConfig(
                proposalListLimit = Some(10),
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
                    navTitle = Some("ndrr-proto-frontend"),
                    phaseBannerHtml = None)),
                  selectPageLabels = None,
                  lookupPageLabels = Some(
                    LookupPageLabels(
                      title = Some("Find the contact address"),
                      heading = Some("Find the contact address"),
                      postcodeLabel = Some("Postcode"))),
                  editPageLabels = Some(
                    EditPageLabels(
                      title =      Some("Find the contact address"),
                      heading =    Some("Find the contact address"),
                      line1Label = Some("Address line 1"),
                      line2Label = Some("Address line 2"),
                      line3Label = Some("Address line 3 (optional)"),
                      townLabel =  Some("Address line 4 (optional)"),
                      postcodeLabel = Some("Postcode"),
                    )),
                  confirmPageLabels = None,
                  countryPickerLabels = None)))
            ),
            requestedVersion = None)

          res mustBe expected
        }

        s"should return Successful future when connector returns success for Address Contact Details" in {
          val exampleNGRIdWeGenerate = "Foobar"
          val expectedJourneyConfigToBePassedToConnector: JourneyConfig = JourneyConfig(
            version = 2,
            options = JourneyOptions(
              continueUrl = s"$offRampBaseUrl/${""}contact-address/$exampleNGRIdWeGenerate",
              homeNavHref = None,
              signOutHref = None,
              accessibilityFooterUrl = Some("localhost/accessibility-statement/ndrr-proto-frontend"),
              phaseFeedbackLink = None,
              deskProServiceName = None,
              showPhaseBanner = Some(false),
              alphaPhase = None,
              includeHMRCBranding = Some(true),
              ukMode = Some(true),
              selectPageConfig = Some(SelectPageConfig(
                proposalListLimit = Some(10),
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
                    navTitle = Some("ndrr-proto-frontend"),
                    phaseBannerHtml = None)),
                  selectPageLabels = None,
                  lookupPageLabels = Some(
                    LookupPageLabels(
                      title = Some("Find the contact address"),
                      heading = Some("Find the contact address"),
                      postcodeLabel = Some("Postcode"))),
                  editPageLabels = Some(
                    EditPageLabels(
                      title =      Some("Find the contact address"),
                      heading =    Some("Find the contact address"),
                      line1Label = Some("Address line 1"),
                      line2Label = Some("Address line 2"),
                      line3Label = Some("Address line 3 (optional)"),
                      townLabel =  Some("Address line 4 (optional)"),
                      postcodeLabel = Some("Postcode"),
                    )),
                  confirmPageLabels = None,
                  countryPickerLabels = None)))
            ),
            requestedVersion = None)

            when(mockALFConnector.initJourney(ArgumentMatchers.eq(expectedJourneyConfigToBePassedToConnector))(ArgumentMatchers.any(), ArgumentMatchers.any()))
              .thenReturn(Future.successful(Right("foo")))
            whenReady(service.initJourneyAndReturnOnRampUrl(exampleNGRIdWeGenerate)(implicitly, implicitly, implicitly)) {
              res => res mustBe "foo"
          }
        }

        "should return Exception if connector returns left" in {
          when(mockALFConnector.initJourney(ArgumentMatchers.any())(ArgumentMatchers.any(), ArgumentMatchers.any()))
            .thenReturn(Future.successful(Left(ErrorModel(1, "foo"))))
          val res = intercept[Exception](await(service.initJourneyAndReturnOnRampUrl()(implicitly, implicitly, implicitly)))
          res.getMessage mustBe "Failed to init ALF foo with status 1 for None"
        }
      }
}