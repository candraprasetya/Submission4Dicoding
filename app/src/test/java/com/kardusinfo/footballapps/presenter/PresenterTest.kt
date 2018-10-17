package com.kardusinfo.footballapps.presenter

import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import com.kardusinfo.footballapps.api.ApiDB
import com.kardusinfo.footballapps.api.ApiRepository
import com.kardusinfo.footballapps.model.*

class PresenterTest {

    @Mock
    private lateinit var teamView: TeamView

    @Mock
    private lateinit var matchView: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apirepository: ApiRepository

    private lateinit var presenter: Presenter<TeamView>
    private lateinit var presenterMatch: Presenter<EventView>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = Presenter(teamView, apirepository, gson, TestContextProvider())
        presenterMatch = Presenter(matchView, apirepository, gson, TestContextProvider())
    }

    @Test
    fun getNextMatch() {
        val event = mutableListOf<Event>()
        val response = EventResponse(event)
        val leagueID = "4328"
        Mockito.`when`(gson.fromJson(apirepository.doRequest(ApiDB.getNextMatches(leagueID))
                , EventResponse::class.java)).thenReturn(response)
        presenterMatch.getNextMatch(leagueID)
        Mockito.verify(matchView).showLoading()
        Mockito.verify(matchView).showEventList(event)
        Mockito.verify(matchView).hideLoading()

    }

    @Test
    fun getTeamList() {
        val teams = mutableListOf<Team>()
        val response = TeamResponse(teams)
        val league = "English Premiere League"
        Mockito.`when`(gson.fromJson(apirepository.doRequest(ApiDB.getTeams(league)),
                TeamResponse::class.java)).thenReturn(response)
        presenter.getTeamList(league)

        Mockito.verify(teamView).showLoading()
        Mockito.verify(teamView).showTeamList(teams)
        Mockito.verify(teamView).hideLoading()
    }
}