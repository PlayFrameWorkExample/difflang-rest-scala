package com.difflang.models

import play.api.libs.json._

/**
  * Created by acer on 10/12/2016.
  */

//Case Class JSON
case class Mobile (
             phone:String
             )


//TODO CREATE BSONDocument FORMATE
object  Mobile{

  implicit object  MobileWriter extends OWrites[Mobile]{
    def writes(mobile: Mobile): JsObject = Json.obj(
      "PHONE" -> mobile.phone
    )
  }

  implicit object MobileReade extends Reads[Mobile]{
    override def reads(json: JsValue): JsResult[Mobile] = json match{
      case obj: JsObject => try {
        val phone = (obj \ "PHONE").as[String]
          JsSuccess(Mobile( phone))
      }
      catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }
}