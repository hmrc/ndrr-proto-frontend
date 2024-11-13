package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.libs.json.{Json, OFormat}

final case class Address(line1: String, line2: Option[String], town: String, county: Option[String], postcode: Postcode) extends ColumnEntry

object Address {
  implicit val format: OFormat[Address] = Json.format[Address]
}
