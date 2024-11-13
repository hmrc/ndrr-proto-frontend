package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.libs.json.{Json, OFormat}

final case class MessageKey(key: String)

object MessageKey {
  implicit val format: OFormat[MessageKey] = Json.format[MessageKey]
}