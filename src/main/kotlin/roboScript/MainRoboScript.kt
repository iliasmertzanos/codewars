package roboScript

fun main(args: Array<String>) {
    var stringInput = ""
    val roboScriptInterpreter = RoboScriptInterpreter()

    while (stringInput != "q") {
        stringInput = readln()
        for (clear in 0..999) {
            println("\b")
        }
        println(roboScriptInterpreter.drawPath(stringInput))
    }
}