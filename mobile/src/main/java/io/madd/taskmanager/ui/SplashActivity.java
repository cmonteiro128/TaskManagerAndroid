package io.madd.taskmanager.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.madd.taskmanager.R;
import io.madd.taskmanager.utils.PreferenceUtils;

public class SplashActivity extends ActionBarActivity implements
        SplashFragment.OnFragmentInteractionListener {

    private static final int NUM_PAGES = 3;
    protected SharedPreferences mPrefs;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @InjectView(R.id.splash_page_pager)
    ViewPager mViewPager;

    /**
     * The {@link CirclePageIndicator} that will indicate the current
     * page the {@link ViewPager} is on.
     */
    @InjectView(R.id.splash_page_indicator)
    CirclePageIndicator mViewPageIndicator;

    /**
     * The {@link TextView} that will display the skip function
     * to enable skipping the first startup splash sequence. this is
     * done by making this clickable by using
     * {@link View.OnClickListener}
     */
    @InjectView(R.id.splash_skip_button)
    TextView mSkipButton;

    /**
     * The {@link ImageView} that will display the next icon to send you
     * next page. this is done by making this clickable by using
     * {@link View.OnClickListener}
     */
    @InjectView(R.id.splash_next_button)
    ImageView mNextButton;

    /**
     * The {@link TextView} that will display the finish function
     * to finish the first startup splash sequence. this is
     * done by making this clickable by using
     * {@link View.OnClickListener}
     */
    @InjectView(R.id.splash_finish_button)
    TextView mFinishButton;

    boolean fromFinish = false;
    private int[] mPageColorArray;
    private Animation mFadeIn, mFadeOut;

    static int blendColors(int to, int from, float ratio) {
        final float inverseRation = 1f - ratio;
        final float r = Color.red(from) * ratio + Color.red(to) * inverseRation;
        final float g = Color.green(from) * ratio + Color.green(to) * inverseRation;
        final float b = Color.blue(from) * ratio + Color.blue(to) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get the saved preferences and check if user has already seen SplashPage
        mPrefs = getSharedPreferences(PreferenceUtils.TASK_MANAGER_PREFS, MODE_PRIVATE);
        if (mPrefs.getBoolean(PreferenceUtils.TASK_MANAGER_FIRST_START, false)) {
            switchToMainActivity();
        }

        ButterKnife.inject(this);

        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in);
        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.abc_fade_out);

        mPageColorArray = getResources().getIntArray(R.array.splash_page_colors);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up ViewPager with page limit.
        mViewPager.setOffscreenPageLimit(NUM_PAGES);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Bind the title indicator to the adapter
        mViewPageIndicator.setViewPager(mViewPager);
        mViewPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position >= mSectionsPagerAdapter.getCount() - 1) {
                    // Guard against ArrayIndexOutOfBoundsException
                    return;
                }
                // Blend the colors while swiping, then update the background
                mViewPager.setBackgroundColor(blendColors(mPageColorArray[position],
                        mPageColorArray[position + 1], positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptions();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainActivity();
            }
        });

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMainActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Setting a record that the splash page has either been skipped
     * or completely seen
     */
    @Override
    public void onFragmentInteraction(boolean setSeen) {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean(PreferenceUtils.TASK_MANAGER_FIRST_START,
                setSeen).apply();
    }

    private void switchToMainActivity() {
        onFragmentInteraction(true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void invalidateOptions() {
        if (mViewPager.getCurrentItem() == mSectionsPagerAdapter.getCount() - 1) {
            fromFinish = true;
            mFinishButton.setVisibility(View.VISIBLE);
            mFinishButton.startAnimation(mFadeIn);
            mFinishButton.setClickable(true);

            mSkipButton.startAnimation(mFadeOut);
            mNextButton.startAnimation(mFadeOut);
            mSkipButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);
            mSkipButton.setClickable(false);
            mNextButton.setClickable(false);
        } else {
            if (fromFinish) {
                mFinishButton.startAnimation(mFadeOut);
                mFinishButton.setVisibility(View.INVISIBLE);
                mFinishButton.setClickable(false);

                mSkipButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.VISIBLE);
                mNextButton.startAnimation(mFadeIn);
                mSkipButton.startAnimation(mFadeIn);
                mSkipButton.setClickable(true);
                mNextButton.setClickable(true);

                fromFinish = false;

            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SplashFragment (defined in SplashFragment).
            return SplashFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return NUM_PAGES;
        }
    }

}