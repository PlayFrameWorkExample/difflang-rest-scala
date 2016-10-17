package com.difflang.controllers

import javax.inject.Inject


import com.difflang.models.User
import com.difflang.utilities.{Pagination, FilterData}
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import com.difflang.services.UserRepositoryImpl

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UserController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller with MongoController with ReactiveMongoComponents{

  def userRepo=new UserRepositoryImpl(reactiveMongoApi)

  def findAll(page:Int, size:Int,sort:String)= Action.async{
    implicit request=>
      val getCount =Await.result(userRepo.getTotalUser(),10 seconds)
      val userFilter = new FilterData(sort)
      val pagination = new Pagination(page,size,getCount)
      userRepo.find(pagination,userFilter).map(users => Ok(Json.obj("Data" -> Json.toJson(users),"PAGINATION"-> Json.toJson(pagination),"STATUS" -> "DATA FOUND" )))
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
