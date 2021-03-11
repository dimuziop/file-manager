package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.{DirEntry, Directory}
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 13:37
 */
case class Mkdir(name: String) extends CreateEntry(name) {

  override def createSpecificEntry(state: State, entryName: String): DirEntry = Directory.empty(state.wd.path, name)

}
