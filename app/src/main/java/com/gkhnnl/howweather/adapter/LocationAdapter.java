package com.gkhnnl.howweather.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.util.ApiUtil;
import com.gkhnnl.howweather.util.ProgressDialogUtil;
import com.gkhnnl.howweather.util.UserPref;
import com.gkhnnl.howweather.wrapper.CityEntity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gkhnnl on 06/09/16.
 */
public class LocationAdapter extends BaseAdapter {

    private final List<CityEntity> cities;
    private final Activity activity;
    private final Context context;

    public LocationAdapter(List<CityEntity> cities, Activity activity, Context context) {
        this.cities = cities;
        this.activity = activity;
        this.context = context;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_list_search, parent, false);

        TextView location = (TextView) convertView.findViewById(R.id.txt_loca);
        location.setText(cities.get(position).getName() + ", " + cities.get(position).getCountry());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CityEntity city = cities.get(position);
                if (city.getId() != 0) {
                    ProgressDialogUtil.ShowDialog(activity, activity.getString(R.string.waiting), false);
                    String cityId = String.valueOf(city.getId());
                    Map<String, String> location  = new LinkedHashMap<>();
                    location.put(cityId, city.getName());
                    ApiUtil.getWeather(location, activity, 1, false);
                    UserPref.setLocation(context.getApplicationContext(), cityId, city.getName());
                    UserPref.setBoolean(context, UserPref.KEY_ISADD, true);
                }
            }
        });

        return convertView;
    }

}
