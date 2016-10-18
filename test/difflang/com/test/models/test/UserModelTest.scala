package difflang.com.test.models.test

import org.scalatest.FunSuite

class UserModelTest extends FunSuite{

  case class User (
                    firstName:String,
                    lastName:String,
                    email:String,
                    address:String,
                    country:String,
                    state:String,
                    city:String,
                    zipCode:String,
                    phone:String
                  )


  test("User Test"){

  }
}
