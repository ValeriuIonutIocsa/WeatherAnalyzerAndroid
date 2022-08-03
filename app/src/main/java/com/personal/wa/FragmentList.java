package com.personal.wa;

import java.util.List;

import com.personal.weather.cities.data.City;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentList extends Fragment {

	private final List<City> cityList;

	public FragmentList(
			final List<City> cityList) {

		this.cityList = cityList;
	}

	@Override
	public View onCreateView(
			final LayoutInflater inflater,
			final ViewGroup container,
			final Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	@Override
	public void onViewCreated(
			@NonNull final View view,
			final Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		final ListView listView = view.findViewById(R.id.list_view);

		final ArrayAdapterCity adapter = new ArrayAdapterCity(
				view.getContext(), R.layout.list_row_layout, cityList);
		listView.setAdapter(adapter);
	}
}
