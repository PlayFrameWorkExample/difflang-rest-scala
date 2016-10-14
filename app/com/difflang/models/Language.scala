package com.difflang.models

import play.api.libs.json._

/**
  * Created by acer on 10/11/2016.
  */
case class Language
(
  language: String
)

object Language {

  implicit object LanguageWrites extends OWrites[Language] {
    def writes(lang: Language): JsObject = Json.obj(
      "LANGUAGE" -> lang.language
    )
  }

  implicit object LanguageReads extends Reads[Language] {
    def reads(json: JsValue): JsResult[Language] = json match {
      case obj: JsObject => try {

        val lang = (obj \ "LANGUAGE").as[String]
        JsSuccess(Language(lang))

      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }

}
