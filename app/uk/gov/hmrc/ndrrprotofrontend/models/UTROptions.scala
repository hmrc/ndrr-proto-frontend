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
import uk.gov.hmrc.ndrrprotofrontend.models.UTROptions.UTROptions

trait selectOption extends Enumeration {
  text
}

object UTROptions extends Enumeration {
  type UTROptions = Value

  case object prodvide
  val PROVIDE_UTR : Value = Value("Yes I want to provide my UTR")
  val PROVIDE_NINO : Value = Value("No, I want to provide my National Insurance number")
  val NO_UTR : Value = Value("No, I want provide a tax reference later")

  def withNameOpt(name: String): Option[Value] = values.find(_.toString == name)

  implicit val format: Format[UTROptions] = Format(Reads.enumNameReads(UTROptions), Writes.enumNameWrites)

}

case class UniqueTaxReferenceOption(value: Option[UTROptions])

object UniqueTaxReferenceOption extends CommonFormValidators {
  val emptyError = "Select an option"

  def form(): Form[UniqueTaxReferenceOption] =
    Form(
      mapping(
        "value" -> optional(text())
          .verifying(emptyError, _.isDefined)
          .transform[String](_.get, Some.apply)
          .verifying(emptyError, contains(UTROptions.values.toSeq.map(_.toString)))
      )(UniqueTaxReferenceOption.apply)(UniqueTaxReferenceOption.unapply)
    )

  def apply(value: String): UniqueTaxReferenceOption = UniqueTaxReferenceOption(UTROptions.withNameOpt(value))
  def unapply(registrationType: UniqueTaxReferenceOption): Option[String] =
    registrationType.value.map(_.toString)
}