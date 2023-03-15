package roboScript

import java.util.regex.Pattern

abstract class RSCompiler {

    protected val defaultSyntax = "F\\d+|F+\\d+|F+|R+\\d+|R\\d+|R+|L+\\d+|L\\d+|L+"

    protected fun defaultCompileCode(code: String): List<String> {
        val p = Pattern.compile(getRSSyntax())
        val m = p.matcher(code)
        val tokens: MutableList<String> = mutableListOf()
        while (m.find()) {
            tokens.add(m.group(0))
        }
        return tokens
    }

    protected fun createRoboCommands(command: String): RoboCommand = when {
        command.contains("F") || command.contains("f") -> Forward(command)
        command.contains("L") || command.contains("l") -> Left(command)
        command.contains("R") || command.contains("r") -> Right(command)
        else -> throw IllegalArgumentException(" $command not supported. ")
    }

    abstract fun canCompileCode(code: String): Boolean

    abstract fun compileCode(code: String): List<RoboCommand>

    protected abstract fun getRSSyntax(): String
}