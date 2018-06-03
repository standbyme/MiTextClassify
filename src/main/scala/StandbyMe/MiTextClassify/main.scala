package StandbyMe.MiTextClassify

object main extends App {
  type Topic = String
  type Word = String
  type SimEmbeddingScore = Double
  type Score = Double
  type Probability = Double
  type WordWithProbability = (Word, Probability)
  val topics = Array("娱乐", "家居", "健康", "国际", "教育", "社会", "游戏", "旅行", "时政", "民生", "科学", "科技", "财经", "房产", "体育", "历史", "时尚", "军事", "美食", "汽车", "星座", "情感", "育儿", "文化", "天气", "宗教")
  val threshold = 0.2

  def wordSimEmbeddingAdapter(word_with_probability: WordWithProbability, topic: Topic): Option[(WordWithProbability, SimEmbeddingScore)] = {
    val word = word_with_probability._1
    wordSimEmbedding(word, topic).map((word_with_probability, _))
  }

  def word_with_probability_and_score_handler(word_with_probability_and_score: (WordWithProbability, SimEmbeddingScore)): Score = {
    val ((_, probability), score) = word_with_probability_and_score
    probability * score
  }

  def topic_handler(word_with_probability_array: Array[WordWithProbability])(topic: Topic): (Topic, SimEmbeddingScore) = {
    println(topic)

    val scores = word_with_probability_array.flatMap(wordSimEmbeddingAdapter(_, topic)).map(word_with_probability_and_score_handler)
    val average = scores.sum / scores.length
    println(scores.length)
    (topic, average)
  }

  def word_with_probability_array_handler(word_with_probability_array: Array[WordWithProbability]): Option[Topic] = {
    val temp = topics.map(topic_handler(word_with_probability_array)).sortBy(_._2)
    temp.foreach(println)
    val (topic, score) = temp.maxBy(_._2)
    println(s"topic $topic    score $score")
    if (score > threshold) Some(topic)
    else None
  }

  val word_with_probability_array = Array(
    ("投资", 0.0708659),
    ("资产", 0.0246454),
    ("报告", 0.0229653),
    ("证券", 0.0224541),
    ("净值", 0.0207581),
    ("组合", 0.0202303),
    ("期末", 0.0190425),
    ("管理人", 0.0163324),
    ("债券", 0.0155643),
    ("持有", 0.0149047)
  )

  println(word_with_probability_array_handler(word_with_probability_array))
}
