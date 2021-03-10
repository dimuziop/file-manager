package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 05:33
 */
trait Command {

  def apply(state: State): State

}

object Command {

  val MKDIR = "mkdir"
  val LS = "ls"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = state.setMessage(name + ": Incomplete command")
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else Mkdir(tokens(1))
    } else if (LS.equals(tokens(0))) {
      new Ls
    }
    else new UnknownCommand
  }
}
