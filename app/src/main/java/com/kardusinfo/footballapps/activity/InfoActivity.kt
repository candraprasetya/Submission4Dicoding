/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kardusinfo.footballapps.model.Event
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.api.ApiRepository
import com.kardusinfo.footballapps.database.MatchDB
import com.kardusinfo.footballapps.model.CoroutineContextProvider
import com.kardusinfo.footballapps.model.Team
import com.kardusinfo.footballapps.model.favorites.MatchFav
import com.kardusinfo.footballapps.model.favorites.MatchTable
import com.kardusinfo.footballapps.presenter.EventDetailView
import com.kardusinfo.footballapps.presenter.Presenter
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh

class InfoActivity : AppCompatActivity(), EventDetailView, AnkoLogger {


    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: Presenter<EventDetailView>
    private lateinit var eventId: String
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventId = intent.getStringExtra("eventId")
        presenter = Presenter(this, ApiRepository(), Gson(), CoroutineContextProvider())
        presenter.getEventDetail(eventId)


        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefresh.onRefresh {
            presenter.getEventDetail(eventId)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun showEventDetail(event: Event) {
        this.event = event
        tanggalnya.text = event.dateEvent ?: " "

        home_name.text = event.strHomeTeam ?: " "
        home_scores.text = event.intHomeScore ?: " "
        home_formation.text = event.strHomeFormation ?: " "
        home_goals.text = parserGoal(event.strHomeGoalDetails ?: " ")
        home_shots.text = event.intHomeShots?.toString()
        home_gk.text = parserGoal(event.strHomeLineupGoalkeeper ?: " ")
        home_def.text = parser(event.strHomeLineupDefense ?: " ")
        home_mdf.text = parser(event.strHomeLineupMidfield ?: " ")
        home_fwd.text = parser(event.strHomeLineupForward ?: " ")
        home_subs.text = parser(event.strHomeLineupSubstitutes ?: " ")

        away_name.text = event.strAwayTeam ?: " "
        away_scores.text = event.intAwayScore ?: " "
        away_formation.text = event.strAwayFormation ?: " "
        away_goals.text = parserGoal(event.strAwayGoalDetails ?: " ")
        away_shots.text = event.intAwayShots?.toString()
        away_gk.text = parserGoal(event.strAwayLineupGoalkeeper ?: " ")
        away_def.text = parser(event.strAwayLineupDefense ?: " ")
        away_mdf.text = parser(event.strAwayLineupMidfield ?: " ")
        away_fwd.text = parser(event.strAwayLineupForward ?: " ")
        away_subs.text = parser(event.strAwayLineupSubstitutes ?: " ")

//        TO SHOW GRAB THE TEAM EMBLEM
        presenter.getSepecificTeam(event.strHomeTeam)
        presenter.getSepecificTeam(event.strAwayTeam)
        getFavoriteState()
    }

    override fun showTeamEmblem(team: Team) {
        if (team.teamName.equals(event.strHomeTeam)) {
            Glide.with(this).load(team.teamBadge).into(home_logo)
        } else if (team.teamName.equals(event.strAwayTeam)) {
            Glide.with(this).load(team.teamBadge).into(away_logo)
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

//    DATABASE STUFF

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_favourite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun getFavoriteState() {
        try {
            MatchDB.getInstance(this).use {
                val result = select(MatchTable.TABLE_NAME).whereArgs("${MatchTable.ID_EVENT} = ${event.idEvent}")
                val favorite = result.parseList(classParser<MatchFav>())
                if (!favorite.isEmpty()) isFavorite = true
                setFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            info { e.localizedMessage }
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_deactivated)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_activated)
    }

    private fun addToFavorite() {
        try {
            val homeScore = event.intHomeScore ?: " "
            val awayScore = event.intAwayScore ?: " "
            MatchDB.getInstance(this).use {
                insert(MatchTable.TABLE_NAME,
                        MatchTable.ID_EVENT to event.idEvent,
                        MatchTable.EVENT_DATE to event.dateEvent,
                        MatchTable.HOME_TEAM to event.strHomeTeam,
                        MatchTable.HOME_SCORE to homeScore,
                        MatchTable.AWAY_TEAM to event.strAwayTeam,
                        MatchTable.AWAY_SCORE to awayScore
                )
            }
            snackbar(swipeRefresh, "added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            MatchDB.getInstance(this).use {
                delete(MatchTable.TABLE_NAME, "${MatchTable.ID_EVENT} = ${event.idEvent}")
            }
            snackbar(swipeRefresh, "removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

}
