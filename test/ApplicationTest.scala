

import org.specs2.mock.Mockito
import play.api.libs.ws._
import play.api.test._


class ApplicationTest extends  PlaySpecification with Mockito{

  "Application" should {
    "be reachable" in new WithServer {
      val response = await(WS.url("http://localhost:" + port).get()) //1

      response.status must equalTo(OK) //2
      response.body must contain("Your new application is ready") //3
    }
  }

}
