package StandbyMe

import com.baidu.aip.nlp.AipNlp

package object MiTextClassify {
  val APP_ID = sys.env("APP_ID")
  val API_KEY = sys.env("API_KEY")
  val SECRET_KEY = sys.env("SECRET_KEY")
  val client = new AipNlp(APP_ID, API_KEY, SECRET_KEY)
}
