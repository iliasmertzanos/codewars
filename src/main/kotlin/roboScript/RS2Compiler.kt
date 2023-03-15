package roboScript

class RS2Compiler : RSCompiler() {
    private val rs2Syntax = "\\([^()]*\\)\\d+|F\\d+|F+\\d+|F+|R+\\d+|R\\d+|R+|L+\\d+|L\\d+|L+"

    override fun canCompileCode(code: String) = code.contains(Regex("\\([^()]*\\)\\d+"))

    override fun compileCode(code: String): List<RoboCommand> {
        val codeToCompile = convertToRS1Syntax(code)
        return defaultCompileCode(codeToCompile).map { this.createRoboCommands(it) }
    }

    private fun convertToRS1Syntax(code: String): String {
        var codeToCompile = code
        while (canCompileCode(codeToCompile)) {
            val compiledCode = defaultCompileCode(codeToCompile)
            compiledCode.filter { it.contains(Regex("\\([^()]*\\)\\d+")) }
                .forEach {
                    val repeatedCommand = it.substring(it.indexOf("(") + 1, it.lastIndexOf(")"))
                    val times = it.substring(it.lastIndexOf(")") + 1, it.length).toInt()
                    codeToCompile = if (times == 0) code.replace(it, "")
                    else codeToCompile.replace(it, repeatedCommand.repeat(times))
                }
        }
        return codeToCompile
    }

    override fun getRSSyntax() = rs2Syntax
}
