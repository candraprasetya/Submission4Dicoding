/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.model.favorites

import java.io.Serializable

data class ClubFav(val id: Long?,
                        val teamId: String?,
                        val teamName: String?,
                        val teamBadge: String?
) : Serializable

object ClubTable {
    const val TABLE_NAME = "Team_Favorite"
    const val ID = "ID"
    const val TEAM_NAME = "TEAM_NAME"
    const val TEAM_ID = "TEAM_ID"
    const val TEAM_BADGE = "TEAM_BADGE"
}