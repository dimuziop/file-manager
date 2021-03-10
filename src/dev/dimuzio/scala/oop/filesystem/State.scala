package dev.dimuzio.scala.oop.filesystem

import dev.dimuzio.scala.oop.files.Directory

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 05:34
 */
class State(val root: Directory, val wd: Directory, val output: String) {

  def show(): Unit = {
    println(output)
    print(State.SHELL_TOKEN)
  }

  def setMessage(message: String): State =
    State(root, wd, message)
}

object State {
  val SHELL_TOKEN = "$ "

  def apply(root: Directory, wd: Directory, output: String = ""): State =
    new State(root, wd, output)
}
