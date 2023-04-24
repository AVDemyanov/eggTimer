package com.example.eggtimer.main

sealed interface MainViewState

data class Initialization(val time: Long): MainViewState
data class Running(val time: Long): MainViewState
object Done: MainViewState
enum class CookDegree(val time: Long){
    SOFT(10_000),
    MEDIUM(5 * 60 * 1_000L),
    HARD(9 * 60 * 1_000L)
}