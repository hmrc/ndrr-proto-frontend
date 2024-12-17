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

package uk.gov.hmrc.ndrrprotofrontend.models.enumsforforms

import scala.collection.immutable
import enumeratum.Enum
import uk.gov.hmrc.ndrrprotofrontend.models.{UTROption, UTROptions}

sealed trait UtrOptionFormValue extends enumeratum.EnumEntry

object UtrOptionFormValue extends Enum[UtrOptionFormValue] {

  case object ProvideUTR  extends UtrOptionFormValue
  case object ProvideNino extends UtrOptionFormValue
  case object ProvideUTRLater extends UtrOptionFormValue

  override def values: immutable.IndexedSeq[UtrOptionFormValue] = findValues

  def UtrOptionAsFormValue(utrOption: UTROption) : UtrOptionFormValue = utrOption match {
    case UTROptions.ProvideUTR => ProvideUTR
    case UTROptions.ProvideNino => ProvideNino
    case UTROptions.ProvideUTRLater => ProvideUTRLater
  }

  def UTROptionsFromFormValue(utrOptionFormValue: UtrOptionFormValue) : UTROption = utrOptionFormValue match {
    case ProvideUTR => UTROptions.ProvideUTR
    case ProvideNino => UTROptions.ProvideNino
    case ProvideUTRLater => UTROptions.ProvideUTRLater
  }

}
