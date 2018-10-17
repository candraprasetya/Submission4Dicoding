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
import com.kardusinfo.footballapps.model.favorites.MatchTable
import org.jetbrains.anko.db.*

class MatchDB(ctx: Context) : ManagedSQLiteOpenHelper(ctx, MainDB.DATABASE_EVENT_NAME, null, 1) {
    companion object {
        private var instance: MatchDB? = null

        @Synchronized
        fun getInstance(ctx: Context): MatchDB {
            if (instance == null) {
                instance = MatchDB(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(MatchTable.TABLE_NAME, true,
                MatchTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                MatchTable.ID_EVENT to TEXT,
                MatchTable.EVENT_DATE to TEXT,
                MatchTable.HOME_TEAM to TEXT,
                MatchTable.HOME_SCORE to TEXT,
                MatchTable.AWAY_TEAM to TEXT,
                MatchTable.AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(MatchTable.TABLE_NAME, true)
    }

    val Context.eventDB: MatchDB
        get() = getInstance(applicationContext)

}