package slowka

import java.io._

class Dictionary {

  type Key = String
  type ValueWithScore = (String, Int)
  type KeyValue = (String, String)

  var dictionary: Map[Key, ValueWithScore] = Map.empty[Key, ValueWithScore]

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

  def addWord(keyWord: Key, valueWord: String) {
    dictionary = dictionary.updated(keyWord, (valueWord, 0))
  }

  def getWord: KeyValue = {

  }

}

object Dictionary {
  def apply(filename: String): Dictionary = {
    val d = new Dictionary
    d.parseFile(filename)
    d
  }
}
