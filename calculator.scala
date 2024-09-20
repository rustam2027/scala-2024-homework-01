/** Software implementation of PROC (PROstoy Calculator) mk. 1 (or mk. 2).
  *
  * You should finish this procedure according to the reference described in
  * `README.md` to complete the assignment.
  */

import scala.util.boundary, boundary.break

class State {
  var acc: Int = 0
  var a: Int = 0
  var b: Int = 0
  var blink: Boolean = false
  var break: Boolean = false // ?
}

trait Command {
  def exec(state: State): Unit
}

object Plus extends Command {
  override def exec(state: State): Unit = {
    state.acc = state.a + state.b
    state.blink = false
  }
}

object Minus extends Command {
  override def exec(state: State): Unit = {
    state.acc = state.a - state.b
    state.blink = false
  }
}

object Multiply extends Command {
  override def exec(state: State): Unit = {
    state.acc = state.a * state.b
    state.blink = false
  }
}

object Divide extends Command {
  override def exec(state: State): Unit = {
    if (state.b == 0) {
      state.acc = 0
      state.a = 0
      state.b = 0
    } else {
      state.acc = state.a / state.b
    }
    state.blink = false
  }
}

object Swap extends Command {
  override def exec(state: State): Unit = {
    val temp = state.b
    state.b = state.a
    state.a = temp
  }
}

object Blink extends Command {
  override def exec(state: State): Unit = {
    state.blink = !state.blink
  }
}

object Acc extends Command {
  override def exec(state: State): Unit = {
    if (state.blink) {
      state.b = state.acc
    } else {
      state.a = state.acc
    }
    Blink.exec(state)
  }
}

class X(num: Int) extends Command {
  override def exec(state: State): Unit = {
    if (state.blink) {
      state.b = num
    } else {
      state.a = num
    }
    Blink.exec(state)
  }
}

object Break extends Command {
  override def exec(state: State): Unit = {
    state.break = true
  }

}

def parseInt(s: String): Int = s.toInt

def parseCommand(command: String) =
  command match {
    case "+"     => Plus
    case "-"     => Minus
    case "*"     => Multiply
    case "/"     => Divide
    case "swap"  => Swap
    case "blink" => Blink
    case "acc"   => Acc
    case "break" => Break
    case x       => X(parseInt(x))
  }

@main def calculator(commands: String*): Unit = {

  /** Converts given string `s` to integer.
    *
    * Throws [[NumberFormatException]] if `s` can't be converted to integer, but
    * you shouldn't worry about it at this moment.
    */
  val state = new State

  boundary {
    for (c <- commands) {
      parseCommand(c).exec(state)
      if (state.break) {
        break()
      }
    }
  }

  println(state.acc)

}
