fun createSeatingArrangement(rows: Int, cols: Int): MutableList<MutableList<String>> {
    /**
     * returns the seating arrangement based on the specified rows and columns
     */
    val seatingArrangement = mutableListOf<MutableList<String>>()
    // making rows with 1st numbers
    for (i in 0..rows) {
        seatingArrangement.add(mutableListOf<String>(i.toString()))
    }
    // filling 1st row with number of cols
    for (i in 1..cols) {
        seatingArrangement[0].add(i.toString())
    }
    // filling rows with "S"
    for (i in 1..rows) {
        repeat(cols) { seatingArrangement[i].add("S") }
    }
    // clearing [0][0]
    seatingArrangement[0][0] = " "

    return seatingArrangement
}

fun showTheSeats(seatingArrangement: MutableList<MutableList<String>>) {
    /**
     *   takes seating arrangement and prints it
     */
    println("Cinema:")
    for (i in seatingArrangement) {
        println(i.joinToString(" "))
    }
}

fun buyATicket(bigCinema: Boolean, highCostRows: Int, seatingArrangement: MutableList<MutableList<String>>): Int {
    /**
     * takes the coordinates of the seat,
     * shows the price for the seat and will replace the symbol "S" with "B" in the seating arrangement
     * returns price of a ticket
     */
    println("Enter a row number:")
    val seatsRow = readln().toInt()
    println("Enter a seat number in that row:")
    val seatsCol = readln().toInt()

    try {
        if (seatingArrangement[seatsRow][seatsCol] == "B") {
            println("That ticket has already been purchased!")
            return 0
        } else {
            seatingArrangement[seatsRow][seatsCol] = "B"
        }
    } catch (e: IndexOutOfBoundsException) {
        println("Wrong input!")
        return 0
    }

    val ticketPrice = if (!bigCinema) {
        10
    } else if (seatsRow > highCostRows) {
        8
    } else {
        10
    }
    return ticketPrice
}

fun isCinemaBig(rows: Int, cols: Int): Boolean {
    return rows * cols > 60
}

fun getHighCostRows(bigCinema: Boolean, rows: Int): Int {
    return if (bigCinema) {
        rows / 2
    } else {
        999
    }
}

fun showStatistics(
    numberOfSeats: Int,
    purchasedTickets: Int,
    currentIncome: Int,
    totalIncome: Int
) {
    val percentage = ((purchasedTickets.toDouble()) / numberOfSeats) * 100
    val formatPercentage = "%.2f".format(percentage)
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: \$$currentIncome")
    println("Total income: \$$totalIncome")
}

fun showMenu(): String {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readln()
}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val cols = readln().toInt()

    val seatingArrangement = createSeatingArrangement(rows, cols)
    val numberOfSeats = rows * cols
    val bigCinema = isCinemaBig(rows, cols)
    val highCostRows = getHighCostRows(bigCinema, rows)
    var currentIncome = 0
    var purchasedTickets = 0
    val totalIncome = if (bigCinema) {
        (highCostRows *  cols * 10) + ((rows - highCostRows) * cols * 8)
    } else {
          numberOfSeats * 10
    }

    do {
        when (showMenu()) {
            "1" -> {
                showTheSeats(seatingArrangement)
            }

            "2" -> {
                var ticketPrice: Int
                do {
                    ticketPrice = buyATicket(bigCinema, highCostRows, seatingArrangement)
                } while (ticketPrice == 0)
                currentIncome += ticketPrice
                purchasedTickets += 1
                println("Ticket price: \$$ticketPrice")
            }

            "3" -> {
                showStatistics(numberOfSeats, purchasedTickets, currentIncome, totalIncome)
            }
            "0" -> return
        }
    } while (true)
}
