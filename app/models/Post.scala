package kcacup
package models

import com.novus.salat.annotations._
import java.util.Date

case class Post(

  @Key("_id") id: Int,

  name: String,

  text: String,

  createdAt: Date

)
//) extends Chronological[Post] {

//}
