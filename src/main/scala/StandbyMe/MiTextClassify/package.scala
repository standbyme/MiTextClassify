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

  val topics = Seq("娱乐", "家居", "健康", "国际", "教育", "社会", "游戏", "旅行", "时政", "民生", "科学", "科技", "财经", "房产", "体育", "历史", "时尚", "军事", "美食", "汽车", "星座", "情感", "育儿", "文化", "天气", "宗教")
  val threshold = 0.003

  private def wordSimEmbeddingAdapter(wordWithProbability: WordWithProbability, topic: Topic): Option[(WordWithProbability, SimEmbeddingScore)] = {
    val word = wordWithProbability._1
    wordSimEmbedding(word, topic).map((wordWithProbability, _))
  }

  private def wordWithProbability_and_score_handler(wordWithProbability_and_score: (WordWithProbability, SimEmbeddingScore)): Score = {
    val ((_, probability), score) = wordWithProbability_and_score
    probability * score
  }

  private def topic_handler(wordWithProbability_seq: Seq[WordWithProbability])(topic: Topic): (Topic, Score) = {
//    println(topic)

    val scores = wordWithProbability_seq.flatMap(wordSimEmbeddingAdapter(_, topic)).map(wordWithProbability_and_score_handler)
    val average = scores.sum / scores.length
//    println(scores.length)
    (topic, average)
  }

  def wordWithProbability_seq_handler(wordWithProbability_seq: Seq[WordWithProbability]): Seq[Topic] = {
    val temp = topics.map(topic_handler(wordWithProbability_seq))
//    temp.sortBy(_._2).foreach(println)
    temp.withFilter(_._2 > threshold).map(_._1)
  }
}
