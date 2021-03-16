package dev.dimuzio.scala.oop.filesystem

import dev.dimuzio.scala.oop.commands.Command
import dev.dimuzio.scala.oop.files.Directory

import java.util.Scanner

/**
 * User: patricio
 * Date: 5/3/21
 * Time: 19:55
 */
object Filesystem  extends App {

  val root = Directory.ROOT

  io.Source.stdin.getLines().foldLeft(State(root, root))((currentState: State, newLine: String) => {
    currentState.show()
    Command.from(newLine).apply(currentState)
  })

  /*var state = State(root, root)
  val scanner = new Scanner(System.in)

  while(true) {
    state.show()
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }*/

}
