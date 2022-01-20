package com.skanderjabouzi.nbateamviewer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.skanderjabouzi.nbateamviewer.core.MainActivity
import com.skanderjabouzi.nbateamviewer.robots.TeamsRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TeamPlayersListViewTest {

    private val teamsRobot: TeamsRobot by lazy {
        TeamsRobot()
    }

    @Rule @JvmField var tasksActivityTestRule =
        ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun runActivityAndValidateViewsIsVisibleAndHasTexts () {
        teamsRobot.selectTeamFromList(0)
        teamsRobot.verifyTeamPlayerToolbarIsDisplayed()
        teamsRobot.verifyTeamPlayersListTitlesAreDisplayed()
        teamsRobot.verifyTeamPlayersItemInListHasText(0, "Aldridge, LaMarcus")
        teamsRobot.scrollTeamPlayersListTo(16)
        teamsRobot.verifyTeamPlayersItemInListHasText(16, "Zeller, Tyler")
    }

    @Test
    fun runActivityAndSelectSortByPositionAndValidateOrder () {
        teamsRobot.selectTeamFromList(0)
        teamsRobot.selectSortByPositionMenu()
        teamsRobot.verifyTeamPlayersItemInListHasText(0, "Pöltl, Jakob (FA)")
        teamsRobot.scrollTeamPlayersListTo(16)
        teamsRobot.verifyTeamPlayersItemInListHasText(16, "Walker, Lonnie")
    }

    @Test
    fun runActivityAndSelectSortByNumberAndValidateOrder () {
        teamsRobot.selectTeamFromList(0)
        teamsRobot.selectSortByNumberMenu()
        teamsRobot.verifyTeamPlayersItemInListHasText(0, "Walker, Lonnie")
        teamsRobot.scrollTeamPlayersListTo(16)
        teamsRobot.verifyTeamPlayersItemInListHasText(16, "Zeller, Tyler")
    }

    @Test
    fun runActivityAndSelectSortByNameAndValidateOrder () {
        teamsRobot.selectTeamFromList(0)
        teamsRobot.selectSortByNameMenu()
        teamsRobot.verifyTeamPlayersItemInListHasText(0, "Aldridge, LaMarcus")
        teamsRobot.scrollTeamPlayersListTo(16)
        teamsRobot.verifyTeamPlayersItemInListHasText(16, "Šamanić, Luka")
    }
}