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
import com.kardusinfo.footballapps.model.Team
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.api.ApiRepository
import com.kardusinfo.footballapps.database.ClubDB
import com.kardusinfo.footballapps.model.CoroutineContextProvider
import com.kardusinfo.footballapps.model.favorites.ClubFav
import com.kardusinfo.footballapps.model.favorites.ClubTable
import com.kardusinfo.footballapps.presenter.Presenter
import com.kardusinfo.footballapps.presenter.TeamDetailView
import kotlinx.android.synthetic.main.activity_info_club.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

class ClubInfoActivity : AppCompatActivity(), TeamDetailView, AnkoLogger {
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: Presenter<TeamDetailView>
    private lateinit var teamName: String
    private lateinit var team: Team


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_club)
        supportActionBar!!.title = "Team Detail"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        presenter = Presenter(this, ApiRepository(), Gson(), CoroutineContextProvider())
        teamName = intent.getStringExtra("team")
        presenter.getSepecificTeam(teamName)
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )
        swipeRefresh.onRefresh {
            presenter.getSepecificTeam(teamName)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_deactivated)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.fav_activated)
    }

    private fun getFavoriteState() {
        ClubDB.getInstance(this).use {
            val result = select(ClubTable.TABLE_NAME).whereArgs("${ClubTable.TEAM_ID} = ${team.teamId}")
            val favorite = result.parseList(classParser<ClubFav>())
            if (!favorite.isEmpty()) isFavorite = true
            setFavorite()
        }
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

    private fun addToFavorite() {
        try {
            ClubDB.getInstance(this).use {
                insert(ClubTable.TABLE_NAME,
                        ClubTable.TEAM_ID to team.teamId,
                        ClubTable.TEAM_NAME to team.teamName,
                        ClubTable.TEAM_BADGE to team.teamBadge
                )
            }
            snackbar(scrollView, "${team.teamName} succesfully added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            ClubDB.getInstance(this).use {
                delete(ClubTable.TABLE_NAME, "${ClubTable.TEAM_ID} = ${team.teamId}")
            }
            snackbar(scrollView, "${team.teamName} is removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(scrollView, e.localizedMessage).show()
        }
    }

    override fun showTeamDetail(team: Team) {
        this.team = team
        getFavoriteState()
        Glide.with(this).load(team.teamBadge).into(imgTeam)
        tvTeamName.text = team.teamName
        tvTeamYear.text = team.formedYear.toString()
        tvTeamStadium.text = team.stadium
        tvTeamDescription.text = team.temDescription
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

}
