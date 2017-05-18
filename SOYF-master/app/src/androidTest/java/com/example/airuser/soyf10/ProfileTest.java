package com.example.airuser.soyf10;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 5/17/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ProfileTest {
    @Rule
    public ActivityTestRule testRule = new ActivityTestRule(Login.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.airuser.soyf10", appContext.getPackageName());
        onView(withId(R.id.fbLogin))            // withId(R.id.my_view) is a ViewMatcher
                .perform(click());// click() is a ViewAction
        onView(withId(R.id.button))
                .perform(click());
        onView(withId(R.id.profilesButton))
                .perform(click());

        onView(withId(R.id.firstname))
                .perform(ViewActions.replaceText("First"));
        onView(withId(R.id.lastname))
                .perform(ViewActions.replaceText("Last"));
        ;

        intValidate(R.id.age);
        inputOutOfView(R.id.age, "23");

        intValidate(R.id.height);
        inputOutOfView(R.id.height, "123");

        intValidate(R.id.weight);
        inputOutOfView(R.id.weight, "200");

        onView(withId(R.id.bUploadImage))
                .perform(ViewActions.click());




    }

    private void inputOutOfView(int view, String input){
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());

        onView(withId(view))
                .perform(ViewActions.replaceText(input));

        onView(withId(android.R.id.content))
                .perform(ViewActions.swipeDown());

    }

    private void intValidate(int view) {
        inputOutOfView(view, "Invalid");

        onView(withId(R.id.bUploadImage))
                .perform(ViewActions.click());

        onView(withText("Invalid Input"))
                .inRoot(withDecorView(not(is(testRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }
}
