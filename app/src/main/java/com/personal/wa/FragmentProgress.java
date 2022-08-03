package com.personal.wa;

import java.lang.ref.WeakReference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentProgress extends Fragment {

	private final MainActivity mainActivity;

	public FragmentProgress(
			MainActivity mainActivity) {

		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(
			LayoutInflater inflater,
			ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_progress, container, false);
	}

	public void onViewCreated(
			@NonNull View view,
			Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		WeakReference<MainActivity> wrMainActivity = new WeakReference<>(mainActivity);
		ProgressBar progressBar = view.findViewById(R.id.progressBar);
		TextView textView = view.findViewById(R.id.textView);
		WeakReference<ProgressBar> wrProgressBar = new WeakReference<>(progressBar);
		WeakReference<TextView> wrTextView = new WeakReference<>(textView);
		new AsyncTaskLoad(wrMainActivity, wrProgressBar, wrTextView).execute();
	}
}
