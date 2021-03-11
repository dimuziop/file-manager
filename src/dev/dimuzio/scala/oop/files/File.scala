package dev.dimuzio.scala.oop.files

import dev.dimuzio.scala.oop.filesystem.FilesystemException


/**
 * User: patricio
 * Date: 10/3/21
 * Time: 01:39
 */
class File(override val parentPath: String, override val name: String, contents: String) extends DirEntry(parentPath, name) {

  override def asDirectory: Directory =
    throw new FilesystemException("A file cannot be converted to a directory")

  override def getType: String = "File"

  override def asFile: File = ???
}

object File {

  def empty(parentPath: String, name: String): File =
    new File(parentPath, name, "")

}