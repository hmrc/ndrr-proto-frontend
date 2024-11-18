package uk.gov.hmrc.ndrrprotofrontend.models

case class FullName (firstName: String, lastName: String) {
  override def toString: String = Seq(firstName,lastName).mkString(" ")
}