package org.techdev.openweather.domain

import java.time.DayOfWeek
import java.util.*

class Forecast(
    val date: Date,
    val temperature: Float,
    val details: String
)