package roboScript

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.regex.Pattern


fun execute(code: String): String {
    return RoboScriptInterpreter().drawPath(code)
}

class RoboScriptInterpreter {

    fun drawPath(code: String): String {
        var grid = Grid()
        val interpretedCode = interpretCode(code, "F\\d+|F+|R\\d+|R+|L+|L\\d+|\\(|\\)+")
        interpretedCode.map { RoboCommandFactory().createRoboCommand(it) }.forEach {
            grid = it.drawPath(grid)
            println("Command $it  ===================")
            println(grid.printGrid())
        }
        return grid.printGrid()
    }

    private fun interpretCode(code: String, regex: String): MutableList<String> {
        val p = Pattern.compile(regex)
        val m = p.matcher(code)
        val tokens: MutableList<String> = LinkedList()
        while (m.find()) {
            tokens.add(m.group(0))
        }
        return tokens
    }
}

class Grid {
    private val grid = MutableList(1) { MutableList(1) { "*" } }
    private var gridPathPointer: GridPathPointer = GridPathPointer(GridPathPointer.GridPathDirection.RIGHT, 0, 0)
    fun printGrid() = grid.asReversed().joinToString("\r\n") { it.joinToString("") }

    fun moveForward() {
        when (gridPathPointer.gridPathDirection) {
            GridPathPointer.GridPathDirection.LEFT -> {
                if (gridPathPointer.y == 0) {
                    createNewColumn()
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                } else {
                    gridPathPointer.y -= 1
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                }
            }

            GridPathPointer.GridPathDirection.RIGHT -> {
                if (gridPathPointer.y == grid[0].size - 1) {
                    createNewColumn()
                }
                gridPathPointer.y += 1
                grid[gridPathPointer.x][gridPathPointer.y] = "*"
            }

            GridPathPointer.GridPathDirection.UP -> {
                if (gridPathPointer.x == grid.size - 1) {
                    createNewRow()
                }
                gridPathPointer.x += 1
                grid[gridPathPointer.x][gridPathPointer.y] = "*"
            }

            GridPathPointer.GridPathDirection.DOWN -> {
                if (gridPathPointer.x == 0) {
                    createNewRow()
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                } else {
                    gridPathPointer.x -= 1
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                }
            }
        }
    }

    fun turnLeft() {
        when (gridPathPointer.gridPathDirection) {
            GridPathPointer.GridPathDirection.LEFT -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.DOWN
            GridPathPointer.GridPathDirection.RIGHT -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.UP
            GridPathPointer.GridPathDirection.UP -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.LEFT
            GridPathPointer.GridPathDirection.DOWN -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.LEFT
        }
    }

    fun turnRight() {
        when (gridPathPointer.gridPathDirection) {
            GridPathPointer.GridPathDirection.LEFT -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.UP
            GridPathPointer.GridPathDirection.RIGHT -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.DOWN
            GridPathPointer.GridPathDirection.UP -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.RIGHT
            GridPathPointer.GridPathDirection.DOWN -> gridPathPointer.gridPathDirection = GridPathPointer.GridPathDirection.LEFT
        }
    }

    private fun createNewRow() {
        val row = MutableList(grid[0].size) { " " }
        when (gridPathPointer.gridPathDirection) {
            GridPathPointer.GridPathDirection.LEFT, GridPathPointer.GridPathDirection.RIGHT -> throw IllegalArgumentException("command not supported. ")
            GridPathPointer.GridPathDirection.UP -> grid.add(row)
            GridPathPointer.GridPathDirection.DOWN -> grid.addAll(0, MutableList(1) { row })
        }
    }

    private fun createNewColumn() {
        when (gridPathPointer.gridPathDirection) {
            GridPathPointer.GridPathDirection.LEFT -> grid.forEach { it.add(0, " ") }
            GridPathPointer.GridPathDirection.RIGHT -> grid.forEach { it.add(" ") }
            GridPathPointer.GridPathDirection.UP, GridPathPointer.GridPathDirection.DOWN -> throw IllegalArgumentException("command not supported. ")
        }
    }
}

abstract class RoboCommand {
    abstract fun drawPath(grid: Grid): Grid
}

class Forward(private var commands: String) : RoboCommand() {
    override fun drawPath(grid: Grid): Grid {
        when {
            commands.matches(Regex(".*\\d.*")) -> {
                val times = commands.filter { it.isDigit() }.toInt();
                repeat(times) { grid.moveForward() }
            }

            else -> commands.forEach { _ -> grid.moveForward() }
        }
        return grid
    }
}

class Left(private var commands: String) : RoboCommand() {
    override fun drawPath(grid: Grid): Grid {
        when {
            commands.matches(Regex(".*\\d.*")) -> {
                val times = commands.filter { it.isDigit() }.toInt();
                repeat(times) { grid.turnLeft() }
            }

            else -> commands.forEach { _ -> grid.turnLeft() }
        }
        return grid
    }
}

class Right(private var commands: String) : RoboCommand() {
    override fun drawPath(grid: Grid): Grid {
        when {
            commands.matches(Regex(".*\\d.*")) -> {
                val times = commands.filter { it.isDigit() }.toInt();
                repeat(times) { grid.turnRight() }
            }

            else -> commands.forEach { _ -> grid.turnRight() }
        }
        return grid
    }
}


data class GridPathPointer(
    var gridPathDirection: GridPathDirection, var x: Int, var y: Int
) {
    enum class GridPathDirection {
        LEFT, RIGHT, UP, DOWN
    }
}

class RoboCommandFactory {
    fun createRoboCommand(command: String): RoboCommand = when {
        command.contains("F") -> Forward(command)
        command.contains("L") -> Left(command)
        command.contains("R") -> Right(command)
        else -> throw IllegalArgumentException(" $command not supported. ")
    }
}


class RoboScriptTest {

    @Test
    fun sampleTests() {
//        assertPathEquals("", "*")
//        assertPathEquals("FFFFF", "******")
//        assertPathEquals("FFFFFLFFFFFLFFFFFLFFFFFL", "******\r\n*    *\r\n*    *\r\n*    *\r\n*    *\r\n******")
//        assertPathEquals("LFFFFFRFFFRFFFRFFFFFFF", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ")
//        assertPathEquals("LF5RF3RF3RF7", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ")
        assertPathEquals("FFFLLFFFFFFRRFFFLFFFRRFFFFFFFF", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ")
    }

    private fun assertPathEquals(code: String, expected: String) {
        val actual = execute(code)
        assertEquals(expected, actual, "--------------\nCode: $code\nYou returned:\n$actual\nExpected path of robot:\n$expected\n--------------\n")
    }
}