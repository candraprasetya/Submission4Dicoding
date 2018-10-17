/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.api

import com.kardusinfo.footballapps.BuildConfig.BASE_URL

object ApiDB {

    val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

    fun getEventDetail(eventId: String?): String {
        return "${BASE_URL}lookupevent.php?id=$eventId"
    }

    fun getSpecificTeam(teamName: String?): String {
        return "${BASE_URL}searchteams.php?t=$teamName"
    }

    fun getNextMatches(leagueId: String?): String {
        return "${BASE_URL}eventspastleague.php?id=$leagueId"
    }

    fun getTeams(league: String?): String {
        return "${BASE_URL}search_all_teams.php?l=$league"
    }

    fun getPrevMatches(leagueId: String?): String {
        return "${BASE_URL}eventspastleague.php?id=$leagueId"
    }

}