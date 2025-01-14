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

package uk.gov.hmrc.ndrrprotofrontend.connectors

import mocks.MockHttp
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import play.api.http.Status
import play.api.test.Helpers.{await, defaultAwaitTimeout}
import play.mvc.Http.HeaderNames
import uk.gov.hmrc.http.HttpResponse
import uk.gov.hmrc.ndrrprotofrontend.connectors.httpParsers.AddressLookupHttpParser.AddressLookupInitJourneyReads
import uk.gov.hmrc.ndrrprotofrontend.connectors.httpParsers.ResponseHttpParser.HttpResult
import uk.gov.hmrc.ndrrprotofrontend.models.alf.init.{JourneyConfig, JourneyOptions}
import uk.gov.hmrc.ndrrprotofrontend.models.alf.{AlfAddress, AlfResponse}
import uk.gov.hmrc.ndrrprotofrontend.models.core.ErrorModel

import scala.concurrent.Future

class AddressLookupConnectorSpec extends MockHttp {

  val errorModel: HttpResponse = HttpResponse(Status.BAD_REQUEST, "Error Message")
  val testAddressLookupConnector = new AddressLookupConnector(mockHttp, appConfig)
  lazy val id = "111111111"
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

  "AddressLookupConnector" - {

    "format the getAddressUrl correctly for" - {
      "calling getAddressUrl when feature switch is disabled" in {
        val testUrl = testAddressLookupConnector.getAddressUrl(id, addressLookupFrontendTestEnabled = false)
        testUrl mustEqual s"${appConfig.addressLookupService}/api/confirmed?id=$id"
      }
      "calling getAddressUrl when feature switch is enabled" in {
        val testUrl = testAddressLookupConnector.getAddressUrl(id, addressLookupFrontendTestEnabled = true)
        testUrl mustEqual s"${appConfig.registrationBaseUrl}${uk.gov.hmrc.ndrrprotofrontend.controllers.test.routes.AddressFrontendStubController.addresses(id).url}"
      }
    }
    "return correct initAddressUrl correctly" - {
      "when feature switch is disabled" in {
        testAddressLookupConnector.initJourneyUrl(addressLookupFrontendTestEnabled = false) mustBe s"${appConfig.addressLookupService}/api/init"
      }
      "when feature switch is enabled" in {
        testAddressLookupConnector.initJourneyUrl(addressLookupFrontendTestEnabled = true) mustBe
          s"${appConfig.registrationBaseUrl}${uk.gov.hmrc.ndrrprotofrontend.controllers.test.routes.AddressFrontendStubController.initialise().url}"
      }
    }

    "getAddress" - {

      def getAddressResult: Future[HttpResult[AlfResponse]] = testAddressLookupConnector.getAddress(id)(implicitly,implicitly)

      "return an AlfResponse Model" in {
        setupMockHttpGet(Right(customerAddressMax))
        await(getAddressResult) mustBe Right(customerAddressMax)
      }

      "given an error should" - {

        "return an Left with an ErrorModel" in {
          setupMockHttpGet(Left(errorModel))
          await(getAddressResult) mustBe Left(errorModel)
        }
      }
    }

    "initJourney" - {
      val journeyConfig = JourneyConfig(1, JourneyOptions(""), None, None)

      s"should return url if ${Status.ACCEPTED} returned and ${HeaderNames.LOCATION} exists" in {
        val response = AddressLookupInitJourneyReads.read("", "", HttpResponse(Status.ACCEPTED, "", Map(HeaderNames.LOCATION -> Seq("foo"))))
        setupMockHttpPost(response)
        await(testAddressLookupConnector.initJourney(journeyConfig)) mustBe response
      }

      s"return Left if ${Status.ACCEPTED} but no header exists" in {
        val response = AddressLookupInitJourneyReads.read("", "", HttpResponse(Status.ACCEPTED, "", Map.empty))
        setupMockHttpPost(response)
        await(testAddressLookupConnector.initJourney(journeyConfig)) mustBe
          Left(ErrorModel(Status.ACCEPTED, s"No ${HeaderNames.LOCATION} key in response from init response from ALF"))
      }

      s"return Left if status is ${Status.BAD_REQUEST}" in {
        val response = AddressLookupInitJourneyReads.read("", "", HttpResponse(Status.BAD_REQUEST, "Error Message"))
        setupMockHttpPost(response)
        await(testAddressLookupConnector.initJourney(journeyConfig)) mustBe Left(ErrorModel(Status.BAD_REQUEST, "Error Message returned from ALF"))
      }

      "return Left if status not accepted statuses from API" in {
        val response = AddressLookupInitJourneyReads.read("", "", HttpResponse(Status.INTERNAL_SERVER_ERROR, "Error Message"))
        setupMockHttpPost(response)
        await(testAddressLookupConnector.initJourney(journeyConfig)) mustBe
          Left(ErrorModel(Status.INTERNAL_SERVER_ERROR, "Unexpected error occurred when init journey from ALF"))
      }
    }
  }
}