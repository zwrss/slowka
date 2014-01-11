package slowka

import java.io._
import scala.util.Random

object Dictionary {

  val random = new Random(System.nanoTime())

  var dictionary: Map[String, (String, Int)] = Map.empty

  def parseFile(filename: String) {
    val scanner: BufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), "windows-1250"))
    while(scanner.ready()) {
      val line = scanner.readLine()
      line.split(':') match {
        case Array(valueWord, keyWord) => addWord(keyWord, valueWord)
        case _ =>
      }
    }
  }

  def addWord(keyWord: String, valueWord: String) {
    dictionary = dictionary.updated(keyWord, (valueWord, 1000))
  }

  def getWord: (String, String, Int) = {
    def choose(max: Int, min: Int): (String, String, Int) = {
      val keysArray = dictionary.keys.toArray
      val key = keysArray(random.nextInt(dictionary.size))
      val (value, score) = dictionary.get(key).get
      val r = random.nextInt(max - min + 1) + min
      if(score <= r) (key, value, score)
      else choose(max, min)
    }

    val (max, min) = {
      val scores = dictionary.values.map(_._2)
      (scores.max, scores.min)
    }

    choose(max, min)
  }

  def correct(keyWord: String, valueWord: String, score: Int) {
    if(score < 1003) dictionary = dictionary.updated(keyWord, (valueWord, score + 1))
    else dictionary = dictionary.filterKeys(_ != keyWord)
  }

  def incorrect(keyWord: String, valueWord: String, score: Int) {
    dictionary = dictionary.updated(keyWord, (valueWord, score - 1))
  }

}