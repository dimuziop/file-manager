package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.filesystem.State

import scala.annotation.tailrec

/**
 * User: patricio
 * Date: 16/3/21
 * Time: 14:27
 */
case class Echo(args: List[String]) extends Command {

  override def apply(state: State): State = {
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args.head)
    else {
      val operator = args(args.length - 2)
      val filename = args.last
      val contents = createContent(args, args.length - 2)
      if (">>".equals(operator))
        doEcho(state, contents, filename, append = true)
      else if (">".equals(operator))
        doEcho(state, contents, filename, append = false)
      else state.setMessage(createContent(args, args.length))
    }
  }

  def createContent(args: List[String], topIndex: Int): String = {
    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      if (currentIndex >= topIndex) accumulator
      else createContentHelper(currentIndex + 1, accumulator + " " + args(currentIndex))
    }
    createContentHelper(0, "")
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    ???
  }

}
