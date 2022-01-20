package com.skanderjabouzi.nbateamviewer.robots

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.skanderjabouzi.nbateamviewer.R
import com.skanderjabouzi.nbateamviewer.matchers.IndexMatcher
import com.skanderjabouzi.nbateamviewer.matchers.RecyclerViewMatcher
import com.skanderjabouzi.nbateamviewer.utils.*
import org.hamcrest.Matchers


class TeamsRobot {

    var context: Context = InstrumentationRegistry.getTargetContext()

    fun verifyTeamsToolbarIsDisplayed() {
        R.string.teams_list.checkFromStringIsDisplayed()
    }

    fun verifyTeamPlayerToolbarIsDisplayed() {
        R.string.players_list.checkFromStringIsDisplayed()
    }

    fun verifyTeamsListTitlesAreDisplayed() {
        onView(IndexMatcher(withId(R.id.team_name_label), 0)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.team_wins_label), 0)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.team_losses_label), 0)).check(matches(isDisplayed()))
    }

    fun verifyTeamPlayersListTitlesAreDisplayed() {
        onView(IndexMatcher(withId(R.id.player_name_title), 0)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.player_number_title), 0)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.player_position_title), 0)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.team_name_label), 1)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.team_wins_label), 1)).check(matches(isDisplayed()))
        onView(IndexMatcher(withId(R.id.team_losses_label), 1)).check(matches(isDisplayed()))
    }

    fun verifyTeamsItemInListHasText(position: Int, text: String) {
        onView(
            RecyclerViewMatcher(R.id.teamsRecyclerView).atPositionOnView(
                position,
                R.id.team_name_value
            )
        ).checkHasText(text)
    }

    fun verifyTeamPlayersItemInListHasText(position: Int, text: String) {
        onView(
                RecyclerViewMatcher(R.id.playersRecyclerView).atPositionOnView(
                        position,
                        R.id.player_name_value
                )
        ).checkHasText(text)
    }

    fun scrollTeamsListTo(item: Int) {
        onView(withId(R.id.teamsRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(item));
    }

    fun scrollTeamPlayersListTo(item: Int) {
        onView(withId(R.id.playersRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(item));
    }

    fun selectSortByNameMenu() {
        openMenu(1)
        val appCompatTextView = onView(
                Matchers.allOf(
                        withId(R.id.title), withText("Sort by name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        appCompatTextView.perform(ViewActions.click())
    }

    fun selectSortByWinsMenu() {
        openMenu(2)
        val appCompatTextView = onView(
                Matchers.allOf(
                        withId(R.id.title), withText("Sort by wins"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        appCompatTextView.perform(ViewActions.click())
    }

    fun selectSortByLossesMenu() {
        openMenu(2)
        val appCompatTextView = onView(
                Matchers.allOf(
                        withId(R.id.title), withText("Sort by losses"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        appCompatTextView.perform(ViewActions.click())
    }

    fun selectSortByPositionMenu() {
        openMenu(3)
        val appCompatTextView = onView(
                Matchers.allOf(
                        withId(R.id.title), withText("Sort by position"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        appCompatTextView.perform(ViewActions.click())
    }

    fun selectSortByNumberMenu() {
        openMenu(3)
        val appCompatTextView = onView(
                Matchers.allOf(
                        withId(R.id.title), withText("Sort by number"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        appCompatTextView.perform(ViewActions.click())
    }

    fun selectTeamFromList(position: Int) {
        R.id.teamsRecyclerView.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
                position,
            ViewActions.click()
        )
    }
    fun checkHasText(id: Int, text: String) {
        id.checkHasText(text)
    }

    fun checkContainText(id: Int, text: String) {
        id.checkContainsText(text)
    }

    private fun openMenu(position: Int) {
        val overflowMenuButton = onView(
                Matchers.allOf(
                        withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        position
                                ),
                                0
                        ),
                        isDisplayed()
                )
        )
        overflowMenuButton.perform(ViewActions.click())
    }

}