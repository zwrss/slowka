package slowka

import java.io._
import scala.util.Random

object Dictionary {

  val random = new Random(System.nanoTime())

  var dictionary: Map[String, (String, Int)] = Map.empty
  var lastWord: (String, String, Int) = _

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

  def getWord: (String, String) = {

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

    lastWord = choose(max, min)
    (lastWord._1, lastWord._2)
  }

  def correct() {
    dictionary = dictionary.updated(lastWord._1, (lastWord._2, lastWord._3 + 1))
  }

  def incorrect() {
    dictionary = dictionary.updated(lastWord._1, (lastWord._2, lastWord._3 - 1))
  }

}