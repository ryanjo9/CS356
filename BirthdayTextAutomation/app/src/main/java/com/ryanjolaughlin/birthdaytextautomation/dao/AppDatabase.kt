package com.ryanjolaughlin.birthdaytextautomation.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryanjolaughlin.birthdaytextautomation.model.Enabled

@Database(entities = arrayOf(Enabled::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun enabledDao(): EnabledDao
}