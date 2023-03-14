package roboScript

import roboScript.GridPathDirection.*

class Grid {
    private val grid = MutableList(1) { MutableList(1) { "*" } }
    private var gridPathPointer: GridPathPointer = GridPathPointer(RIGHT, 0, 0)

    fun printGrid() = grid.asReversed().joinToString("\r\n") { it.joinToString("") }

    fun movePointerForward() {
        when (gridPathPointer.gridPathDirection) {
            LEFT -> {
                if (gridPathPointer.y == 0) {
                    createNewColumn()
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                } else {
                    gridPathPointer.y -= 1
                    grid[gridPathPointer.x][gridPathPointer.y] = "*"
                }
            }

            RIGHT -> {
                if (gridPathPointer.y == grid[0].size - 1) {
                    createNewColumn()
                }
                gridPathPointer.y += 1
                grid[gridPathPointer.x][gridPathPointer.y] = "*"
            }

            UP -> {
                if (gridPathPointer.x == grid.size - 1) {
                    createNewRow()
                }
                gridPathPointer.x += 1
                grid[gridPathPointer.x][gridPathPointer.y] = "*"
            }

            DOWN -> {
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

    fun turnPointerLeft() {
        when (gridPathPointer.gridPathDirection) {
            LEFT -> gridPathPointer.gridPathDirection = DOWN
            RIGHT -> gridPathPointer.gridPathDirection = UP
            UP -> gridPathPointer.gridPathDirection = LEFT
            DOWN -> gridPathPointer.gridPathDirection = RIGHT
        }
    }

    fun turnPointerRight() {
        when (gridPathPointer.gridPathDirection) {
            LEFT -> gridPathPointer.gridPathDirection = UP
            RIGHT -> gridPathPointer.gridPathDirection = DOWN
            UP -> gridPathPointer.gridPathDirection = RIGHT
            DOWN -> gridPathPointer.gridPathDirection = LEFT
        }
    }

    private fun createNewRow() {
        val row = MutableList(grid[0].size) { " " }
        when (gridPathPointer.gridPathDirection) {
            UP -> grid.add(row)
            DOWN -> grid.addAll(0, MutableList(1) { row })
            LEFT, RIGHT -> throw IllegalArgumentException(
                "command not supported. "
            )
        }
    }

    private fun createNewColumn() {
        when (gridPathPointer.gridPathDirection) {
            LEFT -> grid.forEach { it.add(0, " ") }
            RIGHT -> grid.forEach { it.add(" ") }
            UP, DOWN -> throw IllegalArgumentException(
                "command not supported. "
            )
        }
    }
}