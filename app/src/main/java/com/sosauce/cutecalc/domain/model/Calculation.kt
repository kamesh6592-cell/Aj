package com.ajstudioz.ajcalc.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calculation(
    val operation: String,
    val result: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
