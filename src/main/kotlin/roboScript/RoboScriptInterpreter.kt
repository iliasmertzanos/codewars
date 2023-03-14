package roboScript

import java.util.*
import java.util.regex.Pattern

class RoboScriptInterpreter {
    private var grid = Grid()

    fun drawPath(code: String): String {
        val interpretedCode = interpretCode(code)
        interpretedCode.map { createRoboCommands(it) }.forEach { grid = it.drawPath(grid) }
        return grid.printGrid()
    }

    private fun interpretCode(code: String): MutableList<String> {
        val p = Pattern.compile("F\\d+|F+\\d+|F+|R+\\d+|R\\d+|R+|L+\\d+|L\\d+|L+|\\(|\\)+")
        val m = p.matcher(code)
        val tokens: MutableList<String> = LinkedList()
        while (m.find()) {
            tokens.add(m.group(0))
        }
        return tokens
    }

    private fun createRoboCommands(command: String): RoboCommand = when {
        command.contains("F") -> Forward(command)
        command.contains("L") -> Left(command)
        command.contains("R") -> Right(command)
        else -> throw IllegalArgumentException(" $command not supported. ")
    }
}