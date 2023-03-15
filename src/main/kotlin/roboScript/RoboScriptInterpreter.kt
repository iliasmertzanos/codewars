package roboScript

class RoboScriptInterpreter {
    private var grid = Grid()
    private val rSCompilers = importCompilers();

    fun drawPath(code: String): String {
        val interpretedCode = rSCompilers.first { it.canCompileCode(code) }.compileCode(code);
        interpretedCode.forEach { grid = it.drawPath(grid) }
        return grid.printGrid()
    }
}