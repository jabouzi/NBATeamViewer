package com.skanderjabouzi.nbateamviewer.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ScrollView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.EspressoKey
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.skanderjabouzi.nbateamviewer.matchers.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// ID matchers

fun Int.asIdViewMatcher(): Matcher<View> = withId(this)

fun Int.asStringViewMatcher(): Matcher<View> = withText(this)

fun String.withExactTextViewMatcher(): Matcher<View> = withText(this)

fun String.withContainsTextViewMatcher(): Matcher<View> = withText(containsString(this))

fun Int.visibleViewMtcher(): Matcher<View> = withEffectiveVisibility(Visibility.VISIBLE)

fun matchRoot(): ViewInteraction = matchView(isRoot())

fun matchView(matcher: Matcher<View>): ViewInteraction = onView(matcher)

fun matchTag(tag: String): ViewInteraction = onView(withTagValue(Matchers.`is`(tag)))

fun Int.matchView(): ViewInteraction = matchView(asIdViewMatcher())

fun Int.matchViewFromText(): ViewInteraction = matchView(asStringViewMatcher())

fun String.viewByExactText(): ViewInteraction = matchView(withExactTextViewMatcher())

fun String.viewByContainsText(): ViewInteraction = matchView(withContainsTextViewMatcher())

fun getViewByMultipleMatchers(list: List<Matcher<View>>): ViewInteraction = onView(allOf(list))
// ID extensions

fun Int.performViewAction(vararg action: ViewAction) = matchView().perform(*action)

fun Int.performClick() = matchView().performClick()

fun Int.performClickFromText() = matchViewFromText().performClick()

fun String.performClickFromTag() = matchTag(this).performClick()

fun Int.performTypeText(text: String) = matchView().performTypeText(text)

fun Int.performTypeTextIntoFocusedView(text: String) =
    matchView().performTypeTextIntoFocusedView(text)

fun Int.performReplaceText(text: String) = matchView().performReplaceText(text)

fun Int.performClearText() = matchView().performClearText()

fun Int.performClick(inputDevice: Int, buttonState: Int) =
    matchView().performClick(inputDevice, buttonState)

fun Int.performClick(rollbackAction: ViewAction) = matchView().performClick(rollbackAction)

fun Int.performCloseSoftKeyboard() = matchView().performCloseSoftKeyboard()

fun Int.performPressKey(keyCode: Int) = matchView().performPressKey(keyCode)

fun Int.performPressKey(espressoKey: EspressoKey) = matchView().performPressKey(espressoKey)

fun Int.performPressMenu() = matchView().performPressMenu()

fun Int.performPressBack() = matchView().performPressBack()

fun Int.performPressBackUnconditionally() = matchView().performPressBackUnconditionally()

fun Int.performPressImeActionButton() = matchView().performPressImeActionButton()

fun Int.performScrollTo() = matchView().performScrollTo()

fun Int.performDoubleClick() = matchView().performDoubleClick()

fun Int.performLongClick() = matchView().performLongClick()

fun Int.performOpenLink(linkTextMatcher: Matcher<String>, uriMatcher: Matcher<Uri>) =
    matchView().performOpenLink(linkTextMatcher, uriMatcher)

fun Int.performOpenLinkWithText(linkText: String) = matchView().performOpenLinkWithText(linkText)

fun Int.performOpenLinkWithUri(uri: String) = matchView().performOpenLinkWithUri(uri)

fun Int.performSwipeUp() = matchView().performSwipeUp()

fun Int.performSwipeDown() = matchView().performSwipeDown()

fun Int.performSwipeLeft() = matchView().performSwipeLeft()

fun Int.performSwipeRight() = matchView().performSwipeRight()

//list-actions

fun Int.recyclerAdapterSize(): Int = matchView().recyclerAdapterSize()

fun Int.performScrollScrollViewToStart() = matchView().performScrollScrollViewToStart()

fun Int.performScrollScrollViewToEnd() = matchView().performScrollScrollViewToEnd()

fun Int.performScrollScrollViewTo(position: Int) = matchView().performScrollScrollViewTo(position)

fun Int.scrollViewSize(): Int = matchView().scrollViewSize()


fun Int.performOpenNavigationDrawer(gravity: Int = Gravity.START) =
    matchView().performOpenNavigationDrawer(gravity)

