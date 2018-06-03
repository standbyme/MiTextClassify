package StandbyMe.MiTextClassify

object main extends App {

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
    ("医药",0.34692),
    ("药店",0.0579149),
    ("药品",0.0359799),
    ("药房",0.0339464),
    ("处方",0.0258046),
    ("零售",0.0198529),
    ("国药",0.0195491),
    ("回扣",0.0168899),
    ("健康",0.0164062),
    ("连锁",0.0152807)

  )

  println(word_with_probability_array_handler(word_with_probability_array))
}
