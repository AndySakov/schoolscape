package models


import api.server.Commons._
import io.circe.HCursor

import scala.util.{Failure, Success, Try}

object UserFactory {
  def from(obj: HCursor): User = {
    val id = obj.get[Int]("id").getOrElse(0)
    val name = obj.get[String]("name").getOrElse("N/A")
    val user = obj.get[String]("user").getOrElse("N/A")
    val cls = obj.get[String]("class").getOrElse("N/A")
    val pass = obj.get[String]("pass").getOrElse("N/A")
    val role = obj.get[String]("role").getOrElse("N/A")
    role match {
      case "student" => new Student(id,name,user,cls,pass)
      case "teacher" => new Teacher(id,name,user,pass)
    }
  }

  def initUser(username: String): Unit = {
    Try(requests.post(USER_DATA, data = Map("user" -> username, "key" -> API_KEY))) match {
      case Failure(exception) => throw exception
      case Success(response) =>
        user = UserFactory.from(jsonify(response.text).hcursor)
    }
  }

  def demoUser(role: String): User = {
    role match {
      case "student" => new Student(4, "Johnny Walker", "johnnywalker", "JSS1", "123456")
      case "teacher" => new Teacher(4, "Johnny Walker", "johnnywalker", "123456")
    }
  }
}
