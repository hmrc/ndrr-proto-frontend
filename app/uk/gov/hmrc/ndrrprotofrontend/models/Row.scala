package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.libs.json.{Json, OFormat}

final case class Row(columnEntries: Seq[ColumnEntry])

object Row {
  implicit val format: OFormat[Row] = Json.format[Row]
}

