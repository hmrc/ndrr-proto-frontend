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

package uk.gov.hmrc.ndrrprotofrontend.models.alf

import play.api.libs.json.{Json, OFormat}

case class AlfResponse(address: AlfAddress)

case class AlfAddress(
                       organisation: Option[String],
                       lines: List[String],
                       postcode: Option[String],
                       countryCode: Option[String]
                     )

object AlfAddress {
  implicit val format: OFormat[AlfAddress] = Json.format[AlfAddress]
}

object AlfResponse {
  implicit val format: OFormat[AlfResponse] = Json.format[AlfResponse]
}
