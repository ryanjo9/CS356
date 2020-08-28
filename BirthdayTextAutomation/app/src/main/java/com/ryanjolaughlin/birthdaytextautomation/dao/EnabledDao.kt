package com.ryanjolaughlin.birthdaytextautomation.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ryanjolaughlin.birthdaytextautomation.model.Enabled

@Dao
interface EnabledDao {
  @Query("SELECT * FROM enabled WHERE id = :id")
  fun findById(id: String): Enabled

  @Query("SELECT * FROM enabled")
  fun loadAll(): List<Enabled>

  @Insert
  fun insert(vararg enabled: Enabled)

  @Query("DELETE FROM enabled WHERE id = :id")
  fun delete(id: String)

  @Query("SELECT * FROM enabled WHERE id IN (:ids)")
  fun findByIds(ids: List<String>): List<Enabled>
}