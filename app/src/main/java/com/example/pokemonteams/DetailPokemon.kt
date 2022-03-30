package com.example.pokemonteams

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.LiveData
import com.example.pokemonteams.clases.Pokemon
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_nuevo_pokemon.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPokemon : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var _pokemon: Pokemon
    private lateinit var dataBase: AppDataBase
    private lateinit var liveData: LiveData<Pokemon>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)
    }

    override fun onStart() {
        super.onStart()
        dataBase = AppDataBase.getDatabase(this)
        if(intent.hasExtra("Pokemon")){
            val pokemonIntent = intent.extras?.getSerializable("Pokemon") as Pokemon
            liveData = dataBase.pokemon().get(pokemonIntent.idPokemon)
            liveData.observe(this, Observer{
                _pokemon = it
                pokemonPreview(_pokemon)
                createFragment(R.id.map)
            })
        }
    }

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

    private fun createFragment(id: Int) {
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }
    private fun createMarker() {
        if(_pokemon !== null){

            val coordinates = LatLng(java.lang.Double.parseDouble(_pokemon.coordsX) , java.lang.Double.parseDouble(_pokemon.coordsY))
            val marker: MarkerOptions = MarkerOptions().position(coordinates).title(_pokemon.name)

            map.addMarker(marker)

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(coordinates, 10f),
                4000,
                null
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.edit_item->{
                val intent = Intent(this, NuevoPokemon::class.java)
                intent.putExtra("Pokemon",_pokemon)
                startActivity(intent)
            }

            R.id.delete_item->{
                CoroutineScope(Dispatchers.IO).launch {
                    dataBase.pokemon().delete(_pokemon)
                    this@DetailPokemon.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



}