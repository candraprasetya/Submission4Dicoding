/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.kardusinfo.footballapps.model.favorites.ClubTable
import org.jetbrains.anko.db.*

class ClubDB(ctx: Context) : ManagedSQLiteOpenHelper(ctx, MainDB.DATABASE_TEAM_NAME, null, 1) {
    companion object {
        private var instance: ClubDB? = null

        @Synchronized
        fun getInstance(ctx: Context): ClubDB {
            if (instance == null) {
                instance = ClubDB(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(ClubTable.TABLE_NAME, true,
                ClubTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                ClubTable.TEAM_ID to TEXT,
                ClubTable.TEAM_NAME to TEXT,
                ClubTable.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(ClubTable.TABLE_NAME, true)
    }

    val Context.teamDB: ClubDB
        get() = getInstance(applicationContext)
}