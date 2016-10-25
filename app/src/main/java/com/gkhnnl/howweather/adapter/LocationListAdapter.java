package com.gkhnnl.howweather.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.listener.DialogListener;
import com.gkhnnl.howweather.listener.LocationRemoveListener;
import com.gkhnnl.howweather.util.DialogUtil;
import com.gkhnnl.howweather.util.UserPref;
import com.gkhnnl.howweather.wrapper.CityEntity;

import java.util.List;

/**
 * Created by gkhnnl on 23/10/2016.
 */

public class LocationListAdapter extends BaseAdapter {

    private final List<CityEntity> cities;
    private final LocationRemoveListener listener;
    private final Activity activity;

    public LocationListAdapter(List<CityEntity> cities, LocationRemoveListener listener, Activity activity) {
        this.cities = cities;
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_list_location, parent, false);

        TextView location = (TextView) convertView.findViewById(R.id.txt_loca);
        location.setText(cities.get(position).getName());

        LinearLayout deleteButton = (LinearLayout) convertView.findViewById(R.id.delete_city);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CityEntity city = cities.get(position);
                if (city != null) {
                    showAlert(city);
                }
            }
        });

        return convertView;
    }

    private void showAlert(final CityEntity city) {

        DialogUtil.showDialog(activity, activity.getString(R.string.warning), activity.getString(R.string.deleteQuestion),
                activity.getString(R.string.yes), activity.getString(R.string.no), new DialogListener() {
                    @Override
                    public void onPositive() {
                        UserPref.deleteLocation(activity, String.valueOf(city.getId()));
                        cities.remove(city);
                        listener.onClick();
                        notifyDataSetChanged();
                    }
                });
    }
}
