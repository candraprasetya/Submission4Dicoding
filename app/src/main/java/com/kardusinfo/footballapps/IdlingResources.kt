package com.kardusinfo.footballapps

import android.support.test.espresso.idling.CountingIdlingResource


object IdlingResourceProvider {
    var idlingRes: CountingIdlingResource = CountingIdlingResource("DATA", true)
}