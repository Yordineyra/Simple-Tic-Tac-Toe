package tictactoe

const val XXX = "XXX"
const val OOO = "OOO"
const val X = "X"
const val O = "O"
const val EMPTY = " "
const val XW = "X wins"
const val OW = "O wins"

private var _map = listOf(
    mutableListOf("---------"),
    mutableListOf("|", EMPTY, EMPTY, EMPTY, "|"),
    mutableListOf("|", EMPTY, EMPTY, EMPTY, "|"),
    mutableListOf("|", EMPTY, EMPTY, EMPTY, "|"),
    mutableListOf("---------")
)

val map: List<List<String>> = _map

fun main() {
    // print game
    printGame()

    var isX = 1
    val isPar = { isX % 2 == 0}

    while (true) {
        println("Enter the coordinates:")
        val coordinates = readln()
        val n1 = coordinates.first()
        val n2 = coordinates.last()
        var num1 = 0
        var num2 = 0
        try {
            num1 = n1.toString().toInt()
            num2 = n2.toString().toInt()
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
            continue
        }

        val range = 1..3
        val user = if (!isPar()) X else O

        if (num1 in range && num2 in range) {
            if (map[num1][num2] == EMPTY) {
                _map[num1][num2] = user
                printGame()
                isX++
                // value game
                if (valueGame()) {
                    break
                } else continue
            } else {
                println("This cell is occupied! Choose another one!")
            }
        } else println("Coordinates should be from 1 to 3!")

    }
}


private fun valueGame(): Boolean {

    var isX = false
    var isO = false

    val (x, o, empty) = count()

    // Horizontal Check
    if (horizontalCheck(XXX)) isX = true
    if (horizontalCheck(OOO)) isO = true

    // Vertical check
    if (verticalCheck(XXX)) isX = true
    if (verticalCheck(OOO)) isO = true

    // Diagonal check
    if (diagonalCheck(XXX)) {
        isX = true
    } else if (diagonalCheck(OOO)) {
        isO = true
    }

    // print if there is only one winner
     if (isX && !isO) {
        println(XW)
        return true
    } else if (!isX && isO) {
        println(OW)
        return true
    }

    // print if is empate
    if (!isX && !isO && empty == 0) {
        println("Draw")
        return true
    }
    return false
}

private fun printGame() {
    println(map[0].joinToString(""))
    println(map[1].joinToString(" "))
    println(map[2].joinToString(" "))
    println(map[3].joinToString(" "))
    println(map[4].joinToString(""))
}

private fun horizontalCheck(xOrO: String): Boolean {
    var temp1 = ""
    var temp2 = ""
    var temp3 = ""

    for (i in 1..3) {
        temp1 += map[1][i]
        temp2 += map[2][i]
        temp3 += map[3][i]
    }

    return (temp1 == xOrO || temp2 == xOrO || temp3 == xOrO)
}

private fun verticalCheck(xOrO: String): Boolean {
    val temp1 = map[1][1] + map[2][1].toString() + map[3][1]
    val temp2 = map[1][2] + map[2][2].toString() + map[3][2]
    val temp3 = map[1][3] + map[2][3].toString() + map[3][3]

    return (temp1 == xOrO || temp2 == xOrO || temp3 == xOrO)
}

private fun diagonalCheck(xOrO: String): Boolean {
    val temp1 = map[1][1] + map[2][2].toString() + map[3][3]
    val temp2 = map[1][3] + map[2][2].toString() + map[3][1]

    return (temp1 == xOrO || temp2 == xOrO)
}

private fun count(): List<Int> {
    var x = 0
    var o = 0
    var empty = 0

    for (i in 1..3) {
        val list = map[i]
        for (e in 1..3) {
            val char = list[e]
            when (char) {
                X -> x++
                O -> o++
                EMPTY -> empty++
            }
        }
    }
    return listOf(x, o, empty)
}