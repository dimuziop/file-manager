package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.{DirEntry, File}
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 11/3/21
 * Time: 18:01
 */
class Touch(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State, entryName: String): DirEntry = File.empty(state.wd.path, name)

}
