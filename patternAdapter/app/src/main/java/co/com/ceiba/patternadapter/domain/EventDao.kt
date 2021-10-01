package co.com.ceiba.patternadapter.domain

import androidx.room.*
import co.com.ceiba.patternadapter.data.model.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity")
    suspend fun getEvents():List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventEntity: EventEntity)

    @Delete(entity = EventEntity::class)
    suspend fun deleteEvent(eventEntity: EventEntity)

    @Update(entity = EventEntity::class)
    suspend fun updateEvent(eventEntity: EventEntity)
}