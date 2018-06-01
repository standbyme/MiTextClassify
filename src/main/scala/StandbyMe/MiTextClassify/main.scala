package StandbyMe.MiTextClassify

object main extends App {
  val word1 = "note"
  val word2 = "三星"
  val score = client.wordSimEmbedding(word1, word2, null).getDouble("score")
  println(score)
}
