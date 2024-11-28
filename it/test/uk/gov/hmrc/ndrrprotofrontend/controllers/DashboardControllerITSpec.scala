package uk.gov.hmrc.ndrrprotofrontend.controllers

import play.api.i18n.MessagesApi
import play.api.mvc.{AnyContentAsEmpty, Cookie}
import play.api.test.FakeRequest
import uk.gov.hmrc.ndrrprotofrontend.testSupport.BaseSpec

class DashboardControllerITSpec extends BaseSpec {
  def messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val fakeGetRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/dashboard")
}
