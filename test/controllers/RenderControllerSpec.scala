package controllers

import org.scalatestplus.play.PlaySpec
import play.api.mvc.{Result, Results}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RenderControllerSpec extends PlaySpec with Results{
  "Login page" should {
    "be valid" in {
      val controller = new RenderController(Helpers.stubControllerComponents())
      val results: Future[Result] = controller.account("login").apply(FakeRequest())
      results.map(x => x mustBe Results.Ok)

    }
  }
}
