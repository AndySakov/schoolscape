package models

import io.circe.HCursor

class User(val id: Int, name: String, user: String, cls: String, _pass: String) {
  def username: String = user
  def fullname: String = name
  def _class: String = cls
  def pass: String = _pass
}
object User{
  def from(obj: HCursor): User = {
    val id = obj.get[Int]("id").getOrElse(0)
    val name = obj.get[String]("name").getOrElse("N/A")
    val user = obj.get[String]("user").getOrElse("N/A")
    val cls = obj.get[String]("class").getOrElse("N/A")
    val pass = obj.get[String]("pass").getOrElse("N/A")
    new User(id,name,user,cls,pass)
  }
}
