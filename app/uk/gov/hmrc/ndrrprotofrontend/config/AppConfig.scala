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

package uk.gov.hmrc.ndrrprotofrontend.config

import com.typesafe.config.Config

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

@Singleton
class AppConfig @Inject()(servicesConfig: ServicesConfig, config: Configuration) {
  val ngrHomeUrl: String = servicesConfig.getString("ngrHomeUrl")
  val welshLanguageSupportEnabled: Boolean = config.getOptional[Boolean]("features.welsh-language-support").getOrElse(false)
  val registrationBaseUrl: String = servicesConfig.baseUrl("ndrr-proto-frontend")
  private val accessibilityHost: String = servicesConfig.getConfString(
    confKey = "accessibility-statement.host", throw new Exception("missing config accessibility-statement.host")
  )
  def accessibilityFooterUrl = s"$accessibilityHost/accessibility-statement/ndrr-proto-frontend"
  val addressLookupService: String = servicesConfig.baseUrl("address-lookup-frontend")
  val addressLookUpFrontendTestEnabled: Boolean = servicesConfig.getBoolean("addressLookupFrontendTest.enabled")
  val addressLookupOffRampUrl: String = servicesConfig.getString(key ="addressLookupOffRampUrl")

  object AddressLookupConfig {

    private val addressLookupInitConfig: Config = config
      .getOptional[Configuration](s"address-lookup-frontend-init-config")
      .getOrElse(throw new IllegalArgumentException(s"Configuration for address-lookup-frontend-init-config not found"))
      .underlying

    val version: Int = addressLookupInitConfig.getInt("version")
    val selectPageConfigProposalLimit: Int = addressLookupInitConfig.getInt("select-page-config.proposalListLimit")

    object ContactAddress {
      def offRampUrl(ngrId: String): String = {
        s"$addressLookupOffRampUrl${uk.gov.hmrc.ndrrprotofrontend.controllers.addressLookupFrontend.routes.RampOffController.contactAddressOffRamp(ngrId, "").url.replace("?id=", "")}"
      }
    }
  }
}
