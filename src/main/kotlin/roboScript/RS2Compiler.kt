package roboScript

class RS2Compiler : RSCompiler() {
    private val rs2Syntax = "\\(([^()]*|\\(([^()]*|\\([^()]*\\))*\\))*\\)\\d+"

    override fun canCompileCode(code: String) = code.contains(Regex(rs2Syntax))

    override fun compileCode(code: String): List<RoboCommand> {
        val codeToCompile = convertToRS1Syntax(code)
        return defaultCompileCode(codeToCompile).map { this.createRoboCommands(it) }
    }

    private fun convertToRS1Syntax(code: String): String {
        var codeToCompile = code
        while (canCompileCode(codeToCompile)) {
            val compiledCode = defaultCompileCode(codeToCompile)
            compiledCode.filter { it.contains(Regex(rs2Syntax)) }
                .forEach {
                    val repeatedCommand = it.substring(it.indexOf("(") + 1, it.lastIndexOf(")"))
                    val times = it.substring(it.lastIndexOf(")") + 1, it.length).toInt()
                    codeToCompile = if (times == 0) code.replace(it, "")
                    else codeToCompile.replace(it, repeatedCommand.repeat(times))
                }
        }
        return codeToCompile
    }

    override fun getRSSyntax() = "$rs2Syntax|$defaultSyntax"
}
