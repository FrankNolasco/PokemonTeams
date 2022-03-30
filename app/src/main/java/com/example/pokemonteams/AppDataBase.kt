package com.example.pokemonteams

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemonteams.clases.Pokemon
import com.example.pokemonteams.dao.PokemonDAO

@Database(entities = [Pokemon::class], version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun pokemon():PokemonDAO
    companion object{
        @Volatile
        private var INSTANCE: AppDataBase?=null

        fun getDatabase(context: Context):AppDataBase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(lock =this ){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "pokemon_teams"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}