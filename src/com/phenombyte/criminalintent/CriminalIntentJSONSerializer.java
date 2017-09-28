package com.phenombyte.criminalintent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class CriminalIntentJSONSerializer {
	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context context, String filename) {
		super();
		mContext = context;
		mFilename = filename;
	}

	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {
		// Build an Array in JSON
		JSONArray array = new JSONArray();
		for (Crime c : crimes)
			array.put(c.toJSON());

		// Write the File to disk
		Writer writer = null;
		try {
			OutputStream out = null;
			File file = null;
			// If there is external storage then save it into it.
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File path = mContext.getExternalFilesDir(null);
				file = new File(path, mFilename);
				out = new FileOutputStream(file);
			} else
				out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);

			writer.write(array.toString());
		} catch (Exception e) {
			Log.d("Crime", e.getMessage());

		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		InputStream in = null;
		File file = null;
		try {

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File path = mContext.getExternalFilesDir(null);
				file = new File(path, mFilename);
				in = new FileInputStream(file);
			} else {
				// Open and read the file into a StringBuilder
				in = mContext.openFileInput(mFilename);
			}
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			// Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue();
			// Build the array of crimes from JSONObjects
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} finally {
			if (reader != null)
				reader.close();
		}
		return crimes;
	}

}
