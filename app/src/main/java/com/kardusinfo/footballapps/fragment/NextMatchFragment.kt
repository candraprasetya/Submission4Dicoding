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
import com.google.gson.Gson
import com.kardusinfo.footballapps.activity.InfoActivity
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.adapter.MatchAdapter
import com.kardusinfo.footballapps.api.ApiRepository
import com.kardusinfo.footballapps.model.CoroutineContextProvider
import com.kardusinfo.footballapps.model.Event
import com.kardusinfo.footballapps.presenter.EventView
import com.kardusinfo.footballapps.presenter.Presenter
import kotlinx.android.synthetic.main.fragment_layout.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), EventView {
    private lateinit var presenter: Presenter<EventView>
    private var eventList: MutableList<Event> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val leagueId = "4328"

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = Presenter(this, ApiRepository(), Gson(), CoroutineContextProvider())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        adapter = MatchAdapter(activity!!.applicationContext, eventList) {
            startActivity<InfoActivity>("eventId" to it.idEvent)
        }
        recyclerView = view.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.swipeRefresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(leagueId)
        }

        presenter.getNextMatch(leagueId)
        return view
    }

    override fun showEventList(events: List<Event>) {
        eventList.clear()
        eventList.addAll(events)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }


}