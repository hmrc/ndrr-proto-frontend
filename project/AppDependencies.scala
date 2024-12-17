import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  private val bootstrapVersion = "9.5.0"
  private val enumeratumVersion = "1.8.1"


  val compile = Seq(
    "uk.gov.hmrc"             %% "bootstrap-frontend-play-30" % bootstrapVersion,
    "uk.gov.hmrc"             %% "play-frontend-hmrc-play-30" % "11.3.0",
    "com.beachape"            %% "enumeratum-play"            %  enumeratumVersion,
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-30"     % bootstrapVersion            % Test,
    
    "org.jsoup"               %  "jsoup"                      % "1.13.1"            % Test,
  )

  val it = Seq.empty
}
