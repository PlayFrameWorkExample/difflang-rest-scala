package com.difflang.utilities

import play.api.libs.json.{Json, JsObject, OWrites}


case class FilterData(
                 sort:String
                 ){
                  var KEY: String = sort.toUpperCase()
                  var VALUE: Int = 1

                  if(sort.contains('-'))
                  {
                    KEY = sort.trim.toUpperCase.split('-')(1)
                    VALUE = -1
                  }
}
