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
class TeamsListViewTest {

    private val teamsRobot: TeamsRobot by lazy {
        TeamsRobot()
    }

    @Rule @JvmField var tasksActivityTestRule =
        ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun runActivityAndValidateViewsIsVisibleAndHasTexts () {
        teamsRobot.verifyTeamsToolbarIsDisplayed()
        teamsRobot.verifyTeamsListTitlesAreDisplayed()
        teamsRobot.verifyTeamsItemInListHasText(0, "San Antonio Spurs")
        teamsRobot.scrollTeamsListTo(29)
        teamsRobot.verifyTeamsItemInListHasText(29, "Sacramento Kings")
    }

    @Test
    fun runActivityAndSelectSortByNameAndValidateOrder () {
        teamsRobot.selectSortByNameMenu(2)
        teamsRobot.verifyTeamsItemInListHasText(0, "Atlanta Hawks")
        teamsRobot.scrollTeamsListTo(29)
        teamsRobot.verifyTeamsItemInListHasText(29, "Washington Wizards")
    }

    @Test
    fun runActivityAndSelectSortByWinsAndValidateOrder () {
        teamsRobot.selectSortByWinsMenu()
        teamsRobot.verifyTeamsItemInListHasText(0, "Golden State Warriors")
        teamsRobot.scrollTeamsListTo(29)
        teamsRobot.verifyTeamsItemInListHasText(29, "Milwaukee Bucks")
    }

    @Test
    fun runActivityAndSelectSortByLossesAndValidateOrder () {
        teamsRobot.selectSortByLossesMenu()
        teamsRobot.verifyTeamsItemInListHasText(0, "Milwaukee Bucks")
        teamsRobot.scrollTeamsListTo(29)
        teamsRobot.verifyTeamsItemInListHasText(29, "Golden State Warriors")
    }
}