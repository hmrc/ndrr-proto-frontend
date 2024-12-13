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

import enumeratum.{Enum, EnumEntry, EnumFormats, PlayEnum}
import play.api.libs.json.Format
import uk.gov.hmrc.ndrrprotofrontend.utils.{EnumFormat, Eq}

import scala.collection.immutable

sealed trait radioItem
sealed trait UTROption extends EnumEntry with radioItem

object UTROption {
  implicit val format: Format[UTROption] = EnumFormat(UTROptions)
  implicit val eq: Eq[UTROption] = Eq.fromUniversalEquals
}

object UTROptions extends Enum[UTROption] {

  case object ProvideUTR extends UTROption
  case object ProvideNino extends UTROption
  case object ProvideUTRLater extends UTROption

  override val values: immutable.IndexedSeq[UTROption] = findValues
}