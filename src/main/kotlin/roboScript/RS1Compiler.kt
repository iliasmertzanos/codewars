package roboScript

class RS1Compiler : RSCompiler() {

    override fun canCompileCode(code: String) = !code.contains(Regex("\\(([^()]*|\\(([^()]*|\\([^()]*\\))*\\))*\\)\\d+"))

    override fun compileCode(code: String) = defaultCompileCode(code).map { createRoboCommands(it) }

    override fun getRSSyntax() = defaultSyntax
}
