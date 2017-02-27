package com.phenombyte.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class CrimeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager frm = getSupportFragmentManager();
		Fragment fragment = frm.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new CrimeFragment();
			frm.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
}
