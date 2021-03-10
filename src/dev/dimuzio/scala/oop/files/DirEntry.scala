package dev.dimuzio.scala.oop.files

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 05:36
 */
abstract class DirEntry(val parentPath: String, val name: String) {

  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory

  def getType: String

}
