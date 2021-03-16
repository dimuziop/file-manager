package dev.dimuzio.scala.oop.commands
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 16/3/21
 * Time: 18:29
 */
class Cat(filename: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd

    val dirEntry = wd.findEntry(filename)

    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(filename + ": No such file")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
