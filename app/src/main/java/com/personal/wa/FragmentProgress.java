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
			final MainActivity mainActivity) {

		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(
			final LayoutInflater inflater,
			final ViewGroup container,
			final Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_progress, container, false);
	}

	@Override
	public void onViewCreated(
			@NonNull final View view,
			final Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		final WeakReference<MainActivity> wrMainActivity = new WeakReference<>(mainActivity);
		final ProgressBar progressBar = view.findViewById(R.id.progressBar);
		final TextView textView = view.findViewById(R.id.textView);
		final WeakReference<ProgressBar> wrProgressBar = new WeakReference<>(progressBar);
		final WeakReference<TextView> wrTextView = new WeakReference<>(textView);
		new AsyncTaskLoad(wrMainActivity, wrProgressBar, wrTextView).execute();
	}
}
