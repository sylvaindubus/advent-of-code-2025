package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines


typealias Lights = List<Int>

typealias Button = List<Int>

typealias Buttons = List<Button>

fun main() {
    val fileName = "day10.txt"

    val regex = Regex("""^\[([#.]+)\]\s(.*)\s\{(.*)\}$""")

    class Machine(val lights: Lights, val buttons: Buttons) {
        override fun toString(): String {
            val displayedLights = lights.map { light -> if (light == 1) '#' else '.' }.joinToString("")
            return "Lights: $displayedLights, Buttons: $buttons"
        }
    }

    val machines: List<Machine> = parseResourceLines(fileName, regex) { match ->
        val destructuredResult = match.destructured
        val lights = destructuredResult.component1().toCharArray().map { light ->
            if (light == '#') 1 else 0
        }
        val buttons = destructuredResult.component2().split(" ").map { button ->
            button.replace("[()]".toRegex(), "").split(",").map { it.toInt() }
        }
        Machine(lights, buttons)
    }

    fun getCombinations(n: Int, x: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = MutableList(x) { 0 };

        fun backtrack(pos: Int, min: Int) {
            if (pos == x) {
                result.add(current.toList())
                return
            }

            for (i in min..n - 1) {
                current[pos] = i
                backtrack(pos + 1, i + 1)
            }
        }

        backtrack(0, 0)

        return result.toList()
    }


    fun getButtonsCombinations(number: Int, allButtons: Buttons): List<Buttons> {
        val combinations = getCombinations(allButtons.size, number)
        return combinations.map { combination ->
            combination.map { index -> allButtons[index] }
        }
    }

    fun pressButtons(machine: Machine, presses: Int): Boolean {
        val combinations = getButtonsCombinations(presses, machine.buttons)

        for (buttons in combinations) {
            val lights = MutableList(machine.lights.size) { 0 }
            for (button in buttons) {
                for (toggledLight in button) {
                    lights[toggledLight] = (lights[toggledLight] + 1) % 2
                }
            }

            if (lights == machine.lights) {
                return true
            }
        }

        return false
    }

    var result = 0

    for (machine in machines) {
        var found = false
        var presses = 1

        while (!found && presses <= machine.buttons.size) {
            val success = pressButtons(machine, presses)

            if (success) {
                found = true
                result += presses
            } else {
                presses += 1
            }
        }

    }

    println(result)
}


