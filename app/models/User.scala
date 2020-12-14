package models

trait User {
  def id: Int
  def username: String
  def pass: String
  def _class: String
  def role: String
  def name: String
}