package com.skanderjabouzi.nbateamviewer.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.action.ViewActions
//import ca.manulife.MobileGBRS.R
import com.manulife.mobile.gbrs.*
import com.manulife.mobile.gbrs.matchers.RecyclerViewMatcher
import com.skanderjabouzi.nbateamviewer.R

class SavedClaimRobot {
    fun startNewClaim() {
        if (R.string.claim_saved_alert_title.matchViewFromText().isVisible()) {
            R.string.claim_saved_alert_new_claim.performClickFromText()
        }
    }

    fun verifySaveDialogIsNotDisplayed() {
        R.string.claim_saved_alert_title.checkDoesNotExistFromString()
    }

    fun verifySaveDialogIsDisplayed() {
        R.string.claim_cancel_alert_title.checkFromStringIsDisplayed()
    }

    fun verifyContinueDialogIsDisplayed() {
        R.string.claim_saved_alert_title.checkFromStringIsDisplayed()
    }

    fun verifyContinueDialogIsNotDisplayed() {
        R.string.claim_saved_alert_title.checkDoesNotExistFromString()
    }

    fun selectbeneficiaryName() {
        R.id.ac_select_member.performClick()
        R.id.listview.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            2,
            ViewActions.click()
        )
    }

    fun selectSaveClaim() {
        R.string.claim_cancel_alert_save.performClickFromText()
    }

    fun selectDontSaveClaim() {
        R.string.claim_cancel_alert_exit.performClickFromText()
    }

    fun selectContinueClaim() {
        R.string.claim_saved_alert_continue.performClickFromText()
    }

    fun setOtherCoverage() {
        R.id.other_coverage_question.performClick()
        R.id.add_other_cvrg.performClick()
        R.id.other_provider_list_button.performClick()
        "Desjardins".performClickFromTag()
        R.id.other_provider_list_button.performClick()
    }

    fun selectParamedicalType() {
        R.id.paramedical_service_select.performScrollTo().performClick()
        R.id.search_recycle.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            5,
            ViewActions.click()
        )
    }

    fun selectProvider() {
        R.id.provider_name_select.performScrollTo().performClick()
        R.id.recycler.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            0,
            ViewActions.click()
        )
    }

    fun selectVisionProvider() {
        R.id.provider_name_select.performScrollTo().performClick()
        R.id.recycler.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            3,
            ViewActions.click()
        )
    }

    fun setParamedicalExpenses() {
        R.id.add_expns.performScrollTo().performClick()
        R.id.ac_select_expense_input.performClick()
        R.id.radio_list.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            0,
            ViewActions.click()
        )
        R.id.ac_select_length_input.performClick()
        R.id.radio_list.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            0,
            ViewActions.click()
        )
        R.id.lbl_amt_edit.performTypeText("10")
        R.id.ac_next.performClick()
    }

    fun setVisionExpenses() {
        R.id.add_expns.performScrollTo().performClick()
        R.id.ac_select_expense_input.performClick()
        R.id.list_expenses_types.performActionOnRecyclerItemAtPosition<RecyclerView.ViewHolder>(
            0,
            ViewActions.click()
        )
        R.id.lbl_amt_edit.performTypeText("10")
        R.id.ac_next.performClick()
    }

    fun setPaymeWith() {
        R.id.ac_select_reimburse.performScrollTo().performClick()
        R.id.radio_health_plan.performClick()
        R.id.ac_save.performClick()
    }

    fun checkHasText(id: Int, text: String) {
        id.checkHasText(text)
    }

    fun checkContainText(id: Int, text: String) {
        id.checkContainsText(text)
    }
}