fun Int.performCloseNavigationDrawer(gravity: Int = Gravity.START) =
    matchView().performCloseNavigationDrawer(gravity)

fun Int.performNavigateNavViewTo(id: Int) = matchView().performNavigateNavViewTo(id)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerHolderItem(
    viewHolderMatcher: Matcher<T>,
    action: ViewAction
) = matchView().performActionOnRecyclerHolderItem(viewHolderMatcher, action)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerItem(
    itemViewMatcher: Matcher<View>,
    action: ViewAction
) = matchView().performActionOnRecyclerItem<T>(itemViewMatcher, action)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerItemAtPosition(
    position: Int,
    action: ViewAction
) = matchView().performActionOnRecyclerItemAtPosition<T>(position, action)

// interactions

fun ViewInteraction.performClick(): ViewInteraction = perform(click())

fun ViewInteraction.performTypeText(text: String): ViewInteraction =
    perform(typeText(text), closeSoftKeyboard())

fun ViewInteraction.performTypeTextIntoFocusedView(text: String): ViewInteraction =
    perform(typeTextIntoFocusedView(text), closeSoftKeyboard())

fun ViewInteraction.performReplaceText(text: String): ViewInteraction =
    perform(replaceText(text), closeSoftKeyboard())

fun ViewInteraction.performClearText(): ViewInteraction = perform(clearText(), closeSoftKeyboard())

fun ViewInteraction.performClick(inputDevice: Int, buttonState: Int): ViewInteraction =
    perform(click(inputDevice, buttonState))

fun ViewInteraction.performClick(rollbackAction: ViewAction): ViewInteraction =
    perform(click(rollbackAction))

fun ViewInteraction.performCloseSoftKeyboard(): ViewInteraction = perform(closeSoftKeyboard())

fun ViewInteraction.performPressKey(keyCode: Int): ViewInteraction = perform(pressKey(keyCode))

fun ViewInteraction.performPressKey(espressoKey: EspressoKey): ViewInteraction =
    perform(pressKey(espressoKey))

fun ViewInteraction.performPressMenu(): ViewInteraction = perform(pressMenuKey())

fun ViewInteraction.performPressBack(): ViewInteraction = perform(pressBack())

fun ViewInteraction.performPressBackUnconditionally(): ViewInteraction =
    perform(pressBackUnconditionally())

fun ViewInteraction.performPressImeActionButton(): ViewInteraction = perform(pressImeActionButton())

fun ViewInteraction.performScrollTo(): ViewInteraction = perform(scrollTo())

fun ViewInteraction.performDoubleClick(): ViewInteraction = perform(doubleClick())

fun ViewInteraction.performLongClick(): ViewInteraction = perform(longClick())

fun ViewInteraction.performOpenLink(
    linkTextMatcher: Matcher<String>,
    uriMatcher: Matcher<Uri>
): ViewInteraction = perform(openLink(linkTextMatcher, uriMatcher))

fun ViewInteraction.performOpenLinkWithText(linkText: String): ViewInteraction =
    perform(openLinkWithText(linkText))

fun ViewInteraction.performOpenLinkWithUri(uri: String): ViewInteraction =
    perform(openLinkWithUri(uri))

fun ViewInteraction.performSwipeUp(): ViewInteraction = perform(swipeUp())

fun ViewInteraction.performSwipeDown(): ViewInteraction = perform(swipeDown())

fun ViewInteraction.performSwipeLeft(): ViewInteraction = perform(swipeLeft())

fun ViewInteraction.performSwipeRight(): ViewInteraction = perform(swipeRight())

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerHolderItem(
    viewHolderMatcher: Matcher<T>,
    action: ViewAction
): ViewInteraction = perform(actionOnHolderItem(viewHolderMatcher, action))

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerItem(
    itemViewMatcher: Matcher<View>,
    action: ViewAction
): ViewInteraction = perform(actionOnItem<T>(itemViewMatcher, action))

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerItemAtPosition(
    position: Int,
    action: ViewAction
): ViewInteraction = perform(actionOnItemAtPosition<T>(position, action))

fun ViewInteraction.performOpenNavigationDrawer(gravity: Int = Gravity.START): ViewInteraction =
    perform(DrawerActions.open(gravity))

fun ViewInteraction.performCloseNavigationDrawer(gravity: Int = Gravity.START): ViewInteraction =
    perform(DrawerActions.close(gravity))

