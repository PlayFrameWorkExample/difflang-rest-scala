package difflang.com.test.controllers.test

import org.scalatest.FunSuite


class UserControllerTest extends  FunSuite {

  test("Test Add User"){
    val x:Int  = 1
    val textBody: Int = 1
    assert(textBody ==x)
  }

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

}
