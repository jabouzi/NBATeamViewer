package com.skanderjabouzi.nbateamviewer

import androidx.test.espresso.Espresso

import ca.manulife.MobileGBRS.R
import com.manulife.mobile.gb.GBLoginActivityAbstractTest
import com.manulife.mobile.gb.robots.submitClaim.SavedClaimRobot
import com.manulife.mobile.gb.tests.ParamedicalRegression
import com.manulife.mobile.gb.tests.RegressionTest
import com.manulife.mobile.gbrs.*
import com.manulife.mobile.gbrs.utils.SharedPrefs
import org.junit.Assume
import org.junit.Before
import org.junit.Test
import java.util.*

@RegressionTest
@ParamedicalRegression
class SaveParamedicalClaimViewTestEN: GBLoginActivityAbstractTest() {

    private val saveClaimRobot: SavedClaimRobot by lazy {
        SavedClaimRobot()
    }

    @Before
    fun setUp() {
        Assume.assumeTrue(emulatorLanguage == Locale.ENGLISH.language)
        signInGB("72896", "12", "123456")
        2.clickItemOnData()
        SharedPrefs.delete("savedClaim")
        R.id.item_paramedical.performClick()
    }

    @Test
    fun testFirstTimeNoSaveClaimDialogDisplayed() {
        saveClaimRobot.startNewClaim()
        Espresso.pressBack()
        saveClaimRobot.verifySaveDialogIsNotDisplayed()
    }

    @Test
    fun testSelectNameSaveClaimDialogDisplayed() {
        saveClaimRobot.startNewClaim()
        saveClaimRobot.selectbeneficiaryName()
        Espresso.pressBack()
        saveClaimRobot.verifySaveDialogIsDisplayed()
    }

    @Test
    fun testSelectNameContinueClaimDialogDisplayed() {
        saveClaimRobot.startNewClaim()
        saveClaimRobot.selectbeneficiaryName()
        Espresso.pressBack()
        saveClaimRobot.selectSaveClaim()
        R.id.item_paramedical.performClick()
        saveClaimRobot.verifyContinueDialogIsDisplayed()
    }

    @Test
    fun testSelectNameContinueClaimDialogNotDisplayed() {
        saveClaimRobot.startNewClaim()
        saveClaimRobot.selectbeneficiaryName()
        Espresso.pressBack()
        saveClaimRobot.selectDontSaveClaim()
        R.id.item_paramedical.performClick()
        saveClaimRobot.verifyContinueDialogIsNotDisplayed()
    }

    @Test
    fun testWithFullDataFilled() {
        saveClaimRobot.startNewClaim()
        saveClaimRobot.selectbeneficiaryName()
        saveClaimRobot.setOtherCoverage()
        saveClaimRobot.selectParamedicalType()
        saveClaimRobot.selectProvider()
        saveClaimRobot.setParamedicalExpenses()
        saveClaimRobot.setPaymeWith()
        Espresso.pressBack()
        saveClaimRobot.selectSaveClaim()
        R.id.item_paramedical.performClick()
        saveClaimRobot.selectContinueClaim()
        saveClaimRobot.checkHasText(R.id.ac_select_member, "KMOUVO,ZCBFA  ")
        saveClaimRobot.checkContainText(R.id.other_coverage_text, "Desjardins")
        saveClaimRobot.checkHasText(R.id.paramedical_service_select, "Massage therapist")
        saveClaimRobot.checkHasText(R.id.provider_name_select, "Robert Miller")
        saveClaimRobot.checkHasText(R.id.total_amt_holder, "$10.00")
        saveClaimRobot.checkHasText(R.id.ac_select_reimburse, "Health plan")
    }

    @Test
    fun testWithFullDataFilledAndSwitchToVision() {
        saveClaimRobot.startNewClaim()
        saveClaimRobot.selectbeneficiaryName()
        saveClaimRobot.setOtherCoverage()
        saveClaimRobot.selectParamedicalType()
        saveClaimRobot.selectProvider()
        saveClaimRobot.setParamedicalExpenses()
        saveClaimRobot.setPaymeWith()
        Espresso.pressBack()
        saveClaimRobot.selectSaveClaim()
        R.id.item_vision.performClick()
        saveClaimRobot.verifyContinueDialogIsNotDisplayed()
    }
}