fun ViewInteraction.performNavigateNavViewTo(id: Int): ViewInteraction =
    perform(NavigationViewActions.navigateTo(id))

// assertions

fun String.isTextOf(@IdRes viewId: Int) = viewId.matchView().check(matches(withText(this)))

fun Int.isStringResOf(@IdRes viewId: Int) = viewId.matchView().check(matches(withText(this)))

inline fun <reified T : View> ViewInteraction.checkHasChild(): ViewInteraction =
    check(matches(hasDescendant(isAssignableFrom(T::class.java))))

inline fun <reified T : View> ViewInteraction.checkIsAssignableFrom(): ViewInteraction =
    check(matches(isAssignableFrom(T::class.java)))

fun ViewInteraction.checkIsDisplayed(): ViewInteraction = check(matches(isDisplayed()))

fun ViewInteraction.checkIsNotDisplayed(): ViewInteraction =
    check(matches(Matchers.not(isDisplayed())))

fun ViewInteraction.checkIsCompletelyDisplayed(): ViewInteraction =
    check(matches(isCompletelyDisplayed()))

fun ViewInteraction.checkIsNotCompletelyDisplayed(): ViewInteraction =
    check(matches(Matchers.not(isCompletelyDisplayed())))

fun ViewInteraction.checkIsVisible(): ViewInteraction =
    check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

fun ViewInteraction.checkIsInvisible(): ViewInteraction =
    check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))

fun ViewInteraction.checkIsGone(): ViewInteraction =
    check(matches(withEffectiveVisibility(Visibility.GONE)))

fun ViewInteraction.checkIsSelected(): ViewInteraction = check(matches(isSelected()))

fun ViewInteraction.checkIsNotSelected(): ViewInteraction =
    check(matches(Matchers.not(isSelected())))

fun ViewInteraction.checkIsFocused(): ViewInteraction = check(matches(hasFocus()))

fun ViewInteraction.checkIsNotFocused(): ViewInteraction = check(matches(Matchers.not(hasFocus())))

fun ViewInteraction.checkIsFocusable(): ViewInteraction = check(matches(isFocusable()))

fun ViewInteraction.checkIsNotFocusable(): ViewInteraction =
    check(matches(Matchers.not(isFocusable())))

fun ViewInteraction.checkIsClickable(): ViewInteraction = check(matches(isClickable()))

fun ViewInteraction.checkIsNotClickable(): ViewInteraction =
    check(matches(Matchers.not(isClickable())))

fun ViewInteraction.checkIsEnabled(): ViewInteraction = check(matches(isEnabled()))

fun ViewInteraction.checkIsDisabled(): ViewInteraction = check(matches(Matchers.not(isEnabled())))

fun ViewInteraction.checkHasTag(tag: String): ViewInteraction =
    check(matches(withTagValue(Matchers.`is`(tag))))

fun ViewInteraction.checkDoesNotExist(): ViewInteraction = check(doesNotExist())

fun ViewInteraction.checkHasBackgroundColor(@ColorRes resId: Int): ViewInteraction =
    check(matches(BackgroundColorMatcher(resId = resId)))

fun ViewInteraction.checkHasBackgroundColor(colorCode: String): ViewInteraction =
    check(matches(BackgroundColorMatcher(colorCode = colorCode)))

fun ViewInteraction.checkHasEmptyText(): ViewInteraction = check(matches(withText("")))

fun ViewInteraction.checkHasAnyText(): ViewInteraction = check(matches(AnyTextMatcher()))

fun ViewInteraction.checkHasText(text: String): ViewInteraction = check(matches(withText(text)))

fun ViewInteraction.checkHasText(@StringRes resId: Int): ViewInteraction =
    check(matches(withText(resId)))

fun ViewInteraction.checkHasText(matcher: Matcher<String>): ViewInteraction =
    check(matches(withText(matcher)))

fun ViewInteraction.checkHasTextColor(@ColorRes resId: Int): ViewInteraction =
    check(matches(hasTextColor(resId)))

fun ViewInteraction.checkHasNoText(text: String): ViewInteraction =
    check(matches(not(withText(text))))

fun ViewInteraction.checkHasNoText(@StringRes resId: Int): ViewInteraction =
    check(matches(not(withText(resId))))

fun ViewInteraction.checkHasContentDescription(text: String): ViewInteraction =
    check(matches(withContentDescription(text)))

