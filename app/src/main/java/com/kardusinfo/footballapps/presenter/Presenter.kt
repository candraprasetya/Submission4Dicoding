/*
 *  *Copyright (C) Kardusinfo.com - All Rights Reserved
 *                             * Unauthorized copying of this file, via any medium is strictly prohibited
 *                             * Proprietary and confidential
 *                             * Written by Candra Prasetya <candraramadhanp@outlook.com>, 2018.
 *
 */

package com.kardusinfo.footballapps.presenter

import com.google.gson.Gson
import com.kardusinfo.footballapps.IdlingResourceProvider
import com.kardusinfo.footballapps.api.ApiRepository
import com.kardusinfo.footballapps.api.ApiDB
import com.kardusinfo.footballapps.model.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class Presenter<in T>(
        private val view: T,
        private val repository: ApiRepository,
        private val gson: Gson,
        private val context: CoroutineContextProvider
) {
    fun getEventDetail(eventId: String?) {
        if (view is EventDetailView) {
//            IdlingResourceProvider.idlingRes.increment()
            view.showLoading()
            async(context.main) {
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getEventDetail(eventId)),
                            EventResponse::class.java).events[0]
                }
                view.showEventDetail(data.await())
                view.hideLoading()
            }
//            IdlingResourceProvider.idlingRes.decrement()
//            IdlingResourceProvider.idlingRes.dumpStateToLogs()

        }
    }

    fun getNextMatch(leagueId: String?) {
        if (view is EventView) {
//            IdlingResourceProvider.idlingRes.increment()
            async(context.main) {
                view.showLoading()
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getNextMatches(leagueId)),
                            EventResponse::class.java).events
                }
                view.showEventList(data.await())
                view.hideLoading()
            }
//            IdlingResourceProvider.idlingRes.decrement()
//            IdlingResourceProvider.idlingRes.dumpStateToLogs()

        }
    }

    fun getPrevMatch(leagueId: String?) {
        if (view is EventView) {
//            IdlingResourceProvider.idlingRes.increment()
            async(context.main) {
                view.showLoading()
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getPrevMatches(leagueId)),
                            EventResponse::class.java).events
                }

                view.showEventList(data.await())
                view.hideLoading()
            }
//            IdlingResourceProvider.idlingRes.decrement()
//            IdlingResourceProvider.idlingRes.dumpStateToLogs()

        }
    }


    fun getTeamList(league: String?) {
       IdlingResourceProvider.idlingRes.increment()
        IdlingResourceProvider.idlingRes.dumpStateToLogs()
        if (view is TeamView) {
            async(context.main) {
                view.showLoading()
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getTeams(league)),
                            TeamResponse::class.java).teams
                }

                view.showTeamList(data.await())
                view.hideLoading()
                IdlingResourceProvider.idlingRes.decrement()
                IdlingResourceProvider.idlingRes.decrement()
                IdlingResourceProvider.idlingRes.dumpStateToLogs()
            }
        }
    }

    fun getSepecificTeam(teamName: String?) {
        if (view is TeamDetailView) {
            IdlingResourceProvider.idlingRes.increment()
            IdlingResourceProvider.idlingRes.dumpStateToLogs()
            async(context.main) {
                view.showLoading()
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getSpecificTeam(teamName)),
                            TeamResponse::class.java).teams[0]
                }

                view.showTeamDetail(data.await())
                view.hideLoading()
            }
            IdlingResourceProvider.idlingRes.decrement()
            IdlingResourceProvider.idlingRes.dumpStateToLogs()
        }
        if (view is EventDetailView) {
            async(context.main) {
//                IdlingResourceProvider.idlingRes.increment()
                view.showLoading()
                val data = bg {
                    gson.fromJson(repository.doRequest(ApiDB.getSpecificTeam(teamName)),
                            TeamResponse::class.java).teams[0]
                }

                view.showTeamEmblem(data.await())
                view.hideLoading()
            }
//            IdlingResourceProvider.idlingRes.decrement()
//            IdlingResourceProvider.idlingRes.dumpStateToLogs()
        }

    }
}
