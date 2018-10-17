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
import com.kardusinfo.footballapps.activity.ClubHomeActivity
import org.jetbrains.anko.startActivity
import com.kardusinfo.footballapps.R
import com.kardusinfo.footballapps.fragment.MatchFavFragment
import com.kardusinfo.footballapps.fragment.NextMatchFragment
import com.kardusinfo.footballapps.fragment.PrevMatchFragment
import kotlinx.android.synthetic.main.activity_home_match.*

class MatchHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_match)
        supportActionBar?.title = "Match Info"
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, PrevMatchFragment()).commit()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_prev -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, PrevMatchFragment()).commit()
                    true
                }
                R.id.action_next -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, NextMatchFragment()).commit()
                    true
                }
                R.id.action_favorite_match -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MatchFavFragment()).commit()
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
