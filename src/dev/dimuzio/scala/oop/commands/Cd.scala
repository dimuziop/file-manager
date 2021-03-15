package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.{DirEntry, Directory}
import dev.dimuzio.scala.oop.filesystem.State

import scala.annotation.tailrec

/**
 * User: patricio
 * Date: 11/3/21
 * Time: 18:17
 */
class Cd(dir: String) extends Command {
  override def apply(state: State): State = {
    /*
    cd /a/b/v -> absolute path
    cd a/b/v -> relative path
     */

    //1. find root
    val root = state.root
    val wd = state.wd

    //2. find the absolute path of the directory I want to CD to
    val absolutePath =
      if (dir.startsWith(Directory.SEPARATOR)) dir
      else if (wd.isRoot) wd.path + dir
      else wd.path + Directory.SEPARATOR + dir

    //3. find the directory to cd tom given the path
    val destinationDirectory: DirEntry = doFindEntry(root, absolutePath)

    //4. change the state given directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + ": no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }


  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    // 1. tokens
    val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

    //2. navigate to the correct entry
    findEntryHelper(root, tokens)
  }
}
