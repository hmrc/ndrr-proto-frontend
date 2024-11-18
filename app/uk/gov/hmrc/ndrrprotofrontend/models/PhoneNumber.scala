package uk.gov.hmrc.ndrrprotofrontend.models

case class PhoneNumber (value: String) {
  override def toString: String = value.substring(0, 5) + " " + value.substring(5)
}
