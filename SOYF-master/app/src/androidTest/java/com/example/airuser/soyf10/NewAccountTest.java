package com.example.airuser.soyf10;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 5/18/2017.
 */
@RunWith(AndroidJUnit4.class)
public class NewAccountTest {
    @Rule
    public ActivityTestRule testRule = new ActivityTestRule(Login.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.airuser.soyf10", appContext.getPackageName());
        onView(withId(R.id.tvRegisterLink))            // withId(R.id.my_view) is a ViewMatcher
                .perform(click());// click() is a ViewAction

        onView(withId(R.id.etName))            // withId(R.id.my_view) is a ViewMatcher
                .perform(ViewActions.replaceText("Test User"));

        onView(withId(R.id.etUsername))
                .perform(ViewActions.replaceText("UsernameTest"));

        onView(withId(R.id.etPassword))
                .perform(ViewActions.replaceText("password"));

        onView(withId(R.id.bRegister))
                .perform(ViewActions.click());

        onView(withId(R.id.login1))
                .perform(click());

        onView(withId(R.id.etUsername))
                .perform(ViewActions.replaceText("InvalidUser"));
        onView(withId(R.id.etPassword))
                .perform(ViewActions.replaceText("password"));
        onView(withId(R.id.bLogin))
                .perform(click());
        onView(withText("Retry"))
                .inRoot(withDecorView(not(is(testRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

        onView(withId(R.id.etUsername))
                .perform(ViewActions.replaceText("UsernameTest"));
        onView(withId(R.id.etPassword))
                .perform(ViewActions.replaceText("InvalidPassword"));
        onView(withId(R.id.bLogin))
                .perform(click());
        onView(withText("Retry"))
                .inRoot(withDecorView(not(is(testRule.getActivity().getWindow().getDecorView()))))
                .perform(click());

        onView(withId(R.id.etUsername))
                .perform(ViewActions.replaceText("UsernameTest"));

        onView(withId(R.id.etPassword))
                .perform(ViewActions.replaceText("password"));

        onView(withId(R.id.bLogin))
                .perform(click());

        onView(withText("Step On Your Friends"))
                .check(matches(isDisplayed()));




    }
}
