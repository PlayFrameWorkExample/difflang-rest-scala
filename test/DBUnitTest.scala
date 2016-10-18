import java.sql.Connection
import javax.sql.DataSource

import org.specs2.mock.Mockito
import play.api.mvc.Result
import play.api.test.PlaySpecification
import play.db.{ConnectionRunnable, ConnectionCallable, Database}


class DBUnitTest  extends PlaySpecification with Mockito {
  /*"Data base Connected" should{
      "WORK" in{
          def myString(str:String): String ={
             return str
          }
        myString("Hello") must equalTo("Me")


      }

    }*/


  /*
  "DATABASE CONNECTED" should {
    "PASSING" in{

      def doCheckConnection(url:Any): Unit ={
        val URL ="mongodb://dev:dev@ds049651.mlab.com:49651/difflang?authMode=scram-sha1"
        try {
          if (URL.isEmpty){
           println("PASS")
          }
          else
          {
            println("NOT PASS")
          }
        }

      }
     doCheckConnection("mongodb://dev:dev@ds049651.mlab.com:49651/difflang?authMode=scram-sha1")
    }
  }*/



  "COUNT NUMBER" should {
    "WORK" in {
      val numberOne: Int = 1
      val numberTwo: Int = 2
      val result = numberOne + numberTwo


      result must equalTo(3)

    }
  }
}
