package StandbyMe.MiTextClassify

object main extends App {
  val word1 = "note"
  val word2 = "三星"
  val score_opt = wordSimEmbedding(word1, word2)
  println(score_opt)
}
