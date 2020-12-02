package controllers

import java.net.InetAddress
import java.nio.file.{Files, Paths}

import api.server.Api
import api.server.local.data.Data
import javax.inject._
import play.api.mvc._
import sys.process._
import scala.util.{Failure, Success, Try}


@Singleton
class RenderController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    /**
      * This controller handles all the get requests and page rendering in the application
      * @author Obafemi Teminife
      * @group controllers
      */
    val ip: InetAddress = Api.getIp

  def flash(notice: String, mode: String): List[(String, String)] = {
    List("notice" -> notice, "notice-type" -> s"alert-$mode", "showing" -> "show")
  }
  def err500: Result = InternalServerError(views.html.err.InternalServerError())
  def err404: Result = NotFound(views.html.err.NotFound())
  def apiErr: Result = InternalServerError(views.html.err.HostUnreachable())

  def validateSession(x: Result): Result = {
    Try(Api.user.username) match {
      case Failure(exception) => Forbidden(views.html.err.Error(403, "Session expired!"))
      case Success(value) => x
    }
  }
  

  //TODO: Add sid verification and session validation
  def dashboard(route: String): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      route match {
        case "home" =>
          validateSession(Ok(views.html.home()))
        case _ => err404
      }
    }
  }
  def examReplyTheory(x: Int): Action[AnyContent] = Action {
    implicit request => {
      validateSession(Ok(views.html.school.exam_reply_theory(x)))
    }
  }
  def examReplyObjective(x: Int): Action[AnyContent] = Action {
    implicit request => {
      validateSession(Ok(views.html.school.exam_reply_obj(x)))
    }
  }
  def school(path: String): Action[AnyContent] = Action{
    implicit request: Request[AnyContent] => {
      path match {
        case "notes" => validateSession(Ok(views.html.school.notes()))
        case "classwork" => validateSession(Ok(views.html.school.classwork()))
        case "assignments" => validateSession(Ok(views.html.school.assignments()))
        case "assessments" => validateSession(Ok(views.html.school.assessments()))
        case _ => err404
      }
    }
  }
  def account(route: String): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      route match {
        case "login" => Ok(views.html.login())
        case "create" => Ok(views.html.signup())
        case "profile" => validateSession(Ok(views.html.profile()))
        case "logout" =>
          Data.flushName()
          Redirect("/account/login").withNewSession
        case _ => err404
      }
    }
  }

  def redirect: Action[AnyContent] = Action{
    implicit request: Request[AnyContent] => {
      Redirect("/account/login").withNewSession
    }
  }

  def error(err: String): Action[AnyContent] = Action{
    implicit request: Request[AnyContent] => {
      err match {
        case "api/connect_failure" => apiErr
        case _ => err500
      }
    }
  }
  def notFound(unknown: String): Action[AnyContent] = Action{
    implicit request: Request[AnyContent] => {
      err404
    }
  }

  def success(msg: String): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] => {
      msg match {
        case "signup" => Redirect("/account/login").flashing(flash("Signup Successfull!!!", "success"): _*)
        case _ => err404
      }
    }
  }

  def shutdown: Action[AnyContent] = Action {
    request => {
      val rpid = s"${sys.env("SCHOOLSCAPE_HOME")}student\\bin\\RUNNING_PID"
      if(Files.exists(Paths.get(rpid))){
        val pid = Files.readAllLines(Paths.get(rpid)).get(0)
        s"cmd /c taskkill /F /PID $pid".!!
      }
      System.exit(0)
      Ok("Shutting down...")
    }
  }
}
