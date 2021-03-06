package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 05:33
 */
trait Command extends (State => State){

  //def apply(state: State): State

}

object Command {

  val MKDIR = "mkdir"
  val LS = "ls"
  val PWD = "pwd"
  val TOUCH = "touch"
  val CD = "cd"
  val RM = "rm"
  val ECHO = "echo"
  val CAT = "cat"

  def emptyCommand: Command = new Command {
    override def apply(state: State): State = state
  }

  def incompleteCommand(name: String): Command = new Command {
    override def apply(state: State): State = state.setMessage(name + ": Incomplete command")
  }

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    /*else tokens(0) match {
      case MKDIR => if (tokens.length < 2) incompleteCommand(MKDIR)
      else Mkdir(tokens(1))
    }*/
    else if (MKDIR.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else Mkdir(tokens(1))
    } else if (LS.equals(tokens(0))) {
      new Ls
    } else if (PWD.equals(tokens(0))) {
      new Pwd
    } else if (TOUCH.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else new Touch(tokens(1))
    } else if (CD.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1))
    } else if (RM.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(RM)
      else new Rm(tokens(1))
    } else if (ECHO.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(ECHO)
      else new Echo(tokens.tail.toList)
    } else if (CAT.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CAT)
      else new Cat(tokens(1))
    }
    else new UnknownCommand
  }
}
