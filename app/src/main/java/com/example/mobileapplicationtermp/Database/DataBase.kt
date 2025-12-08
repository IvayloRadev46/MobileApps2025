package com.example.mobileapplicationtermp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BmiRecord::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bmiDao(): BmiDao

    companion object {

        @Volatile
        private var sharedInstance: AppDatabase? = null

        fun getInstance(appContext: Context): AppDatabase {
            // локален кеш за бърз достъп без синхронизация
            val cached = sharedInstance
            if (cached != null) {
                return cached
            }

            return synchronized(this) {
                val secondCheck = sharedInstance
                if (secondCheck != null) {
                    secondCheck
                } else {
                    val created = Room.databaseBuilder(
                        appContext.applicationContext,
                        AppDatabase::class.java,
                        "bmi_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    sharedInstance = created
                    created
                }
            }
        }
    }
}
