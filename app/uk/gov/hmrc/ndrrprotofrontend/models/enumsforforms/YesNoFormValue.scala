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

import enumeratum.Enum
import uk.gov.hmrc.ndrrprotofrontend.models.{YesNoOption, YesNoOptions}

import scala.collection.immutable

sealed trait YesNoFormValue extends enumeratum.EnumEntry

object YesNoFormValue extends Enum[YesNoFormValue] {

  case object Yes  extends YesNoFormValue
  case object No extends YesNoFormValue

  override def values: immutable.IndexedSeq[YesNoFormValue] = findValues

  def YesNoOptionAsFormValue(yesNoOption: YesNoOption) : YesNoFormValue = yesNoOption match {
    case YesNoOptions.Yes => Yes
    case YesNoOptions.No => No
  }

  def YesNoOptionsFromFormValue(yesNoFormValue: YesNoFormValue): YesNoOption = yesNoFormValue match {
    case Yes => YesNoOptions.Yes
    case No => YesNoOptions.No
  }

}
