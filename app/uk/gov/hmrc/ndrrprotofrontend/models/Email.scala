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

final case class Email (value: String)

object Email extends CommonFormValidators {

  implicit val format: OFormat[Email] = Json.format[Email]
  lazy val emailEmptyError          = "registration.email.empty.error"
  lazy val emailInvalidFormat       = "registration.email.invalidFormat.error"
  val maxLength                     = 24
  val email                   = "email.value"

  def form(): Form[Email] =
    Form(
      mapping(
        email -> text()
          .verifying(emailEmptyError, isNonEmpty)
          .verifying(emailInvalidFormat, isValidEmail)
      )(Email.apply)(Email.unapply)
    )

}
