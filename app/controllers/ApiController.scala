package controllers

import java.net.{NoRouteToHostException, SocketException, SocketTimeoutException}

import api.server.Commons._
import api.server.{Api, NoneApi, StudentApi, TeacherApi}
import javax.inject.{Inject, Singleton}
import models.UserFactory
import play.api.Logger
import play.api.mvc._
import requests.{RequestFailedException, TimeoutException}

import scala.util.{Failure, Success, Try}
//noinspection DuplicatedCode
@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  /**
    * This controller handles all the data management in the application
    * Mainly for grabbing data from the api endpoints
    * @author Obafemi Teminife
    * @note Might be broken down into several components later
    * @group controllers
    */

  def errFlash(reason: String): (String, String) = {
    "reason" -> reason
  }

  val apiLogger: Logger = Logger(this.getClass)

  type Body = Option[Map[String, Seq[String]]]

  def body(implicit request: Request[AnyContent]): Body = {
    request.body.asFormUrlEncoded
  }

  var api: Api = NoneApi()

  def login: Action[AnyContent] = Action{
    implicit request: Request[AnyContent] => {
      body.map{
        args =>
          val user = args("user").head
          val pass = args("pass").head
          val role = args("role").head
          api = role match {
            case "student" => StudentApi()
            case "teacher" => TeacherApi()
            case "admin" => NoneApi()
            case _ => NoneApi()
          }
          Try(jsonify(requests.post(AUTH_USER, data = Map("user" -> user, "pass" -> pass)).text).hcursor) match {
            case Success(result) =>
              if(result.get[Boolean]("success").getOrElse(false)){
                UserFactory.initUser(user, role.toLowerCase)
                Redirect("/dashboard/home").withNewSession.withSession("user" -> user)
              } else
                Redirect("/account/login").flashing(flash("Login Failed!!!", "danger"): _*)
            case Failure(exception) =>
              exception match {
                case _: NoRouteToHostException => Redirect("/error/api/connect_failure").flashing(errFlash("Server unreachable"))
                case _: SocketTimeoutException => Redirect("/error/api/connect_failure").flashing(errFlash("Socket connection timed out!"))
                case _: SocketException => Redirect("/error/api/connect_failure").flashing(errFlash("Socket related error!"))
                case _: TimeoutException => Redirect("/error/api/connect_failure").flashing(errFlash("Connection timed out"))
                case _ =>
                  apiLogger.error(_)
                  InternalServerError(views.html.err.InternalServerError())

              }
          }
      }.getOrElse(Redirect("/error/login/failure"))
    }
  }

  def signup: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      body.map {
        args => {
          val formData = Map("full" -> args("full").head, "user" -> args("user").head, "class" -> args("class").head, "pass" -> args("pass").head)
          Try(jsonify(requests.post(url = CREATE_USER, data = formData).text).hcursor) match {
            case Failure(exception) => exception match {
              case _: NoRouteToHostException => Redirect("/error/api/connect_failure").flashing(errFlash("Server unreachable"))
              case _: SocketTimeoutException => Redirect("/error/api/connect_failure").flashing(errFlash("Socket connection timed out!"))
              case _ =>
                apiLogger.error(_)
                InternalServerError(views.html.err.InternalServerError())
            }
            case Success(result) =>
              if(result.get[Boolean]("success").getOrElse(false)) Redirect("/success/signup")
              else Redirect("/account/create").flashing(flash(result.get[String]("message").getOrElse("Signup Failed"), "danger"): _*)
          }
        }
      }.getOrElse(Redirect("/error/login/failure"))
    }
  }
  def editProfile: Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      body.map {
        args => {
          val formData = Map("oldUser" -> user.username, "name" -> args("name").head, "user" -> args("user").head, "pass" -> args("pass").head)
          Try(jsonify(requests.post(url = EDIT_USER, data = formData).text).hcursor) match {
            case Failure(exception) => exception match {
              case _: NoRouteToHostException => Redirect("/error/api/connect_failure")
              case _: SocketTimeoutException => Redirect("/error/api/connect_failure")
              case _: RequestFailedException => Redirect("/acccount/profile").flashing(flash("Unable to edit account!", "danger"): _*)
              case _ =>
                apiLogger.error(_)
                InternalServerError(views.html.err.InternalServerError())
            }
            case Success(_) =>
              UserFactory.initUser(args("user").head, role)
              Redirect("/account/login").withNewSession.flashing(flash("Edited account successfully!", "success"): _*)
          }
        }
      }.getOrElse(Redirect("/account/profile").flashing(flash("Unable to edit account!", "danger"): _*))
    }
  }
}