package com.example.pokemonteams.clases

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon")
class Pokemon (
    val id: Int,
    val name: String,
    val order: Int,
    val sprite: String,
    val type1: String,
    val type2: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val weight: Float,
    val height: Float,
    val move1: String,
    val move2: String,
    val move3: String,
    val move4: String,
    val baseExperience: Int,
    val hability: String,
    val isHabilityHide: Boolean,
    val coordsX: String,
    val coordsY: String,
    @PrimaryKey(autoGenerate = true)
    var idPokemon:Int =0,): Serializable


