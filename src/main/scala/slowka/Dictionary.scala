package slowka

import java.io._
import scala.util.Random

object Dictionary {

  val random = new Random(System.nanoTime())

  var dictionary: Map[Int, Map[String, String]] = Map.empty[Int, Map[String, String]]
  var lastWord: (String, String, Int) = _

  def parseFile(filename: String) {
    val scanner: BufferedReader = new BufferedReader(new FileReader(new File(filename)))
    while(scanner.ready()) {
      val line = scanner.readLine()
      line.split(':') match {
        case Array(valueWord, keyWord) => addWord(keyWord, valueWord)
        case _ =>
      }
    }
  }

  def addWord(keyWord: String, valueWord: String) {
    val m = dictionary.get(1000).getOrElse(Map.empty).updated(keyWord, valueWord)
    dictionary = dictionary.updated(1000, m)
  }

  def getWord: (String, String) = {
    println(dictionary.values.flatMap(_.values).size)
    val scores = random.shuffle(dictionary.keys.toList)
    println(dictionary.keys.toList)
    println(scores)
    val rand = random.nextInt(scores.max - scores.min + 1) + scores.min + 1
    val score = scores.find(_ < rand).get
    val map = dictionary.get(score).get
    val keyArray = map.keys.toArray
    val keyWord = keyArray(random.nextInt(keyArray.size)) //random.shuffle(map.keys).head
    lastWord = (keyWord, map.get(keyWord).get, score)
    (lastWord._1, lastWord._2)
  }

  def correct() {
    val keyWord = lastWord._1
    val valueWord = lastWord._2
    val oldScore = lastWord._3
    val m = dictionary.get(oldScore).get.filterKeys(_ != keyWord)
    val m2 = dictionary.get(oldScore + 1).getOrElse(Map.empty).updated(keyWord, valueWord)
    if(m.isEmpty) dictionary = dictionary.filterKeys(_ != oldScore)
    else dictionary = dictionary.updated(oldScore, m)
    dictionary = dictionary.updated(oldScore + 1, m2)
  }

  def incorrect() {
    val keyWord = lastWord._1
    val valueWord = lastWord._2
    val oldScore = lastWord._3
    if(oldScore > 0) {
      val m = dictionary.get(oldScore).get.filterKeys(_ != keyWord)
      val m2 = dictionary.get(oldScore - 1).getOrElse(Map.empty).updated(keyWord, valueWord)
      if(m.isEmpty) dictionary = dictionary.filterKeys(_ != oldScore)
      else dictionary = dictionary.updated(oldScore, m)
      dictionary = dictionary.updated(oldScore - 1, m2)
    }
  }

}