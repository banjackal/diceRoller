package com.example.diceroller.components

import kotlin.random.Random

fun rollDice(numberOfDice: Int, diceSides: Int): List<Int> {
    return List(numberOfDice) { Random.nextInt(1, diceSides + 1) }
}