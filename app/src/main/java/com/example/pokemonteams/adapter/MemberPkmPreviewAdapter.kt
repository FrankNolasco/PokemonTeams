package com.example.pokemonteams.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import com.example.pokemonteams.DetailPokemon
import com.example.pokemonteams.clases.Pokemon
import com.example.pokemonteams.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_card.view.*

class MemberPkmPreviewAdapter(
    private val listaPokemon:List<Pokemon>)
    : RecyclerView.Adapter<MemberPkmPreviewAdapter.ViewHolder>(){

    private lateinit var ctx: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the custom view from xml layout file
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_pokemon_card,parent,false)
        ctx = parent.context
        // return the view holder
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // display the current animal
        val card = holder.card
        val pokemon =listaPokemon[position]

        card.BtnDetail.setOnClickListener {
            val intent = Intent(ctx, DetailPokemon::class.java)
            intent.putExtra("Pokemon", pokemon)
            ctx.startActivity(intent);
        }

        setPokemonOnCard(card, pokemon)
    }

    fun setPokemonOnCard(card: View,pkm: Pokemon){
        card.id_pokemon.text = "N.° ${pkm.id}"
        card.pkmName.text = pkm.name
        if(pkm.sprite.isNotEmpty()){
            Picasso.get().load(pkm.sprite.toUri()).into(card.pkmSprite)
        }
    }

    override fun getItemCount(): Int {
        // number of items in the data set held by the adapter
        return listaPokemon.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card: View = itemView
    }


    // this two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }


}