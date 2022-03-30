package com.example.pokemonteams.adapter

import com.example.pokemonteams.clases.Pokemon
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Float

class APIAdapterPokemonObject {

    fun getPokemon(p: JSONObject): Pokemon{
        val name = p.getString("name");
        val id = p.getInt("id");

        val isShiny = getIsShiny()

        var sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${id}.png";

        if(isShiny){
            sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/${id}.png"
        }

        val order = p.getInt("order");
        val _height = p.getDouble("height");
        val _weight = p.getDouble("weight");
        val baseExperience = p.getInt("base_experience")

        val types = p.getJSONArray("types")
        var type1 = ""
        var type2 = ""
        if(types.length() === 2){
            type1 = getType(types, 0);
            type2 = getType(types, 1);
        }else{
            type1 = getType(types, 0);
        }

        val stats = p.getJSONArray("stats")

        var hp = 0
        var attack = 0
        var defense = 0
        var specialAttack = 0
        var specialDefense = 0
        var speed = 0

        if(stats.length() === 6){
            hp = getStat(stats, 0)
            attack = getStat(stats, 1)
            defense = getStat(stats, 2)
            specialAttack = getStat(stats, 3)
            specialDefense = getStat(stats, 4)
            speed = getStat(stats, 5)
        }
        val moves = p.getJSONArray("moves")
        val mle = moves.length()

        val move1 = getMove(moves, (0..mle).random())
        val move2 = getMove(moves, (0..mle).random())
        val move3 = getMove(moves, (0..mle).random())
        val move4 = getMove(moves, (0..mle).random())

        val abilities = p.getJSONArray("abilities")

        val isHide = (0..1).random()
        var ability = ""

        if((isHide === 1) and (abilities.length() === 2)){
            ability = getAbility(abilities, 1)
        }else{
            ability = getAbility(abilities, 0)
        }
        val coordsX = getCoordsRandom()
        val coordsY = getCoordsRandom()


        return Pokemon(
            id,
            name,
            order,
            sprite,
            type1,
            type2,
            hp,
            attack,
            defense,
            specialAttack,
            specialDefense,
            speed,
            weight = Float.parseFloat(_weight.toString()),
            height = Float.parseFloat(_height.toString()),
            move1,
            move2,
            move3,
            move4,
            baseExperience,
            hability = ability,
            isHabilityHide = isHide === 1,
            coordsX,
            coordsY
        )

    }

    fun getType(obj: JSONArray, index: Int): String {
        return try {
            obj.getJSONObject(index).getJSONObject("type").getString("name")
        }catch (error: Error){
            ""
        }
    }

    fun getStat(obj: JSONArray, index: Int): Int {
        return try {
            obj.getJSONObject(index).getInt("base_stat")
        }catch (error: Error){
            0
        }
    }

    fun getMove(obj: JSONArray, index: Int): String {
        return try {
            obj.getJSONObject(index).getJSONObject("move").getString("name")
        }catch (error: Error){
            ""
        }
    }


    fun getAbility(obj: JSONArray, index: Int): String {
        return try {
            obj.getJSONObject(index).getJSONObject("ability").getString("name")
        }catch (error: Error){
            ""
        }
    }

    fun getCoordsRandom(): String {
        val signe = (0..1).random()
        var signoStr = ""
        if(signe === 1){
            signoStr = "-"
        }

        return "${signoStr}${(10..99).random()}.${(100000..999999).random()}"
    }

    fun getIsShiny(): Boolean {
        val n = (0..10).random()
        return n === 5
    }
}

