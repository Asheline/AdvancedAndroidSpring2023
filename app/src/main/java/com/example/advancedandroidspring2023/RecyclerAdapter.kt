package com.example.advancedandroidspring2023

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advancedandroidspring2023.databinding.RecyclerviewItemRowBinding


// fragmentin päätasolle (eli jäsenmuuttujiksi onCreateViewin ulkopuolelle), tarvitaan seuraavat:

// alustetaan viittaus adapteriin sekä luodaan LinearLayoutManager
// RecyclerView tarvitsee jonkin LayoutManagerin, joista yksinkertaisin on Linear
private lateinit var adapter: CommentAdapter
private lateinit var linearLayoutManager: LinearLayoutManager

class RecyclerAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<RecyclerAdapter.CommentHolder>() {

    // alustetaan binding layet -> recyclerview_item_row.xml
    private var _binding: RecyclerviewItemRowBinding? = null
    private val binding get() = _binding!!

    // RecyclerAdapter vaatii, että luokassa on toteutettuna:
    // getItemCount, onCreateViewHolder, OnBindViewHolder

    // jotta RecyclerView tietää kuinka monta itemiä listassa on
    override fun getItemCount() = comments.size

    // tämä funktio kytkee jokaisen yksittäisen datan listassa omaan ulkoasuunsa
    // (recyclerview_item_row.xml)
    override fun onBindViewHolder(holder: RecyclerAdapter.CommentHolder, position: Int) {
        val itemComment = comments[position]
        holder.bindComment(itemComment)
    }

    // luodaan binding layer ja kytketään se CommentHolderiin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.CommentHolder {
        _binding = RecyclerviewItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolder(binding)
    }

    // CommentHolder kytkee datan ulkoasuun jokaisen itemin kohdalla
    class CommentHolder(v: RecyclerviewItemRowBinding) : RecyclerView.ViewHolder(v.root), View.OnClickListener {

        // view => binding layer
        // comment => yksittäinen comment-data
        private var view: RecyclerviewItemRowBinding = v
        private var comment: Comment? = null

        // v.root tarvitaan siksi, koska v on tässä tapauksessa binding layer, ei View
        // v.root on binding layerin pohjimmainen View, eli ulkoasu

        init {
            v.root.setOnClickListener(this)
        }

        // tätä fuktiota kutsutaan jokaisen comment-datan kohdalla
        // kun se asetetaan listaan
        // toisin sanoen, tässä funktiossa päätetään mikä muuttuja
        // kytketään mihinkiin Viewiin, esim. TextViewiin
        fun bindComment(comment: Comment)
        {

        }

        override fun onClick(v: View) {
            // huom: comment.id on Int? eli "nullable" Int, minkä vuoksi meidän pitää muuntaa se vielä Int-muotoon
            val action = apiFragmentDirections.actionApiFragmentToApiDetailFragment(comment?.id as Int)
            v.findNavController().navigate(action)

        }
        }
    }


