package controllers

import java.io.FileNotFoundException

import data.FileManager
import javax.inject.{Inject, Singleton}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

@Singleton
class UDataController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  /**
    * This controller handles all the data management in the application
    * Mainly for adding generated data to the UI
    * @author Obafemi Teminife
    * @note Might be broken down into several components later
    * @group controllers
    */

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def download(name: String): Action[AnyContent] = Action{
    val res = Map(
      "pdf" -> "document",
      "exe" -> "executable",
      "docx" -> "document",
      "msi" -> "executable",
      "jpg" -> "image",      "png" -> "image",
      "avi" -> "media",
      "mp4" -> "media",
      "mp3" -> "audio",
      "flv" -> "media",
      "txt" -> "document",
      "doc" -> "document",
      "pptx" -> "document",
      "xlsx" -> "document"
    )
    try{
      val ext = name.split("").splitAt(name.split("").lastIndexOf("."))._2.mkString.tail
      val file = FileManager.file(name, res.get(ext))
//      val path: java.nio.file.Path      = file.toPath
//      val source: scaladsl.Source[ByteString, Future[IOResult]] = FileIO.fromPath(path)
//      Result(
//        header = ResponseHeader(200, Map.empty),
//        body = HttpEntity.Streamed(source, Some(Files.size(path).toLong), Some("application/file"))
//      )
      Ok.sendFile(file)

    }catch{
      case o:FileNotFoundException =>
        Results.NotFound(o.getMessage)
      case ex:Exception =>
        Results.InternalServerError(ex.getMessage)
    }
  }
}
