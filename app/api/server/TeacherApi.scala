package api.server

import api.server.Commons._
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

class TeacherApi

object TeacherApi {

  implicit val decoderAss: Decoder[Assignment] = deriveDecoder[Assignment]
  implicit val decoderNote: Decoder[Note] = deriveDecoder[Note]
  //date, file, id, max_score,
  case class Assignment(id: Long, sub: String, topic: String, max_score: Int, ref: String, term: Int, date: String, file: String, cls: String)
  case class Note(id: Long, sub: String, topic: String, ref: String, date: String, file: String, cls: String, term: Int)

  def assignments: List[Assignment] = io.circe.parser.decode[List[Assignment]](requests.post(MY_ASSIGNMENTS, data = Map("tid" -> s"${user.id}")).text).getOrElse(List(Assignment(0, "N/A", "N/A", 0, "N/A", 0, "N/A", "N/A", "N/A")))
  def notes: List[Note] = io.circe.parser.decode[List[Note]](requests.post(MY_NOTES, data = Map("tid" -> s"${user.id}")).text).getOrElse(List(Note(0, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", 0)))
  def subjects: List[String] = io.circe.parser.decode[List[String]](requests.get(MY_SUBJECTS).text).getOrElse(List("N/A"))

  val UPLOAD_NOTE: String = s"$SERVER/note/add"
  val UPLOAD_ASS: String = s"$SERVER/ass/add"
  val GET_SUBJECTS: String = s"$SERVER/subjects"
  lazy val MY_SUBJECTS: String = s"$SERVER/${user.username}/subjects"
  lazy val GET_ASSESSMENT_THEORY: String = s"$SERVER/${user.username}/exam-theory"
  lazy val GET_ASSESSMENT_OBJ: String = s"$SERVER/${user.username}/exam-obj"
  lazy val MY_ASSIGNMENTS: String = s"$SERVER/my/assignments"
  lazy val MY_NOTES: String = s"$SERVER/my/notes"


}
