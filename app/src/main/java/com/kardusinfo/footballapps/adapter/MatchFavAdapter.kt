/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_match.view.*
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.model.favorites.MatchFav


class MatchFavAdapter(private val context: Context, private val eventFavorite: List<MatchFav>, private val listener: (MatchFav) -> Unit) : RecyclerView.Adapter<MatchFavAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_match,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = eventFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(eventFavorite[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(eventFavorite: MatchFav, listener: (MatchFav) -> Unit) {

            itemView.tanggal_match.text = eventFavorite.dateEvent
            itemView.name_home.text = eventFavorite.strHomeTeam
            itemView.score_home.text = eventFavorite.intHomeScore
            itemView.name_away.text = eventFavorite.strAwayTeam
            itemView.score_away.text = eventFavorite.intAwayScore
            itemView.setOnClickListener {
                listener(eventFavorite)
            }

        }
    }
}
