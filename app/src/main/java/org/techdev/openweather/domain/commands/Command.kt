package org.techdev.openweather.domain.commands

/**
 * Execute an operation and return and object of the class specified in its generic type
 */
interface Command<out T> {
    fun execute(): T
}