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
import com.bumptech.glide.Glide
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.model.Team
import kotlinx.android.synthetic.main.item_club.view.*

class ClubAdapter(private val context: Context, private val teams: List<Team>, private val listener: (Team) -> Unit) : RecyclerView.Adapter<ClubAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_club,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(team: Team, listener: (Team) -> Unit) {
            itemView.namaClub.text = team.teamName
            Glide.with(context).load(team.teamBadge).into(itemView.logoClub)
            itemView.setOnClickListener {
                listener(team)
            }
        }
    }
}
