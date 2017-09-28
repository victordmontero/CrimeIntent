package com.phenombyte.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_fragment);

		FragmentManager frm = getSupportFragmentManager();
		Fragment fragment = frm.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = createFragment();
			frm.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
	
}
