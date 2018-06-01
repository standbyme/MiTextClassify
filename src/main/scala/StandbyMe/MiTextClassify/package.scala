package StandbyMe

import com.baidu.aip.nlp.AipNlp

package object MiTextClassify {
  val APP_ID = sys.env("APP_ID")
  val API_KEY = sys.env("API_KEY")
  val SECRET_KEY = sys.env("SECRET_KEY")
  val client = new AipNlp(APP_ID, API_KEY, SECRET_KEY)

  def wordSimEmbedding(word1: String, word2: String): Option[Double] = {
    try {
      val score = client.wordSimEmbedding(word1, word2, null).getDouble("score")
      Some(score)
    } catch {
      case _ => None
    }
  }
}
