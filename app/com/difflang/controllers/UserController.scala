package com.difflang.controllers

import javax.inject.Inject


import com.difflang.models.User
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import com.difflang.services.UserRepositoryImpl
import views.html.helper.select
import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller with MongoController with ReactiveMongoComponents{

  def userRepo=new UserRepositoryImpl(reactiveMongoApi)

  def findAll= Action.async{
    implicit request=>
      userRepo.find().map(users => Ok(Json.toJson(users)))
  }

  def findById(id: String) = Action.async {
    userRepo.select(BSONDocument("_id" -> BSONObjectID(id))).map(translator => Ok(Json.toJson(translator)))
  }

  def create = Action.async(BodyParsers.parse.json){ implicit request =>
     val user =(request.body).as[User]
    userRepo.save(user).map(result => Accepted)

  }

  def delete(id: String) = Action.async{
    userRepo.remove(BSONDocument("_id" -> BSONObjectID(id))).map(result => Accepted)
  }

  def update(id: String)= Action.async(BodyParsers.parse.json){
    {
      implicit request =>
        val user =(request.body).as[User]
        val selector =BSONDocument("_id" -> BSONObjectID(id))

        val modifier= (user)
        userRepo.update(selector, modifier).map( result => Accepted)
    }
  }

  def getTotalUser = Action.async{implicit request =>
    userRepo.getTotalUser().map(result => Ok(result.toString))
  }
}
