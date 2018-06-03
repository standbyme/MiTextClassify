package StandbyMe.MiTextClassify

object main extends App {
  type Topic = String
  type Word = String
  type Score = Double
  val topics = Array("娱乐", "家居", "健康", "国际", "教育", "社会", "游戏", "旅行", "时政", "民生", "科学", "科技", "财经", "房产", "体育", "历史", "时尚", "军事", "美食", "汽车", "星座", "情感", "育儿", "文化", "天气", "宗教")
  val threshold = 0.2

  def topic_handler(words: Array[Word])(topic: Topic): (Topic, Score) = {
    println(topic)
    val scores = words.flatMap(wordSimEmbedding(_, topic))
    val average = scores.sum / scores.length
    println(scores.length)
    (topic, average)
  }

  def words_handler(words: Array[Word]): Option[Topic] = {
    val temp = topics.map(topic_handler(words)).sortBy(_._2)
    temp.foreach(println)
    val (topic, score) = temp.maxBy(_._2)
    println(s"topic $topic    score $score")
    if (score > threshold) Some(topic)
    else None
  }

  val words = Array("投资", "资产", "报告", "证券", "净值", "组合", "期末", "管理人", "债券", "持有")

  println(words_handler(words))
}
