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
import play.api.data.Forms.{mapping, optional, text}
import play.api.libs.json.{Format, Reads, Writes}
import uk.gov.hmrc.ndrrprotofrontend.models.YesNoOptions.YesNoOptions

object YesNoOptions extends Enumeration {
  type YesNoOptions = Value

  case object prodvide
  val YES : Value = Value("Yes")
  val NO : Value = Value("No")

  def withNameOpt(name: String): Option[Value] = values.find(_.toString == name)

  implicit val format: Format[YesNoOptions] = Format(Reads.enumNameReads(YesNoOptions), Writes.enumNameWrites)

}

case class YesNoValidator(value: Option[YesNoOptions])

object YesNoValidator extends CommonFormValidators {
  val emptyError = "Select an option"

  def form(): Form[YesNoValidator] =
    Form(
      mapping(
        "value" -> optional(text())
          .verifying(emptyError, _.isDefined)
          .transform[String](_.get, Some.apply)
          .verifying(emptyError, contains(YesNoOptions.values.toSeq.map(_.toString)))
      )(YesNoValidator.apply)(YesNoValidator.unapply)
    )

  def apply(value: String): YesNoValidator = YesNoValidator(YesNoOptions.withNameOpt(value))
  def unapply(registrationType: YesNoValidator): Option[String] =
    registrationType.value.map(_.toString)
}