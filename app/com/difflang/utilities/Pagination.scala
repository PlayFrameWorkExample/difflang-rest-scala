package com.difflang.utilities

import com.difflang.models._
import play.api.libs.json._
import reactivemongo.api.QueryOpts


/*
* PAGINATION
* */

class  Pagination(var page:Int,var limit:Int, totalCount:Int)  extends QueryOpts{

  var Page:Int  = page
  var Limit:Int = limit
  var TotalCount = totalCount
  var TotalPage:Double = 0
  if (TotalPage == 0){
    TotalPage =1
  } else
    {
      TotalPage =Math.ceil(totalCount / limit)
    }

  var offset = (page-1) * limit
    skip((page-1)*limit)
    batchSize(limit)



 /* def getPage={
    page
  }

  def nextPage={
    this.page+1
  }

  def previousPage={
    this.page -1
  }

 def hasPreviousPage={
    this.previousPage >=1 ? true | false
  }

  def haveNextPage={
    this.nextPage <= this.totalPages ? true | false
  }

  def totalPages={
    Math.ceil( TotalCount / limit);
  }


  def setPage( currentPage :Int)= {
    this.page = currentPage
    this.offset
  }

  def getLimit={
    limit
  }

  def setLimit( limit : Int)={
    this.limit = limit
  }

  def getTotalCount ={
    TotalCount
  }
  def setTotalCount(totalCount:Int) ={
     this.TotalCount==totalCount
  }

  def setTotalPage={
    totalPages
  }

  def getTotalPage(totalPages:Int) ={
    this.totalPages == totalPages
  }

  def offset={
    this.offset = ( page-1 ) * limit
  }

 def getOffset={
    this.offset
  }

  def setOffset(offset:Int): Unit ={
    this.offset = offset
  }

  def Pagination(page:Int,  limit:Int, totalCount:Int, totalPage:Int)={
    this.page = page
    this.limit =limit
    this.TotalCount = totalCount
    this.TotalPage = totalPage
  }

  def Pagination(page:Int,limit:Int) ={
    this.page = page
    this.limit = limit
    this.TotalCount =0
    this.TotalPage =0
  }

  def CalculationPage(page: Int,limit:Int, totalPage:Int): Unit ={
    val from = ((page - 1) * page) + 1
    val to = totalPage min (from + limit - 1)
    val totalPages = (totalPage / limit) + (if (totalPage % limit > 0) 1 else 0)
    (from, to, totalPages)
  }*/
}

object Pagination {
  implicit object  PaginationWrite extends  OWrites[Pagination]
  {
    def writes(pagination: Pagination):JsObject = Json.obj(
      "PAGE"->pagination.Page,
      "LIMIT"->pagination.Limit,
      "TOTALPAGE"->pagination.TotalPage,
      "TotalCount"->pagination.TotalCount
    )
  }
}