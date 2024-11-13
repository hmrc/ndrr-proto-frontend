package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.libs.json.{Json, OFormat}

final case class Postcode(value: String)

object Address {
  implicit val format: OFormat[Postcode] = Json.format[Postcode]
}