fun ViewInteraction.checkContainsText(text: String): ViewInteraction =
    check(matches(withText(Matchers.containsString(text))))

fun ViewInteraction.checkStartsWithText(text: String): ViewInteraction =
    check(matches(withText(Matchers.startsWith(text))))

fun ViewInteraction.checkHasHint(hint: String): ViewInteraction = check(matches(withHint(hint)))

fun ViewInteraction.checkHasHint(@StringRes resId: Int): ViewInteraction =
    check(matches(withHint(resId)))

fun ViewInteraction.checkIsChecked(): ViewInteraction = check(matches(isChecked()))

fun ViewInteraction.checkIsNotChecked(): ViewInteraction = check(matches(isNotChecked()))

fun ViewInteraction.checkIsViewPagerAtPage(index: Int): ViewInteraction =
    check(matches(PageMatcher(index)))

fun ViewInteraction.checkIsRecyclerSize(size: Int): ViewInteraction =
    check(matches(RecyclerViewAdapterSizeMatcher(size)))

fun ViewInteraction.checkIsListSize(size: Int): ViewInteraction =
    check(matches(ListViewViewAdapterSizeMatcher(size)))

fun ViewInteraction.checkIsNavigationViewItemChecked(id: Int): ViewInteraction =
    check(matches(NavigationItemMatcher(id)))

fun ViewInteraction.checkIsProgressBarProgress(number: Int): ViewInteraction =
    check(matches(ProgressMatcher(number)))

fun ViewInteraction.checkIsRatingBarRating(number: Float): ViewInteraction =
    check(matches(RatingBarMatcher(number)))

fun ViewInteraction.checkIsTextInputLayoutHintEnabled(): ViewInteraction =
    check(matches(TextInputLayoutHintEnabledMatcher(true)))

fun ViewInteraction.checkIsTextInputLayoutHintDisabled(): ViewInteraction =
    check(matches(TextInputLayoutHintEnabledMatcher(false)))

fun ViewInteraction.checkIsTextInputLayoutErrorEnabled(): ViewInteraction =
    check(matches(TextInputLayoutErrorEnabledMatcher(true)))

fun ViewInteraction.checkIsTextInputLayoutErrorDisabled(): ViewInteraction =
    check(matches(TextInputLayoutErrorEnabledMatcher(false)))

fun ViewInteraction.checkIsTextInputLayoutCounterEnabled(): ViewInteraction =
    check(matches(TextInputLayoutCounterEnabledMatcher(true)))

fun ViewInteraction.checkIsTextInputLayoutCounterDisabled(): ViewInteraction =
    check(matches(TextInputLayoutCounterEnabledMatcher(false)))

fun ViewInteraction.checkIsImageViewDrawable(@DrawableRes resId: Int, toBitmap: ((drawable: Drawable) -> Bitmap)? = null): ViewInteraction =
    check(matches(DrawableMatcher(resId = resId, toBitmap = toBitmap)))

fun ViewInteraction.checkIsImageViewDrawable(
    drawable: Drawable,
    toBitmap: ((drawable: Drawable) -> Bitmap)? = null
): ViewInteraction = check(matches(DrawableMatcher(drawable = drawable, toBitmap = toBitmap)))

//onDataClick
fun Int.clickItemOnData(): ViewInteraction =
    Espresso.onData(Matchers.anything()).atPosition(this).perform(click())

fun ViewInteraction.recyclerAdapterSize(): Int {
    var size = 0

    perform(object : ViewAction {
        override fun getDescription() = "Get RecyclerView adapter size"

        override fun getConstraints() =
            Matchers.allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is RecyclerView) size = view.adapter?.itemCount!!
        }
    })

    return size
}

fun ViewInteraction.performScrollScrollViewToStart() {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to start"

        override fun getConstraints() =
            Matchers.allOf(isAssignableFrom(ScrollView::class.java), isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) view.fullScroll(View.FOCUS_UP)
        }
    })
}

fun ViewInteraction.performScrollScrollViewToEnd() {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to end"

        override fun getConstraints() =
            Matchers.allOf(isAssignableFrom(ScrollView::class.java), isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) view.fullScroll(View.FOCUS_DOWN)
        }
    })
}

