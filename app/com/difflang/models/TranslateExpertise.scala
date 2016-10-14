package com.difflang.models

import play.api.libs.json._

/**
  * Created by acer on 10/11/2016.
  */
case class TranslateExpertise
(
  tranExpert: String
)

object TranslateExpertise {

  implicit object LanguageWrites extends OWrites[TranslateExpertise] {
    def writes(exp: TranslateExpertise): JsObject = Json.obj(
      "EXPERT" -> exp.tranExpert
    )
  }

  implicit object LanguageReads extends Reads[TranslateExpertise] {
    def reads(json: JsValue): JsResult[TranslateExpertise] = json match {
      case obj: JsObject => try {

        val exp = (obj \ "EXPERT").as[String]
        JsSuccess(TranslateExpertise(exp))

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }

}
