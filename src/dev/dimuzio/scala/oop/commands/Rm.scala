package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.Directory
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 16/3/21
 * Time: 11:56
 */
class Rm(name: String) extends Command {

  override def apply(state: State): State = {
    // 1. get working dir
    val wd = state.wd

    // 2. get absolute path
    val absolutePath = {
      if (name.startsWith(Directory.SEPARATOR)) name
      else if (wd.isRoot) wd.path + name
      else wd.path + Directory.SEPARATOR + name
    }

    // 3. do some checks
    if (Directory.ROOT_PATH.equals(absolutePath))
      state.setMessage("Nuclear total war is not allowed yet")
    else
      doRm(state, absolutePath)

  }

  def doRm(state: State, absolutePath: String): State = {

    def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
      if (path.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
      else {
        val nextDirectory = currentDirectory.findEntry(path.head)
        if (!nextDirectory.isDirectory) currentDirectory
        else {
          val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
          if (newNextDirectory == newNextDirectory) currentDirectory
          else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
      }
    }
    // 4. get or find the entry to remove
    // 5. update structure like we do for mkdir

    val tokens: List[String] = absolutePath.substring(1).split(Directory.SEPARATOR).toList
    val newRoot: Directory = rmHelper(state.root, tokens)

    if (newRoot == state.root)
      state.setMessage(absolutePath + ": no such a file or directory")
    else
      State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))

  }
}
