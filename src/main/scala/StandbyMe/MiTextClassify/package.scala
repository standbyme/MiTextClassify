package StandbyMe

import com.baidu.aip.nlp.AipNlp

package object MiTextClassify {
  type Topic = String
  type Word = String
  type SimEmbeddingScore = Double
  type Score = Double
  type Probability = Double
  type WordWithProbability = (Word, Probability)

  val APP_ID = sys.env("APP_ID")
  val API_KEY = sys.env("API_KEY")
  val SECRET_KEY = sys.env("SECRET_KEY")
  val client = new AipNlp(APP_ID, API_KEY, SECRET_KEY)

  def wordSimEmbedding(word1: String, word2: String): Option[SimEmbeddingScore] = {
    Thread.sleep(200)
    try {
      val res = client.wordSimEmbedding(word1, word2, null)
      val score = res.getDouble("score")
      Some(score)
    } catch {
      case _ => None
    }
  }
}
