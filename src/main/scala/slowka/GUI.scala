package slowka

import java.awt.FileDialog
import javax.swing.JFrame


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
      Dictionary(filename)
    }

    frame.dispose()
  }
}
