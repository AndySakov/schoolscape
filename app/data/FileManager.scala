package data

import java.io.File

object FileManager {
  def file(name: String, dir: Option[String]): File ={
    val pwd = new File(".").getAbsolutePath
    val pi = pwd.replace('.', ' ').replace('\\', '/').trim
    dir match {
      case Some(value) =>new File(s"$pi/$value/$name")
      case None => new File(s"$pi/unknown/$name")
    }
  }
}
