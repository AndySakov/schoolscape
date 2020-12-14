package api.server

import api.server.Commons._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder


class StudentApi

object StudentApi {

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

  lazy val GET_ASSIGNMENTS: String = s"$SERVER/${user._class}/assignments"
  lazy val GET_CLASSWORK: String = s"$SERVER/${user._class}/classworks"
  val SUBMIT_ASSIGNMENT: String = s"$SERVER/ass/submit"
  lazy val SUBMIT_OBJ: String = s"$SERVER/submit/exam-obj"
  lazy val GET_NOTES: String = s"$SERVER/${user._class}/notes"
  lazy val GET_ASSESSMENT_THEORY: String = s"$SERVER/${user._class}/exam-theory"
  lazy val GET_ASSESSMENT_OBJ: String = s"$SERVER/${user._class}/exam-obj"

}
