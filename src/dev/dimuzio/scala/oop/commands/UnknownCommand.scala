package dev.dimuzio.scala.oop.commands
import dev.dimuzio.scala.oop.filesystem.State

/**
 * User: patricio
 * Date: 8/3/21
 * Time: 11:16
 */
class UnknownCommand  extends Command {

  override def apply(state: State): State =
    state.setMessage("Command not found")


}
