package com.kotlinconf.workshop.househelper.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kotlinconf.workshop.househelper.database.entities.AreaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {
    @Query("SELECT * FROM areas")
    fun getAllAreas(): Flow<List<AreaEntity>>

    @Query("SELECT * FROM areas WHERE id = :areaId")
    fun getAreaById(areaId: String): Flow<AreaEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(area: AreaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAreas(areas: List<AreaEntity>)

    @Update
    suspend fun updateArea(area: AreaEntity)

    @Delete
    suspend fun deleteArea(area: AreaEntity)

    @Query("DELETE FROM areas")
    suspend fun deleteAllAreas()
}
