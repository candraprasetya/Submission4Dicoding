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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kardusinfo.footballapps.adapter.MatchFavAdapter
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.activity.InfoActivity
import com.kardusinfo.footballapps.database.MatchDB
import com.kardusinfo.footballapps.model.favorites.MatchFav
import com.kardusinfo.footballapps.model.favorites.MatchTable
import kotlinx.android.synthetic.main.fragment_layout.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class MatchFavFragment : Fragment() {
    private lateinit var eventAdapter: MatchFavAdapter
    private var eventFavorite: MutableList<MatchFav> = mutableListOf()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventAdapter = MatchFavAdapter(activity!!.applicationContext, eventFavorite) {
            startActivity<InfoActivity>("eventId" to it.idEvent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        recyclerView = view.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = eventAdapter

        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            getFavoriteEvent()
        }
        getFavoriteEvent()
        return view
    }

    private fun getFavoriteEvent() {
        MatchDB.getInstance(activity!!.applicationContext).use {
            val queryResult = select(MatchTable.TABLE_NAME)
            val favoriteEvent = queryResult.parseList(classParser<MatchFav>())
            eventFavorite.clear()
            eventFavorite.addAll(favoriteEvent)
            eventAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
