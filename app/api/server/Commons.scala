package api.server

import java.math.BigInteger
import java.security.MessageDigest
import java.time.Year

import io.circe.Json
import models.{User, UserFactory}

import scala.util.Random

object Commons {
  var user: User = UserFactory.demoUser(Random.shuffle(List("teacher", "student")).head)
  var role: String = ""

  def updateRole(newRole: String): Unit = role = newRole.toLowerCase

  def flash(notice: String, ntype: String): List[(String, String)] = {
    List("notice" -> notice, "notice-type" -> s"alert-$ntype", "showing" -> "show")
  }

  private def md5( s: String): String = {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(s.getBytes)
    val bigInt = new BigInteger(1,digest)
    val hashedString = bigInt.toString(16)
    hashedString
  }

  def jsonify(text: String): Json = {
    io.circe.parser.parse(text).getOrElse(Json.Null)
  }

  val API_KEY: String = md5("EhqJ@1jP")
  val NAME: String = "SchoolScape"
  val THIS_YEAR: String = Year.now.toString
  val SERVER: String = "https://school-scape-be.herokuapp.com"
  val MAKE_SUGGESTION: String = s"$SERVER/drop/suggestion"
  val USER_DATA: String = s"$SERVER/$role/data"


  lazy val EDIT_USER: String = s"$SERVER/$role/edit"
  lazy val AUTH_USER: String = s"$SERVER/$role/auth"
  lazy val CREATE_USER: String = s"$SERVER/create"
  lazy val RESET_PASSWD: String = s"$SERVER/$role/edit"


}
