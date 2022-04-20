package com.ominfo.crm_solution.ui.visit_report.activity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.common.BackgroundLocationUpdateService;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat, lng;
    SupportMapFragment mapFragment;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    private MyReceiver myReceiver;
    boolean statusLoc = false;
    String markerTitle = "";
    LatLng addLatLong;
    Context mContext;
    @BindView(R.id.dragg_result)
    AppCompatTextView mTextResult;
    @BindView(R.id.dragg_result_title)
    AppCompatTextView mTextResultTitle;
    @BindView(R.id.addLocationButton)
    AppCompatButton btnAddLocation;
    @BindView(R.id.imgCurrentLoc)
    AppCompatImageView imgLocation;
    @BindView(R.id.idSearchView)
    SearchView searchView;
    int zoom = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        myReceiver = new MyReceiver();
        mContext = this;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isGPSEnabled(mContext)) {
            OnGPS();
        } else {
            getLocation();
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        // Obtain the SupportMapFragment and get notified
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchBar();
        // at last we calling our map fragment to update.
        mapFragment.getMapAsync(this);
    }

    //check if gps is enabled
    public boolean isGPSEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gps_enabled;
    }


    private void searchBar(){
        // adding on query listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;

                // checking if the entered location is null or not.
                if (location != null || location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(AddLocationActivity.this);
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        // on below line we are getting the location
                        // from our list a first position.
                        Address address = addressList.get(0);

                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            mTextResult.setText(locality + "  " + country);
                        // on below line we are adding marker to that position.
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location).draggable(true)
                                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker)).draggable(true));
                        markerTitle = new MarkerOptions().position(latLng).title(location).getTitle();
                        mTextResultTitle.setText(markerTitle);
                        addLatLong = latLng;
                        // below line is to animate camera to that position.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                        //marker.setDraggable(true);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //perform click actions
    @OnClick({R.id.addLocationButton,R.id.imgCurrentLoc})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.addLocationButton:
                //LogUtil.printToastMSG(mContext,"clickk");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", mTextResultTitle.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                LatLng latLng = new LatLng(addLatLong.latitude,addLatLong.longitude);
                String latLongSharePref = AppUtils.getJsonToString(latLng);
                SharedPref.getInstance(mContext).write(SharedPrefKey.ENTERED_VISIT_LAT, String.valueOf(addLatLong.latitude));
                SharedPref.getInstance(mContext).write(SharedPrefKey.ENTERED_VISIT_LNG, String.valueOf(addLatLong.longitude));
                SharedPref.getInstance(mContext).write(SharedPrefKey.LOCATION_ENTERED_TXT, mTextResult.getText().toString());
                break;
            case R.id.imgCurrentLoc:
                setCurrent();
                break;
        }
    }

    /**
     * Receiver for broadcasts sent by {@link }.
     */

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(BackgroundLocationUpdateService.EXTRA_LOCATION);
            if (location != null) {
                if (!statusLoc) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    setCurrent();
                    statusLoc = true;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundLocationUpdateService.ACTION_BROADCAST));
    }


    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AddLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AddLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }

            }
            final Handler handler = new Handler();
            Location finalBestLocation = bestLocation;
            handler.postDelayed(new Runnable() {
                public void run() {
                    Location locationGPS = finalBestLocation;
                    if (locationGPS != null) {
                        lat = locationGPS.getLatitude();
                        lng = locationGPS.getLongitude();
                        latitude = String.valueOf(lat);
                        longitude = String.valueOf(lng);
                        setCurrent();
                        //showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                    } else {
                        //Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);

        }
    }

    //starting foreground service and registering broadcast for lat long
    private void startLocationService() {
        //startService(new Intent(this, BackgroundLocationUpdateService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundLocationUpdateService.ACTION_BROADCAST));
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationService();

    }


    //adding customize marker
    public BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, 150, 160);

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(150, 160, Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(AddLocationActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            mTextResult.setText(locality + "  " + country);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);
        //mMap.setMapStyle(GoogleMapOptions.);
        // map is a GoogleMap object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mMap.getUiSettings().setMapToolbarEnabled(false);

        // for Zoom Button Enable on Google Map
        //mMap.getUiSettings().setZoomControlsEnabled(true);

        //for Location  Button enable on Google Map
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        //mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                // where we will add our locations latitude and longitude.
                // on below line we are adding marker to that position.
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(AddLocationActivity.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String locality = addresses.get(0).getAddressLine(0);
                    String country = addresses.get(0).getCountryName();
                    if (!locality.isEmpty() && !country.isEmpty())
                        mTextResult.setText(locality + "  " + country);
                    // on below line we are adding marker to that position.
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getFeatureName()).draggable(true)
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker)).draggable(true));
                    markerTitle = new MarkerOptions().position(latLng).title(addresses.get(0).getFeatureName()).getTitle();
                    mTextResultTitle.setText(markerTitle);
                    addLatLong = latLng;
                    // below line is to animate camera to that position.
                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        setCurrent();

    }

    private void setCurrent() {
        LatLng latLng = new LatLng(lat, lng);
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(AddLocationActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null && addresses.size() > 0) {
                String locality = addresses.get(0).getAddressLine(0);
                String country = addresses.get(0).getCountryName();
                if (!locality.isEmpty() && !country.isEmpty())
                    mTextResult.setText(locality + "  " + country);
                // on below line we are adding marker to that position.
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getFeatureName()).draggable(true)
                        .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_marker)).draggable(true));
                markerTitle = new MarkerOptions().position(latLng).title(addresses.get(0).getFeatureName()).getTitle();
                mTextResultTitle.setText(markerTitle);
                addLatLong = latLng;
                // below line is to animate camera to that position.
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
