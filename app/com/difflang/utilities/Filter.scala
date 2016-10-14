package com.difflang.utilities

import play.api.libs.json.{Json, JsObject, OWrites}


class Filter(name:Int){
  var NAME:Int = name
  var KEY_NAME :Int= -1

}
object  Filter{
  implicit object  FilterWrite extends  OWrites[Filter]
  {
    def writes(filter: Filter):JsObject = Json.obj(
      "NAME"->filter.NAME

    )
  }

}