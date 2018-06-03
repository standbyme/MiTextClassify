package StandbyMe.MiTextClassify

object main extends App {

  val topics = Seq("娱乐", "家居", "健康", "国际", "教育", "社会", "游戏", "旅行", "时政", "民生", "科学", "科技", "财经", "房产", "体育", "历史", "时尚", "军事", "美食", "汽车", "星座", "情感", "育儿", "文化", "天气", "宗教")
  val threshold = 0.003

  def wordSimEmbeddingAdapter(wordWithProbability: WordWithProbability, topic: Topic): Option[(WordWithProbability, SimEmbeddingScore)] = {
    val word = wordWithProbability._1
    wordSimEmbedding(word, topic).map((wordWithProbability, _))
  }

  def wordWithProbability_and_score_handler(wordWithProbability_and_score: (WordWithProbability, SimEmbeddingScore)): Score = {
    val ((_, probability), score) = wordWithProbability_and_score
    probability * score
  }

  def topic_handler(wordWithProbability_seq: Seq[WordWithProbability])(topic: Topic): (Topic, Score) = {
    println(topic)

    val scores = wordWithProbability_seq.flatMap(wordSimEmbeddingAdapter(_, topic)).map(wordWithProbability_and_score_handler)
    val average = scores.sum / scores.length
    println(scores.length)
    (topic, average)
  }

  def wordWithProbability_seq_handler(wordWithProbability_seq: Seq[WordWithProbability]): Seq[Topic] = {
    val temp = topics.map(topic_handler(wordWithProbability_seq))
    temp.sortBy(_._2).foreach(println)
    temp.withFilter(_._2 > threshold).map(_._1)
  }

  val wordWithProbability_seq = Seq(
    ("医药", 0.34692),
    ("药店", 0.0579149),
    ("药品", 0.0359799),
    ("药房", 0.0339464),
    ("处方", 0.0258046),
    ("零售", 0.0198529),
    ("国药", 0.0195491),
    ("回扣", 0.0168899),
    ("健康", 0.0164062),
    ("连锁", 0.0152807)

  )

  println(wordWithProbability_seq_handler(wordWithProbability_seq))
}
