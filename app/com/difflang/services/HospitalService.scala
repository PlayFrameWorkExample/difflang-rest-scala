package com.difflang.services

import javax.inject.Inject
import com.difflang.utilities.{FilterData, Pagination}

import com.difflang.models.Hospital
import play.api.libs.json.{Json, JsObject}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import scala.concurrent.{Future, ExecutionContext}
import play.modules.reactivemongo.json._


trait HospitalRepository {

  def findAllHospital(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]
  def findHospitalById(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]
  def findHospitalByName(name:String)(implicit  ec:ExecutionContext):Future[List[JsObject]]
  def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
  def deleteHospital(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
  def updateHospital(id: BSONDocument, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
  def getTotalHospital()(implicit ec:ExecutionContext):Future[Int]

}

class HospitalRepositoryImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends HospitalRepository{

  def collection (implicit ec: ExecutionContext)  = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("HOSPITALS"))

 /* override def list(pagination: Pagination,sort:Int)(implicit ec:ExecutionContext): Future[List[JsObject]] ={
    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.offset)).sort(Json.obj("NAME" -> sort ))  )
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.limit))
  }*/


  //TODO GET ALL HOSPITAL
  override def findAllHospital(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {

    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.KEY -> sort.VALUE)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))
   /* val genericeQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.KEY -> sort.VALUE)))
    val cursor = genericeQueryBuilder.map(_.cursor[JsObject](ReadPreference.primary))
    cursor.flatMap(_.collect[List](pagination.Size))*/
  }

  //TODO GET HOSPITAL BY ID
  override def findHospitalById(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  //TODO FIND HOSPITAL BY NAME
  override def  findHospitalByName(name:String)(implicit ec:ExecutionContext):Future[List[JsObject]]={
    val genericQueryBuilder = collection.map(_.find(Json.obj("NAME" -> name)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List]())
  }

  //TODO ADD HOSPITAL
  override def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(hospital))
  }

  //TODO DELETE HOSPITAL
  override def deleteHospital(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  //TODO UPDATE HOSPITAL
  override def updateHospital(id: BSONDocument, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

  //TODO GET TOTAL HOSPITAL
  override def getTotalHospital()(implicit ec:ExecutionContext):Future[Int] = {
      collection.flatMap(_.count())
  }
}