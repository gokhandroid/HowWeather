package com.gkhnnl.howweather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gkhnnl.howweather.adapter.LocationListAdapter;
import com.gkhnnl.howweather.listener.DialogDismissListener;
import com.gkhnnl.howweather.listener.DialogListener;
import com.gkhnnl.howweather.listener.LocationRemoveListener;
import com.gkhnnl.howweather.util.ApiUtil;
import com.gkhnnl.howweather.util.DialogUtil;
import com.gkhnnl.howweather.util.NavigationUtil;
import com.gkhnnl.howweather.util.ProgressDialogUtil;
import com.gkhnnl.howweather.util.UserPref;
import com.gkhnnl.howweather.wrapper.CityEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationListActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private boolean isRemoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        ListView locationList = (ListView) findViewById(R.id.list_locations);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.locations);

        Map<String, String> locations = UserPref.getLocation(this);
        List<CityEntity> cities = new ArrayList<>();
        for (Map.Entry<String, String> i : locations.entrySet()) {
            cities.add(new CityEntity(Integer.parseInt(i.getKey()), i.getValue()));
        }
        LocationListAdapter adapter = new LocationListAdapter(cities, new LocationRemoveListener() {
            @Override
            public void onClick() {
                isRemoved = true;
            }
        }, this);
        locationList.setAdapter(adapter);

        LinearLayout addLocation = (LinearLayout) findViewById(R.id.add_location);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocationEnabled()) {
                    openLocationListener();
                    ProgressDialogUtil.ShowDialog(LocationListActivity.this, getString(R.string.waitingLocation), true, new DialogDismissListener() {
                        @Override
                        public void OnDismiss() {
                            removeLocationListener();
                        }
                    });
                } else {
                    showAlert();
                }
            }
        });

        LinearLayout addCity = (LinearLayout) findViewById(R.id.add_city);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.goLocation(LocationListActivity.this);
            }
        });
    }

    private void openLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerGPS);
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private void showAlert() {

        DialogUtil.showDialog(this, getString(R.string.enableLocation), getString(R.string.locationWarning), getString(R.string.locationSetting), getString(R.string.cancel), new DialogListener() {
            @Override
            public void onPositive() {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            double longitudeGPS = location.getLongitude();
            double latitudeGPS = location.getLatitude();
            ApiUtil.getWeatherWithLocation(LocationListActivity.this, String.valueOf(latitudeGPS), String.valueOf(longitudeGPS));
            removeLocationListener();
            ProgressDialogUtil.Dismiss();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void removeLocationListener() {
        if (ActivityCompat.checkSelfPermission(LocationListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        locationManager.removeUpdates(locationListenerGPS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                removeLocationListener();
                if (isRemoved)
                    NavigationUtil.home(this);
                else
                    finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeLocationListener();
        if (isRemoved)
            NavigationUtil.home(this);
    }
}
