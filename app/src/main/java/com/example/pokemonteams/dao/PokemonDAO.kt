package com.example.pokemonteams.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokemonteams.clases.Pokemon

@Dao
interface PokemonDAO {

    @Query(value = "Select * from pokemon")
    fun getAll(): LiveData<List<Pokemon>>

    @Query(value = "Select * from pokemon where idPokemon=:id")
    fun get(id:Int):LiveData<Pokemon>

    @Insert
    fun insertAll(vararg pokemon:Pokemon)

    @Update
    fun update(pokemon: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

}