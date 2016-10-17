package com.difflang.controllers

import com.difflang.utilities.{FilterData, Pagination}
import play.modules.reactivemongo.json._
import javax.inject.Inject
import com.difflang.models.Hospital
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import com.difflang.services.HospitalRepositoryImpl


import reactivemongo.bson.{
 BSONDocument, BSONObjectID
}



import scala.concurrent.Await
import scala.concurrent.duration._




class HospitalController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller with MongoController with ReactiveMongoComponents {

  def hospitalService = new HospitalRepositoryImpl(reactiveMongoApi)

  /*def test(page:Int, limit:Int) = Action.async {
    implicit request =>
      val getHospitalCount = Await.result(hospitalService.getTotalHospital(),10 second)
      val pagination = new Pagination(page,limit,getHospitalCount)
      println("page = "+ page +" limit"+ limit +"Count "+ getTotalHospital)
      hospitalService.list(pagination).map(hospital => Ok(Json.obj("Data" -> Json.toJson(hospital),"PAGINATION"-> Json.toJson(pagination),"STATUS" -> "OK" )))
  }*/

  //TODO GET ALL HOSPITAL
  def findAll(page:Int , limit:Int,sort:String) = Action.async {
    implicit request =>
      val sortData = new FilterData(sort)
      val getCount = Await.result(hospitalService.getTotalHospital(),10 seconds)
      val pagination = new Pagination(page, limit, getCount)
      hospitalService.findAllHospital(pagination,sortData).map(hospital => Ok(Json.obj("Data" -> Json.toJson(hospital),"PAGINATION"-> Json.toJson(pagination),"STATUS" -> "DATA FOUND" )))
  }


  //TODO ADD HOSPITAL
  def  create = Action.async(BodyParsers.parse.json){
    implicit request =>
      val hospital = (request.body).as[Hospital]
      hospitalService.addHospital(hospital).map(result => Created)
  }

  //TODO GET HOSPITAL BY ID
  def findById(id: String) = Action.async {
    hospitalService.findHospitalById(BSONDocument("_id" -> BSONObjectID(id))).map(hospital => Ok(Json.toJson(hospital)))
  }

  //TODO GET HOSPITAL BY NAME
  def findByName(name:String) = Action.async{
    hospitalService.findHospitalByName(name).map(result => Ok(Json.toJson(result)))
  }

  //TODO UPDATE HOSPITAL
  def update(id: String) = Action.async(BodyParsers.parse.json) {
    { implicit request =>
      val hospital = (request.body).as[Hospital]
      val selector = BSONDocument("_id" -> BSONObjectID(id))
      hospitalService.updateHospital(selector, hospital).map(result => Accepted)
    }
  }

  //TODO DELETE HOSPITAL
  def delete(id: String) = Action.async {
    hospitalService.deleteHospital(BSONDocument("_id" -> BSONObjectID(id)))
      .map(result => Accepted)
  }

  //TODO GET TOTAL HOSPITAL
  def getTotalHospital = Action.async{
    hospitalService.getTotalHospital().map(result =>Ok(result.toString))
  }



}
