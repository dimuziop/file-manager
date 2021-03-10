package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.DirEntry
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 9/3/21
 * Time: 14:48
 */
class Ls extends Command {

  def createImprovedOutput(contents: List[DirEntry]): String =
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + " -- " + entry.getType + "\n" + createImprovedOutput(contents.tail)
    }

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val improvedOutput = createImprovedOutput(contents)
    state.setMessage(improvedOutput)
  }
}
