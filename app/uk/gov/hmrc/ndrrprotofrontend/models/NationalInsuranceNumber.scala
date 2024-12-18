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

final case class NationalInsuranceNumber (value: String)

object NationalInsuranceNumber extends CommonFormValidators {

  implicit val format: OFormat[NationalInsuranceNumber] = Json.format[NationalInsuranceNumber]
  lazy val ninoEmptyError          = "registration.nino.empty.error"
  lazy val ninoInvalidFormat       = "registration.nino.invalidFormat.error"
  val NINO                    = "nino.value"

  def form(): Form[NationalInsuranceNumber] =
    Form(
      mapping(
        NINO -> text()
          .verifying(ninoEmptyError, isNonEmpty)
          .verifying(ninoInvalidFormat, isValidNino)
      )(NationalInsuranceNumber.apply)(NationalInsuranceNumber.unapply)
    )

}

