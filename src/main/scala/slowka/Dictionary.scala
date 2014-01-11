package slowka

import java.io._
import scala.util.Random
import javax.swing.JOptionPane

object Dictionary {

  val random = new Random(System.nanoTime())

  var dictionary: Map[String, (String, Int)] = Map.empty

  var scanner: BufferedReader = _

  def readWordFromFile: Boolean = {
    if(scanner.ready()) {
      val line = scanner.readLine()
      line.split(':') match {
        case Array(valueWord, keyWord) => addWord(keyWord, valueWord)
        case Array("") => false
        case _ =>
          JOptionPane.showMessageDialog(null, "UWAGA, uszkodzona linia: " + line)
          false
      }
    } else false
  }

  def parseFile(filename: String) {
    scanner = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), "windows-1250"))
    var counter = 0
    while(counter < 20) if(readWordFromFile) counter += 1
  }

  def addWord(keyWord: String, valueWord: String): Boolean = {
    if(dictionary.contains(keyWord)) false
    else {
      dictionary = dictionary.updated(keyWord, (valueWord, 1000))
      dictionary.contains(keyWord)
    }
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