package com.example.drawingapp

import kotlinx.coroutines.*

class Test {

}

fun main() = runBlocking {
    val deferreds: List<Deferred<Int>> = (1..3).map {
        async {
            delay(1000L * it)
            println("Loading $it")
            it
        }
    }
    val sum = deferreds.awaitAll().sum()
    println("$sum")
}

// this is your first suspending function
suspend fun doWorld() {
    coroutineScope {
        launch {
            delay(1000L)
            println(" World 1")

        }
        launch {
            delay(1000L)
            println(" World 2")

        }
        print("Hello")
    }
}