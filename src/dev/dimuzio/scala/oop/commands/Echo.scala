package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.files.{Directory, File}
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
    if (filename.contains(Directory.SEPARATOR))
      state.setMessage("Echo: filename must not contain separators")
    else {
      val newRoot: Directory = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)
      if (newRoot == state.root)
        state.setMessage(filename + ": not such file")
      else
        State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  def getRootAfterEcho(currentDir: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    if (path.isEmpty) currentDir
    else if (path.tail.isEmpty) {
      val dirEntry = currentDir.findEntry(path.head)
      if (dirEntry == null) currentDir.addEntry(new File(currentDir.path, path.head, contents))
      else if (dirEntry.isDirectory) currentDir
      else if (append) currentDir.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
      else
        currentDir.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
    }
    else {
      val nextDirectory = currentDir.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)

      if(newNextDirectory == nextDirectory) currentDir
      else currentDir.replaceEntry(path.head, newNextDirectory)

    }

  }

}
