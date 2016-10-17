package com.difflang.services

import javax.inject.Inject

import com.difflang.utilities.{FilterData, Pagination}
import play.modules.reactivemongo.json._
import com.difflang.models.User
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

trait UserRepository {


  def find(pagination: Pagination,filterData: FilterData)(implicit ec:ExecutionContext): Future[List[JsObject]]
  def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]
  def save(document: User)(implicit ec: ExecutionContext):Future[WriteResult]
  def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
  def update(id:BSONDocument, document: User)(implicit ec:ExecutionContext):Future[WriteResult]
  def getTotalUser()(implicit ec:ExecutionContext):Future[Int]
}


class UserRepositoryImpl @Inject()(reactiveMongoApi: ReactiveMongoApi) extends UserRepository{
  def collection (implicit ec: ExecutionContext)= reactiveMongoApi.database.map(db=> db.collection[JSONCollection]("USERS"))

  override def find(pagination: Pagination,filterData: FilterData)(implicit ec:ExecutionContext): Future[List[JsObject]]={
    val queryBuilder= collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(filterData.KEY ->filterData.VALUE)))
    val cursor=queryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))
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