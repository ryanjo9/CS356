package com.ryanjolaughlin.birthdaytextautomation.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Enabled(
  @PrimaryKey val id: String
)