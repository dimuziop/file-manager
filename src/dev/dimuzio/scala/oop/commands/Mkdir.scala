package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.{DirEntry, Directory}
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 13:37
 */
case class Mkdir(name: String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Entry " + name + " already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(name + " must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(name + " is an illegal entry name!")
    } else {
      doMkdir(state, name)
    }
  }

  def checkIllegal(name: String): Boolean = {
    name.contains(".")
  }


  def doMkdir(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head)
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
      }
    }

    val wd = state.wd

    //1. all directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath

    //2. create new directory in the wd
    val newDir = Directory.empty(wd.path, name)

    //3. update the whole directory structure starting from the root
    // (the directory structure is IMMUTABLE)
    val newRoot = updateStructure(state.root, allDirsInPath, newDir)

    //4 fins new working directory INSTANCE given re¡s full path , in the NEW directory structure

    val newWd = newRoot.findDescendant(allDirsInPath)

    State(newRoot, newWd)

  }
}
