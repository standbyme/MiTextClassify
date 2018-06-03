package StandbyMe.Script

import StandbyMe.MiTextClassify.{Topic, WordWithProbability, wordWithProbability_seq_handler}

import scala.io.Source
import java.io.PrintWriter
import java.io.File

object main extends App {
  type FirstBlock = Seq[String]
  type SecondBlock = Seq[WordWithProbability]

  val file = Source.fromFile("topic_words.lda.txt")
  val lines = file.getLines

  def line_to_wordWithProbability(line: String): WordWithProbability = {
    val Array(word, probability_string) = line.split("\t")
    (word, probability_string.toDouble)
  }

  def firstBlock_to_secondBlock(firstBlock: FirstBlock): SecondBlock = {
    firstBlock
      .drop(2)
      .map(line_to_wordWithProbability)
  }

  val firstBlock_array = lines.grouped(12)

  val writer = new PrintWriter(new File("output"))

  def handler(m: (Seq[Topic], Int)): Unit = {
    val (topics, index) = m
    println(s"Output $index")
    println(topics)
    writer.println(topics)
    writer.flush()
  }


  firstBlock_array
    .map(firstBlock_to_secondBlock)
    .map(wordWithProbability_seq_handler)
    .zipWithIndex
    .foreach(handler)

  writer.close()
}