fun ViewInteraction.performScrollScrollViewTo(position: Int) {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to $position Y position"

        override fun getConstraints() =
            Matchers.allOf(isAssignableFrom(ScrollView::class.java), isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) view.scrollTo(0, position)
        }
    })
}

fun ViewInteraction.scrollViewSize(): Int {
    var size = 0

    perform(object : ViewAction {
        override fun getDescription() = "Get AdapterView adapter size"

        override fun getConstraints() =
            Matchers.allOf(isAssignableFrom(AdapterView::class.java), isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is AdapterView<*>) size = view.count
        }
    })

    return size
}

fun ViewInteraction.checkHasAnyTag(vararg tags: String) {
    val matchers = ArrayList<Matcher<Any>>(tags.size)

    tags.forEach {
        matchers.add(Matchers.`is`(it))
    }
    check(matches(withTagValue(Matchers.anyOf(matchers))))
}

fun ViewInteraction.checkIsTabLayoutTabSelected(index: Int) {
    check { view, notFoundException ->
        if (view is TabLayout) {
            if (view.selectedTabPosition != index) {
                throw AssertionError(
                    "Expected selected item index is $index," +
                            " but actual is ${view.selectedTabPosition}"
                )
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutHint(hint: String) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (hint != view.hint) {
                throw AssertionError(
                    "Expected hint is $hint," +
                            " but actual is ${view.hint}"
                )
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutError(error: String) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (error != view.error) {
                throw AssertionError(
                    "Expected error is $error," +
                            " but actual is ${view.error}"
                )
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutCounterMaxLength(length: Int) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (length != view.counterMaxLength) {
                throw AssertionError(
                    "Expected counter max length is $length," +
                            " but actual is ${view.counterMaxLength}"
                )
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun Int.checkViewAssertion(viewAssertion: ViewAssertion) = matchView().check(viewAssertion)

fun Int.checkIsDisplayed() = matchView().checkIsDisplayed()

fun Int.checkFromStringIsDisplayed() = matchViewFromText().checkIsDisplayed()

fun Int.checkIsNotDisplayed() = matchView().checkIsNotDisplayed()

fun Int.checkFromStringIsNotDisplayed() = matchViewFromText().checkIsNotDisplayed()

fun Int.checkIsCompletelyDisplayed() = matchView().checkIsCompletelyDisplayed()

fun Int.checkIsNotCompletelyDisplayed() = matchView().checkIsNotCompletelyDisplayed()

fun Int.checkIsVisible() = matchView().checkIsVisible()

fun Int.checkIsInvisible() = matchView().checkIsInvisible()

fun Int.checkIsGone() = matchView().checkIsGone()

fun Int.checkIsSelected() = matchView().checkIsSelected()

fun Int.checkIsNotSelected() = matchView().checkIsNotSelected()

fun Int.checkIsFocused() = matchView().checkIsFocused()

fun Int.checkIsNotFocused() = matchView().checkIsNotFocused()

fun Int.checkIsFocusable() = matchView().checkIsFocusable()

fun Int.checkIsNotFocusable() = matchView().checkIsNotFocusable()

fun Int.checkIsClickable() = matchView().checkIsClickable()

fun Int.checkIsNotClickable() = matchView().checkIsNotClickable()

fun Int.checkIsEnabled() = matchView().checkIsEnabled()

fun Int.checkIsDisabled() = matchView().checkIsDisabled()

fun Int.checkHasTag(tag: String) = matchView().checkHasTag(tag)

fun Int.checkHasAnyTag(vararg tags: String) = matchView().checkHasAnyTag(*tags)

fun Int.checkHasHint(@StringRes hintId: Int) = matchView().checkHasHint(hintId)

fun Int.checkHasHint(hint: String) = matchView().checkHasHint(hint)

fun Int.checkDoesNotExist() = matchView().checkDoesNotExist()

fun Int.checkDoesNotExistFromString() = matchViewFromText().checkDoesNotExist()

fun Int.checkHasBackgroundColor(@ColorRes resId: Int) = matchView().checkHasBackgroundColor(resId)

fun Int.checkHasBackgroundColor(colorCode: String) = matchView().checkHasBackgroundColor(colorCode)

fun Int.checkHasEmptyText() = matchView().checkHasEmptyText()

fun Int.checkHasAnyText() = matchView().checkHasAnyText()

fun Int.checkHasText(text: String) = matchView().checkHasText(text)

fun Int.checkHasText(@StringRes resId: Int) = matchView().checkHasText(resId)

fun Int.checkHasText(matcher: Matcher<String>) = matchView().checkHasText(matcher)

fun Int.checkHasTextColor(@ColorRes resId: Int) = matchView().checkHasTextColor(resId)

fun Int.checkHasNoText(text: String) = matchView().checkHasNoText(text)

fun Int.checkHasNoText(@StringRes resId: Int) = matchView().checkHasNoText(resId)

fun Int.checkHasContentDescription(text: String) = matchView().checkHasContentDescription(text)

fun Int.checkContainsText(text: String) = matchView().checkContainsText(text)

fun Int.checkStartsWithText(text: String) = matchView().checkStartsWithText(text)

fun Int.checkIsChecked() = matchView().checkIsChecked()

fun Int.checkIsNotChecked() = matchView().checkIsNotChecked()

fun Int.checkIsViewPagerAtPage(index: Int) = matchView().checkIsViewPagerAtPage(index)

fun Int.checkIsRecyclerSize(size: Int) = matchView().checkIsRecyclerSize(size)

fun Int.checkIsListSize(size: Int) = matchView().checkIsListSize(size)

fun Int.checkIsNavigationViewItemChecked(id: Int) = matchView().checkIsNavigationViewItemChecked(id)

fun Int.checkIsProgressBarProgress(number: Int) = matchView().checkIsProgressBarProgress(number)

fun Int.checkIsRatingBarRating(number: Float) = matchView().checkIsRatingBarRating(number)

fun Int.checkIsTabLayoutTabSelected(index: Int) = matchView().checkIsTabLayoutTabSelected(index)

fun Int.checkIsTextInputLayoutHint(hint: String) = matchView().checkIsTextInputLayoutHint(hint)

fun Int.checkIsTextInputLayoutHintEnabled() = matchView().checkIsTextInputLayoutHintEnabled()

fun Int.checkIsTextInputLayoutHintDisabled() = matchView().checkIsTextInputLayoutHintDisabled()

fun Int.checkIsTextInputLayoutError(error: String) = matchView().checkIsTextInputLayoutError(error)

fun Int.checkIsTextInputLayoutErrorEnabled() = matchView().checkIsTextInputLayoutErrorEnabled()

fun Int.checkIsTextInputLayoutErrorDisabled() = matchView().checkIsTextInputLayoutErrorDisabled()

fun Int.checkIsTextInputLayoutCounterMaxLength(length: Int) =
    matchView().checkIsTextInputLayoutCounterMaxLength(length)

fun Int.checkIsTextInputLayoutCounterEnabled() = matchView().checkIsTextInputLayoutCounterEnabled()

fun Int.checkIsTextInputLayoutCounterDisabled() =
    matchView().checkIsTextInputLayoutCounterDisabled()

fun Int.checkIsImageViewDrawable(@DrawableRes resId: Int, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) =
    matchView().checkIsImageViewDrawable(resId, toBitmap)

fun Int.checkIsImageViewDrawable(
    drawable: Drawable,
    toBitmap: ((drawable: Drawable) -> Bitmap)? = null
) = matchView().checkIsImageViewDrawable(drawable, toBitmap)

inline fun <reified T : View> Int.checkHasChild() = matchView().checkHasChild<T>()

inline fun <reified T : View> Int.checkIsAssignableFrom() = matchView().checkIsAssignableFrom<T>()

// Wait for UI thread
private typealias InvokeOnComplete = () -> Unit


fun <T : Activity> ActivityTestRule<T>.waitOnMainThread(
    duration: Long = 15L,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    executionBlock: (InvokeOnComplete) -> Unit
) {
    val latch = CountDownLatch(1)
    val completionCallback = { latch.countDown() }
    runOnUiThread { executionBlock(completionCallback) }
    MatcherAssert.assertThat(
        "Waiting timed out, callback was never invoked.",
        latch.await(duration, timeUnit),
        CoreMatchers.`is`(true)
    )

}

fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int
): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}

fun ViewInteraction.isVisible(): Boolean {
    try {
        check(matches(isDisplayed()))
        Log.e("VIEW VISIBLE", "TRUE")
        return true
    } catch (e: NoMatchingViewException) {
        Log.e("VIEW VISIBLE", "FALSE")
        Log.e("VIEW VISIBLE", e.viewMatcherDescription)
        return false
    }
}