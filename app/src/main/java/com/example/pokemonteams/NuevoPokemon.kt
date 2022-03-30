package com.example.pokemonteams

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.pokemonteams.adapter.APIAdapterPokemonObject
import com.example.pokemonteams.clases.Pokemon
import com.example.pokemonteams.dao.PokemonDAO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_nuevo_pokemon.*
import kotlinx.android.synthetic.main.fragment_pokemon_card.view.*
import org.json.JSONObject

class NuevoPokemon : AppCompatActivity() {

    private lateinit var PokemonAPI : Pokemon
    private var idPokemon: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_pokemon)
        val ctx = this

        if (intent.hasExtra("Pokemon")){
            val editPokemon = intent.extras?.getSerializable("Pokemon") as Pokemon
            idPokemon = editPokemon.idPokemon
            PokemonAPI = editPokemon
            pokemonPreview(PokemonAPI)
        }

        btnSearch.setOnClickListener {
            searchPokemon(ctx)
        }
        buttonAdd.setOnClickListener {
            addPokemonToTeam(ctx)
        }
    }

    fun addPokemonToTeam(ctx: Context){
        CoroutineScope(Dispatchers.IO).launch {
            val dataBase = AppDataBase.getDatabase(ctx)
            if(idPokemon !== 0){
                PokemonAPI.idPokemon = idPokemon
                dataBase.pokemon().update(PokemonAPI)
            }else{
                dataBase.pokemon().insertAll(PokemonAPI)
            }
            this@NuevoPokemon.finish()
        }
    }

    fun searchPokemon(context: Context) {
        val pkmName = FindPokemonName.text
        if(!pkmName.equals("")){
            getPokemonAPI(pkmName.toString().toLowerCase(),context);
        }else{
            FindPokemonName.hint="Ejemplo: Charizard"
        }
    }

    @SuppressLint("SetTextI18n")
    fun getPokemonAPI(PokemonName: String, context: Context) {
        val queue = newRequestQueue(context)
        val url = "https://pokeapi.co/api/v2/pokemon/${PokemonName}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val pkm = APIAdapterPokemonObject().getPokemon(JSONObject(response));
                PokemonAPI = pkm
                pokemonPreview(pkm)
            },
            { textView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
    @SuppressLint("SetTextI18n")
    fun pokemonPreview(pkm: Pokemon) {
        Picasso.get().load(pkm.sprite).into(pkmImagePreview)
        id_pokemon.text = "N.° ${pkm.id.toString()}"
        textView.text = pkm.name;
        type1.text = pkm.type1
        type2.text = pkm.type2
        Weight.text = "${pkm.weight.toString()} KG"
        Height.text = "${pkm.height.toString()} M"
        hp.text = "HP: ${pkm.hp.toString()}"
        attack.text = "Attack: ${pkm.attack.toString()}"
        defense.text = "Defense: ${pkm.defense.toString()}"
        attackSpecial.text = "Special Attack: ${pkm.specialAttack.toString()}"
        defenseSpecial.text = "Special Defense: ${pkm.specialDefense.toString()}"
        speed.text = "Speed: ${pkm.speed.toString()}"
    }
}