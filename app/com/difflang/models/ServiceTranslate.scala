package com.difflang.models

import play.api.libs.json._

/**
  * Created by acer on 10/11/2016.
  */
case class ServiceTranslate
(
  serviceTranslate: String
)

object ServiceTranslate {

  implicit object LanguageWrites extends OWrites[ServiceTranslate] {
    def writes(serviceTran: ServiceTranslate): JsObject = Json.obj(
      "SERVICE" -> serviceTran.serviceTranslate
    )
  }

  implicit object LanguageReads extends Reads[ServiceTranslate] {
    def reads(json: JsValue): JsResult[ServiceTranslate] = json match {
      case obj: JsObject => try {

        val serviceTran = (obj \ "SERVICE").as[String]
        JsSuccess(ServiceTranslate(serviceTran))

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }

}
