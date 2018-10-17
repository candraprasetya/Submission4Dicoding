/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.model.favorites

import java.io.Serializable

data class MatchFav(
        val id: Long,
        val idEvent: String,
        val dateEvent: String,
        val strHomeTeam: String,
        val intHomeScore: String,
        val strAwayTeam: String,
        val intAwayScore: String
): Serializable

object MatchTable {
    const val TABLE_NAME = "Event_Favorite"
    const val ID = "ID"
    const val ID_EVENT = "ID_EVENT"
    const val EVENT_DATE = "EVENT_DATE"
    const val HOME_TEAM = "HOME_TEAM"
    const val HOME_SCORE = "HOME_SCORE"
    const val AWAY_TEAM = "AWAY_TEAM"
    const val AWAY_SCORE = "AWAY_SCORE"
}