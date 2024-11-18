package uk.gov.hmrc.ndrrprotofrontend.models

case class DateOfBirth (day: Int, month: String, year: Int) {
  override def toString: String = Seq(day, month, year).mkString(" ")
}
