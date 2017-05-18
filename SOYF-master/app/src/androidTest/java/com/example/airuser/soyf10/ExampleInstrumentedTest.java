package com.example.airuser.soyf10;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule testRule = new ActivityTestRule(Login.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.airuser.soyf10", appContext.getPackageName());
                onView(withId(R.id.fbLogin))            // withId(R.id.my_view) is a ViewMatcher
                .perform(click());// click() is a ViewAction

               /* onView(withId(R.id.button))
                .perform(click());

                onView(withId(R.id.fbLogout))
                .perform(click());
                */
        onView(withText("LOG OUT"))
                .inRoot(withDecorView(not(is(testRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }
}
