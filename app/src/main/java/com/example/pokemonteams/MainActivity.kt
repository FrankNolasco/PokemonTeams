package com.example.pokemonteams

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.pokemonteams.adapter.MemberPkmPreviewAdapter
import com.example.pokemonteams.clases.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var listaPKM = emptyList<Pokemon>()
        val dataBase = AppDataBase.getDatabase(this)
        dataBase.pokemon().getAll().observe(this, Observer {
            listaPKM = it
            GridLayoutManager(
                this,2
            ).apply{
                lista.layoutManager = this
            }
            lista.adapter = MemberPkmPreviewAdapter(listaPKM)
        })


        ButtonNewPokemon.setOnClickListener{
            val intent = Intent(this,NuevoPokemon::class.java)
            startActivity(intent)
        }

    }
}