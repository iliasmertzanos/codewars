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
        val p = Pattern.compile("f\\d+|f+\\d+|f+|r+\\d+|r\\d+|r+|l+\\d+|l\\d+|l+|\\(|\\)+")
        val m = p.matcher(code)
        val tokens: MutableList<String> = LinkedList()
        while (m.find()) {
            tokens.add(m.group(0))
        }
        return tokens
    }

    private fun createRoboCommands(command: String): RoboCommand = when {
        command.contains("F") || command.contains("f") -> Forward(command)
        command.contains("L") || command.contains("l") -> Left(command)
        command.contains("R") || command.contains("r") -> Right(command)
        else -> throw IllegalArgumentException(" $command not supported. ")
    }
}