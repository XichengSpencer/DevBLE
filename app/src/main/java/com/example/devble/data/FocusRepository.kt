package com.example.devble.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class FocusRepository {

    // Emits a new random focus score every 5 seconds
    fun getFocusScores(): Flow<Int> = flow {
        while (true) {
            emit(Random.nextInt(0, 101))
            delay(5000)
        }
    }
}