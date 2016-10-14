package com.difflang.controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import com.difflang.services.TranslatorRepoImpl
import com.difflang.models.Translator
import play.modules.reactivemongo.json._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by acer on 10/11/2016.
  */
class TranslatorController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller
  with MongoController with ReactiveMongoComponents{

  def translatorService = new TranslatorRepoImpl(reactiveMongoApi)

  // TODO ADD ALL TRANSLATOR
  def create = Action.async(BodyParsers.parse.json) {
    implicit request =>
    val translator = (request.body).as[Translator]
      translatorService.save(translator).map(result => Created)
  }

  // TODO GET ALL TRANSLATOR
  def findAll = Action.async {
    implicit request =>
      translatorService.find().map(translator => Ok(Json.toJson(translator)))
  }

  // TODO FIND TRANSLATOR BY ID
  def findById(id: String) = Action.async {
    translatorService.select(BSONDocument("_id" -> BSONObjectID(id))).map(translator => Ok(Json.toJson(translator)))
  }

  // TODO UPDATE TRANSLATOR
  def update(id: String) = Action.async(BodyParsers.parse.json) {
    {
      implicit request =>
      val translator = (request.body).as[Translator]
      val selector = BSONDocument("_id" -> BSONObjectID(id))
      translatorService.update(selector, translator).map(result => Accepted)
    }
  }

  // TODO DELETE TRANSLATOR
  def delete(id: String) = Action.async {
    translatorService.remove(BSONDocument("_id" -> BSONObjectID(id)))
      .map(result => Accepted)
  }
}
