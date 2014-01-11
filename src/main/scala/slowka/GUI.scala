package slowka

import java.awt.FileDialog
import javax.swing.{JOptionPane, JFrame}
import java.io.FileNotFoundException


object GUI {
  def main(args: Array[String]) {

    val frame: JFrame = new JFrame("Program")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    val fd: FileDialog = new FileDialog(frame, "Wybierz plik z slówkami", FileDialog.LOAD)
    fd.setDirectory("C:\\")
    fd.setFile("*.txt")
    fd.setVisible(true)
    val filename = fd.getDirectory + fd.getFile

    if (filename != null) {
      try {
        Dictionary.parseFile(filename)
        var flag = true
        while(flag) {
          val (keyWord, valueWord, score) = Dictionary.getWord
          JOptionPane.showMessageDialog(null, keyWord, "Zapytanie", JOptionPane.INFORMATION_MESSAGE)
          val ans = JOptionPane.showConfirmDialog(null, keyWord + "\n" + valueWord, "Poprawnie?",  JOptionPane.YES_NO_OPTION)
          ans match {
            case JOptionPane.YES_OPTION => Dictionary.correct(keyWord, valueWord, score)
            case JOptionPane.NO_OPTION => Dictionary.incorrect(keyWord, valueWord, score)
            case _ =>
          }
          println(Dictionary.dictionary.size)
          if(Dictionary.dictionary.size < 20) Dictionary.readWordFromFile
          if(Dictionary.dictionary.isEmpty) flag = false
        }
      } catch {
        case t: FileNotFoundException =>
      }
    }

    frame.dispose()
  }
}
