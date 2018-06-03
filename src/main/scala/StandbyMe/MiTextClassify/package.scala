package StandbyMe

import com.baidu.aip.nlp.AipNlp

package object MiTextClassify {
  val APP_ID = sys.env("APP_ID")
  val API_KEY = sys.env("API_KEY")
  val SECRET_KEY = sys.env("SECRET_KEY")
  val client = new AipNlp(APP_ID, API_KEY, SECRET_KEY)

  def wordSimEmbedding(word1: String, word2: String): Option[Double] = {
    try {
      val res = client.wordSimEmbedding(word1, word2, null)
      val error_code = res.optInt("error_code", 0)
      if (error_code == 18) {
        println("QPS")
        Thread.sleep(3000)
        wordSimEmbedding(word1, word2)
      } else {
        val score = res.getDouble("score")
        Some(score)
      }
    } catch {
      case _ => None
    }
  }
}
