package models

class Student(val _id: Int, fullname: String, user: String, cls: String, _pass: String) extends User {
  def id: Int = _id
  def username: String = user
  def name: String = fullname
  def _class: String = cls
  def pass: String = _pass
  def role: String = "student"
}
