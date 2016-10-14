package com.difflang.models

import scala.collection.mutable.ListBuffer
import play.api.libs.json._
/**
  * Created by acer on 10/11/2016.
  */
case class Translator
(
  firstname: String,
  lastname: String,
  companyName: String,
  email: String,
  phone: String,
  address: String,
  state: String,
  zipcode: String,
  targetLanguage: ListBuffer[Language],  // TODO INJECT LIST OF LANGUAGE
  nativeLanguage: String,
  officialDegree: Boolean,
  translateSince: Int,
  serviceTranslate: ListBuffer[ServiceTranslate],  // TODO INJECT LIST OF SERVICE TRANSLATE
  translateExpertise: ListBuffer[TranslateExpertise] // TODO INJECT LIST OF TRANSLATE EXPERTISE
)

//TODO CREATE BSONDocument FORMATE
object Translator
{


  implicit object TranslatorWrite extends OWrites[Translator]{
    def writes(translator: Translator): JsObject = Json.obj(

      "FIRSTNAME" -> translator.firstname,
      "LASTNAME" -> translator.lastname,
      "COMPANYNAME" -> translator.companyName,
      "EMAIL" -> translator.email,
      "PHONE" -> translator.phone,
      "ADDRESS" -> translator.address,
      "STATE" -> translator.state,
      "ZIPCODE" -> translator.zipcode,
      "TARGETLANGUAGE" -> (translator.targetLanguage),
      "NATIVELANGUAGE" -> translator.nativeLanguage,
      "OFFICALDEGREE" -> translator.officialDegree,
      "TRANSLATESINCE" -> translator.translateSince,
      "SERVICETRANSLATE" -> Json.arr(translator.serviceTranslate),
      "TRANSLATEEXPRETISE" -> Json.arr(translator.translateExpertise)
    )
  }

  implicit object TranslatorReads extends Reads[Translator] {
    def reads(json: JsValue): JsResult[Translator] = json match {
      case obj: JsObject => try {

        val firstname = (obj \ "FIRSTNAME").as[String]
        val lastname = (obj \ "LASTNAME").as[String]
        val companyName = (obj \ "COMPANYNAME").as[String]
        val email = (obj \ "EMAIL").as[String]
        val phone = (obj \ "PHONE").as[String]
        val address = (obj \ "ADDRESS").as[String]
        val state = (obj \ "STATE").as[String]
        val zipcode = (obj \ "ZIPCODE").as[String]
        val targetLanguage = (obj \ "TARGETLANGUAGE").as[ListBuffer[Language]]
        val nativeLanguage = (obj \ "NATIVELANGUAGE").as[String]
        val officialDegree = (obj \ "OFFICALDEGREE").as[Boolean]
        val translateSince = (obj \ "TRANSLATESINCE").as[Int]
        val serviceTranslate = (obj \ "SERVICETRANSLATE").as[ListBuffer[ServiceTranslate]]
        val translateExpertise = (obj \ "TRANSLATEEXPRETISE").as[ListBuffer[TranslateExpertise]]
        JsSuccess(Translator(firstname, lastname, companyName, email, phone, address, state, zipcode, targetLanguage, nativeLanguage, officialDegree, translateSince, serviceTranslate, translateExpertise))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }


}