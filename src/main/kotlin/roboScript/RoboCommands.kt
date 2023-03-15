package roboScript

abstract class RoboCommand(private val commands: String) {
    fun drawPath(grid: Grid): Grid {
        when {
            commands.matches(Regex(".*\\d.*")) -> {
                val times = commands.filter { it.isDigit() }.toInt();
                if(times>0) repeat(times) { specialMove(grid) }
                commands.filter { !it.isDigit() }.dropLast(1).forEach { _ -> specialMove(grid) }
            }

            else -> commands.forEach { _ -> specialMove(grid) }
        }
        return grid
    }

    abstract fun specialMove(grid: Grid)
}

class Forward(commands: String) : RoboCommand(commands) {
    override fun specialMove(grid: Grid) {
        grid.movePointerForward()
    }
}

class Left(commands: String) : RoboCommand(commands) {
    override fun specialMove(grid: Grid) {
        grid.turnPointerLeft()
    }
}

class Right(commands: String) : RoboCommand(commands) {
    override fun specialMove(grid: Grid) {
        grid.turnPointerRight()
    }
}