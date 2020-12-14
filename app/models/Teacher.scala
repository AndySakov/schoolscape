package models

class Teacher(val _id: Int, fullname: String, user: String, _pass: String) extends User {
  def id: Int = _id
  def username: String = user
  def name: String = fullname
  def pass: String = _pass
  override def _class: String = "N/A"
  override def role: String = "teacher"
}
