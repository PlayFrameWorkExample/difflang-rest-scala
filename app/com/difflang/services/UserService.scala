package com.difflang.services

import javax.inject.Inject

import play.modules.reactivemongo.json._
import com.difflang.models.User
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by acer on 10/12/2016.
  */
trait UserRepository {

  //FIND ALL USER
  def find()(implicit ec:ExecutionContext): Future[List[JsObject]]


  def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def save(document: User)(implicit ec: ExecutionContext):Future[WriteResult]

  def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def update(id:BSONDocument, document: User)(implicit ec:ExecutionContext):Future[WriteResult]


  def getTotalUser()(implicit ec:ExecutionContext):Future[Int]
}


class UserRepositoryImpl @Inject()(reactiveMongoApi: ReactiveMongoApi) extends UserRepository{

  def collection (implicit ec: ExecutionContext)= reactiveMongoApi.database.map(db=> db.collection[JSONCollection]("USERS"))

  override def find()(implicit ec:ExecutionContext): Future[List[JsObject]]={
    val queryBuilder= collection.map(_.find(Json.obj()))
    val cursor=queryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List]())
  }

  override def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  override def save(document: User)(implicit ec: ExecutionContext): Future[WriteResult]={
    collection.flatMap(_.insert(document))
  }

  override def remove(id:BSONDocument)(implicit ec: ExecutionContext):Future[WriteResult]={
    collection.flatMap(_.remove(id))
  }

  override def update(id:BSONDocument, document: User)(implicit ec: ExecutionContext):Future[WriteResult]={
    collection.flatMap(_.update(id, document))
  }


  override def getTotalUser()(implicit ec:ExecutionContext):Future[Int]={
    val totalUser = collection.flatMap(_.count())
    totalUser
  }
}