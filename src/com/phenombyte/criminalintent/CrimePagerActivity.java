package com.phenombyte.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class CrimePagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		mCrimes = CrimeLab.get(this).getCrimes();

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCrimes.size();
			}

			@Override
			public Fragment getItem(int index) {
				Crime c = mCrimes.get(index);
				return CrimeFragment.newInstance(c.getId());
			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				Crime c = mCrimes.get(arg0);
				if (c.getTitle() != null) {
					setTitle(c.getTitle());
				}

			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {}
		});

		UUID crimeId = (UUID) getIntent().getSerializableExtra(
				CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}
}
