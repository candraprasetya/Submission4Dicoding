/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kardusinfo.footballapps.activity.ClubInfoActivity
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.adapter.ClubFavAdapter
import com.kardusinfo.footballapps.database.ClubDB
import com.kardusinfo.footballapps.model.favorites.ClubFav
import com.kardusinfo.footballapps.model.favorites.ClubTable
import kotlinx.android.synthetic.main.fragment_layout.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class ClubFavFragment : Fragment() {


    private lateinit var adapterTeam: ClubFavAdapter
    private var teamFavoriteList: MutableList<ClubFav> = mutableListOf()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapterTeam = ClubFavAdapter(activity!!.applicationContext, teamFavoriteList) {
            ctx.startActivity(intentFor<ClubInfoActivity>("team" to it.teamName))
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        getFavoriteTeam()
        swipeRefreshLayout.onRefresh {
            getFavoriteTeam()
        }

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = adapterTeam

        getFavoriteTeam()
        return view
    }


    private fun getFavoriteTeam() {
        ClubDB.getInstance(context!!).use {
            val queryResult = select(ClubTable.TABLE_NAME)
            val favoriteTeam = queryResult.parseList(classParser<ClubFav>())
            teamFavoriteList.clear()
            teamFavoriteList.addAll(favoriteTeam)
            adapterTeam.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }

}
