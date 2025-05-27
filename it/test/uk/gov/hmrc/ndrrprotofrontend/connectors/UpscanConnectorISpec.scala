package uk.gov.hmrc.ndrrprotofrontend.connectors

import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.ndrrprotofrontend.models.upscan.UpscanInitiateResponse

import scala.concurrent.{Await, Future}

class UpscanConnectorISpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure("upscanUrl" -> "http://localhost:9570/upscan/initiate")
      .build()

  private val connector = app.injector.instanceOf[UpscanConnector]
  private implicit val hc: HeaderCarrier = HeaderCarrier().withExtraHeaders(("Content-Type", "application/json"))

  "UpscanConnector" should {
    "successfully call the Upscan service and return a valid response" in {
      val result: Future[UpscanInitiateResponse] = connector.initiate("https://callBackUrl.com", "successRedirect", "failureRedirect")

      val response = Await.result(result, 10.seconds)
      print("XXX" + response)
      response.fileReference.reference should not be empty
    }
  }
}
