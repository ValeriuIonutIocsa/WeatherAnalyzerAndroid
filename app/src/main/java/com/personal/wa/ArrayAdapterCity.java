package com.personal.wa;

import java.util.List;

import com.personal.weather.cities.data.City;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ArrayAdapterCity extends ArrayAdapter<City> {

	private final Context context;
	private final int resource;

	ArrayAdapterCity(
			Context context,
			int resource,
			List<City> cityList) {

		super(context, resource, cityList);

		this.context = context;
		this.resource = resource;
	}

	@Override
	public View getView(
			int position,
			View convertView,
			ViewGroup parent) {

		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(resource, parent, false);
			viewHolder.textViewCityName = convertView.findViewById(R.id.textViewCityName);
			viewHolder.textViewCurrentHighTemp = convertView.findViewById(R.id.textViewCurrentHighTemp);
			viewHolder.textViewCurrentLowTemp = convertView.findViewById(R.id.textViewCurrentLowTemp);
			viewHolder.textViewHistoricalHighTemp = convertView.findViewById(R.id.textViewHistoricalHighTemp);
			viewHolder.textViewHistoricalLowTemp = convertView.findViewById(R.id.textViewHistoricalLowTemp);
			viewHolder.textViewDiffHighTemp = convertView.findViewById(R.id.textViewDiffHighTemp);
			viewHolder.textViewDiffLowTemp = convertView.findViewById(R.id.textViewDiffLowTemp);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		City city = getItem(position);
		if (city != null) {

			String cityName = city.getCityName();
			viewHolder.textViewCityName.setText(cityName);
			String currentHighTempString = city.createCurrentHighTempString();
			viewHolder.textViewCurrentHighTemp.setText(currentHighTempString);
			String currentLowTempString = city.createCurrentLowTempString();
			viewHolder.textViewCurrentLowTemp.setText(currentLowTempString);
			String historicalHighTempString = city.createHistoricalHighTempString();
			viewHolder.textViewHistoricalHighTemp.setText(historicalHighTempString);
			String historicalLowTempString = city.createHistoricalLowTempString();
			viewHolder.textViewHistoricalLowTemp.setText(historicalLowTempString);
			String diffHighTempString = city.createDiffHighTempString();
			viewHolder.textViewDiffHighTemp.setText(diffHighTempString);
			String diffLowTempString = city.createDiffLowTempString();
			viewHolder.textViewDiffLowTemp.setText(diffLowTempString);
		}

		return convertView;
	}

	private static class ViewHolder {

		private TextView textViewCityName;
		private TextView textViewCurrentHighTemp;
		private TextView textViewCurrentLowTemp;
		private TextView textViewHistoricalHighTemp;
		private TextView textViewHistoricalLowTemp;
		private TextView textViewDiffHighTemp;
		private TextView textViewDiffLowTemp;
	}
}
