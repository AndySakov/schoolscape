package api.server

import java.net.InetAddress
import java.time.Year
import java.math.BigInteger
import java.security.MessageDigest

import api.server.StartMode.StartMode
import api.server.local.data.Data
import io.circe.generic.semiauto.deriveDecoder
import io.circe.{Decoder, Json}
import models.User

import scala.util.{Failure, Success, Try}


class Api

object Api {

  var user: User = new User(4, "Johnny Walker", "johnnywalker", "JSS1", "123456")
  def getIp: InetAddress = InetAddress.getLocalHost

  private def jsonify(text: String): Json = {
    io.circe.parser.parse(text).getOrElse(Json.Null)
  }
  private def md5( s: String): String = {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }

  implicit val decoderAss: Decoder[Assignment] = deriveDecoder[Assignment]
  implicit val decoderNote: Decoder[Note] = deriveDecoder[Note]
  implicit val decoderAssessmentTheory: Decoder[AssessmentTheory] = deriveDecoder[AssessmentTheory]
  implicit val decoderAssessmentObjective: Decoder[AssessmentObj] = deriveDecoder[AssessmentObj]

  case class Assignment(id: Int, sub: String, topic: String, max_score: Int, ref: String, submitted: Boolean, score: Int, date: String, file: String)
  case class Note(id: Int, sub: String, topic: String, ref: String, date: String, file: String)
  case class AssessmentTheory(sub: String, name: String, term: Int, ref: String, date: String, stampy: Double, submitted: Boolean, score: Double)
  case class AssessmentObj(sub: String, name: String, term: Int, ref: String, date: String, stampy: Double, submitted: Boolean, score: Double)

  def assignments: List[Assignment] = io.circe.parser.decode[List[Assignment]](requests.post(GET_ASSIGNMENTS, data = Map("user" -> user.username)).text).getOrElse(List(Assignment(0, "N/A", "N/A", 0, "N/A", submitted = false, 0, "N/A", "N/A")))
  def notes: List[Note] = io.circe.parser.decode[List[Note]](requests.post(GET_NOTES).text).getOrElse(List(Note(0, "N/A", "N/A", "N/A", "N/A", "N/A")))
  def theory: List[AssessmentTheory] = io.circe.parser.decode[List[AssessmentTheory]](requests.post(GET_ASSESSMENT_THEORY, data = Map("sid" -> s"${user.id}")).text).getOrElse(List(AssessmentTheory("N/A", "N/A", 0, "N/A", "N/A", 0, submitted = false, 0)))
  def objective: List[AssessmentObj] = io.circe.parser.decode[List[AssessmentObj]](requests.post(GET_ASSESSMENT_OBJ, data = Map("sid" -> s"${user.id}")).text).getOrElse(List(AssessmentObj("N/A", "N/A", 0, "N/A", "N/A", 0, submitted = false, 0)))

  private val API_KEY: String = md5("EhqJ@1jP")
  val NAME: String = "SchoolScape Student"
  val THIS_YEAR: String = Year.now.toString
  val SERVER: String = "https://school-scape-be.herokuapp.com"
  val AUTH_USER: String = s"$SERVER/student/auth"
  val CREATE_USER: String = s"$SERVER/student/create"
  val MAKE_SUGGESTION: String = s"$SERVER/drop/suggestion"
  val RESET_PASSWD: String = s"$SERVER/student/edit"
  val USER_DATA: String = s"$SERVER/student/data"
  lazy val GET_ASSIGNMENTS: String = s"$SERVER/${user._class}/assignments"
  lazy val GET_CLASSWORK: String = s"$SERVER/${user._class}/classworks"
  val SUBMIT_ASSIGNMENT: String = s"$SERVER/ass/submit"
  lazy val SUBMIT_OBJ: String = s"$SERVER/submit/exam-obj"
  lazy val GET_NOTES: String = s"$SERVER/${user._class}/notes"
  val EDIT_USER: String = s"$SERVER/student/edit"
  lazy val GET_ASSESSMENT_THEORY: String = s"$SERVER/${user._class}/exam-theory"
  lazy val GET_ASSESSMENT_OBJ: String = s"$SERVER/${user._class}/exam-obj"

  def modeDeterminant(): StartMode = {
    Try{
      requests.get(SERVER)
    } match {
      case Success(value) => StartMode.ONLINE
      case Failure(exception) => StartMode.OFFLINE
    }
  }
  def initUser(username: String): Unit = {
    Try(requests.post(Api.USER_DATA, data = Map("user" -> username, "key" -> API_KEY))) match {
      case Failure(exception) => //TODO: Figure out how to handle the exception from here
      case Success(response) =>
        this.user = User.from(jsonify(response.text).hcursor)
        Data.saveName(username)
    }
  }
  def initSequence(): Unit = {
    Data.setMode(modeDeterminant())
  }
}
