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

package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.json.{Json, OFormat}

case class Postcode(value: String){
  override def toString: String = value
}

object Postcode extends CommonFormValidators {

  implicit val format: OFormat[Postcode] = Json.format[Postcode]

  lazy val postcodeEmptyError    = "registration.postcode.empty.error"
  lazy val postcodeInvalidFormat = "registration.postcode.invalidFormat.error"
  val postcode                   = "postcode.value"

  def form(): Form[Postcode] =
    Form(
      mapping(
        postcode -> text()
          .verifying(postcodeEmptyError, isNonEmpty)
          .verifying(postcodeInvalidFormat, isValidPostcode)
      )(Postcode.apply)(Postcode.unapply)
    )

}
