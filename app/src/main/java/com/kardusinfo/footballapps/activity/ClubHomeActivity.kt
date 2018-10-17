/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.activity.MatchHomeActivity
import com.kardusinfo.footballapps.fragment.ClubFavFragment
import com.kardusinfo.footballapps.fragment.ClubFragment
import kotlinx.android.synthetic.main.activity_home_club.*

class ClubHomeActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_club)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ClubFragment()).commit()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.act_teams -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ClubFragment()).commit()
                    true
                }
                R.id.act_favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, ClubFavFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ganti, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_team_app -> {
                startActivity<ClubHomeActivity>()
                finish()
            }
            R.id.action_match_app -> {
                startActivity<MatchHomeActivity>()
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}

