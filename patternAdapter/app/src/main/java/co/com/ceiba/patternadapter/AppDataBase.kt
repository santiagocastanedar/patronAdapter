package co.com.ceiba.patternadapter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.ceiba.patternadapter.data.model.EventEntity
import co.com.ceiba.patternadapter.domain.EventDao

@Database(entities = arrayOf(EventEntity::class),version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object{
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context):AppDataBase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,AppDataBase::class.java,"events_table").build()
            return INSTANCE!!
        }
    }
}