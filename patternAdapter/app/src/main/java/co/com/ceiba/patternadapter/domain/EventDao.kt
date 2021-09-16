package co.com.ceiba.patternadapter.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.ceiba.patternadapter.data.model.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity")
    suspend fun getEvents():List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)
}