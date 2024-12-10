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

package uk.gov.hmrc.ndrrprotofrontend


import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, OptionValues, TryValues}
import play.api.i18n.{Lang, Messages, MessagesApi, MessagesImpl}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.MessagesControllerComponents
import play.api.test.{DefaultTestServerFactory, FakeRequest}
import play.api.{Application, Mode, Play}
import play.core.server.ServerConfig
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.ndrrprotofrontend.config.AppConfig

import scala.concurrent.ExecutionContext

trait BaseSpec extends AnyFreeSpec
  with Matchers
  with TryValues
  with OptionValues
  with ScalaFutures
  with IntegrationPatience
  with BeforeAndAfterEach { self =>

  protected def applicationBuilder(): GuiceApplicationBuilder = {
    new GuiceApplicationBuilder()
      .overrides()
  }

  lazy val application: Application = applicationBuilder().build()
  implicit lazy val messagesAPI: MessagesApi = application.injector.instanceOf[MessagesApi]
  implicit lazy val messagesProvider: MessagesImpl = MessagesImpl(Lang("en"), messagesAPI)
  lazy val mcc: MessagesControllerComponents = application.injector.instanceOf[MessagesControllerComponents]
  def messages(app: Application): Messages = app.injector.instanceOf[MessagesApi].preferred(FakeRequest())
  implicit lazy val appConfig: AppConfig = application.injector.instanceOf[AppConfig]
  implicit lazy val ec: ExecutionContext = application.injector.instanceOf[ExecutionContext]
  implicit val hc: HeaderCarrier = HeaderCarrier()

  private val testServerPort: Int = 19001

  override def beforeEach(): Unit = {
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    Play.stop(application)
    super.afterEach()
  }

  object TestServerFactory extends DefaultTestServerFactory {
    override protected def serverConfig(app: Application): ServerConfig = {
      val sc: ServerConfig = ServerConfig(port    = Some(testServerPort), sslPort = Some(0), mode = Mode.Test, rootDir = app.path)
      sc.copy(configuration = sc.configuration.withFallback(overrideServerConfiguration(app)))
    }
  }
}
