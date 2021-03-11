package dev.dimuzio.scala.oop.commands

import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 10/3/21
 * Time: 01:36
 */
class Pwd extends Command {
  override def apply(state: State): State =
    state.setMessage(state.wd.path)
}
