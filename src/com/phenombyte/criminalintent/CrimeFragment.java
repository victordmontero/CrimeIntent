package com.phenombyte.criminalintent;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID = "com.phenombyte.criminalintent.crimeId";
	private Crime mCrime;
	private EditText mEditText;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;

	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		CrimeFragment f = new CrimeFragment();
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, container, false);

		mEditText = (EditText) v.findViewById(R.id.crime_title);
		mEditText.setText(mCrime.getTitle());
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mCrime.setTitle(arg0.toString());

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean isChecked) {
						mCrime.setSolved(isChecked);
					}
				});

		mDateButton = (Button) v.findViewById(R.id.crime_date);
		mDateButton.setText(mCrime.getDate().toString());
		mDateButton.setEnabled(false);

		return v;
	}
}
