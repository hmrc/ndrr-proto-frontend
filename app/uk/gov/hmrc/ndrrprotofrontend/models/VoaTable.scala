package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.libs.json.{Json, OFormat}
import uk.gov.hmrc.govukfrontend.views.Aliases.Table

final case class VoaTable(header: Seq[MessageKey], rows: Seq[Row]) {
  def toVisibleTable(): Table = ???
}

object VoaTable {
  implicit val format: OFormat[VoaTable] = Json.format[VoaTable]
}
