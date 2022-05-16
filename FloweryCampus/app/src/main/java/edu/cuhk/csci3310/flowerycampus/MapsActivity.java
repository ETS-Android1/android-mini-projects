package edu.cuhk.csci3310.flowerycampus;

// TODO: Include your personal particular here
// Name: Yeung Man Wai
// SID: 1155126854

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Stack;

import android.util.Log;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "FloweryCampus";
    private GoogleMap mMap;
    private Marker selectedMarker;
    static LinkedList<Spot> spotList = new LinkedList<>();
    Stack<Marker> markerStack = new Stack<>();

    // TODO: Define other attributes as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // By default map fragment is added statically via the SupportMapFragment obtained
        // and get notified when the map is ready to be used.
        //
        // TODO: Modify the following code to include mapFragment and other fragments dynamically
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapFragment).addToBackStack(null).commit();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // TODO: Modify the following code to
        // 1. read flowery locations from a CSV,
        readCSV();
        // 2. set up markers, view bounds and zoom
        for(Spot spot : spotList){
            LatLng newSpot = new LatLng(spot.getLatitude(),	spot.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(newSpot).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title(spot.getFlower_name_loc());
            mMap.addMarker(marker);
        }

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                setDefaultView();
            }
        });

        // 3. add listeners to handle different map clicking events
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                changePrevMarkerColor();
                resetView();
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerStack.add(selectedMarker);
                changePrevMarkerColor();
                selectedMarker = marker;
                showMarkerInfo();
                return true;
            }
        });
        // 4. include extra data structure to handle non-system "Back-pressing" states

    }

    /**
     * Include customized handling on pressing Back button
     * This callback is triggered when the Back is pressed.
     * This is where we can include extra BackStack handling not done by system by default
     * e.g. markers status etc.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: include addition BackStack handling, e.g. for markers, here
        if(markerStack.isEmpty()){
            this.finishAffinity();
        } else {
            try {
                changePrevMarkerColor();
                Marker prevMarker =  markerStack.pop();
                if(prevMarker == null){
                    resetView();
                } else {
                    selectedMarker = prevMarker;
                    showMarkerInfo();
                }
            } catch (Exception e) {
                resetView();
            }
        }
    }

    // TODO: Add more utility methods, e.g. readCSV as needed
    private void readCSV() {
        InputStream is = getResources().openRawResource(R.raw.cu_flowers);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Spot spot = new Spot();
                spot.setFilename(tokens[0].length() > 0 ? tokens[0] : "");
                spot.setFlower_name_loc(tokens[1].length() > 0 ? tokens[1] : "");
                spot.setLatitude(tokens[2].length() > 0 ? Double.parseDouble(tokens[2]) : 0);
                spot.setLongitude(tokens[3].length() > 0 ? Double.parseDouble(tokens[3]) : 0);
                spotList.add(spot);
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Error reading csv file at line " + line, e);
            e.printStackTrace();
        }
    }

    public void displayFragment(String image) {
        PhotoFragment photoFragment = PhotoFragment.newInstance(image);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, photoFragment).addToBackStack(null).commit();
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (Fragment fragment: fragmentManager.getFragments()){
            if(fragment.getId() != R.id.map) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
        }
    }

    public void showMarkerInfo() {
        selectedMarker.showInfoWindow();
        selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        String index = String.valueOf(selectedMarker.getId().charAt(selectedMarker.getId().length() - 1));
        displayFragment(index);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedMarker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }

    public void changePrevMarkerColor() {
        if(selectedMarker != null) {
            selectedMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        }
    }

    public void resetView() {
        if(selectedMarker != null){
            selectedMarker.hideInfoWindow();
            selectedMarker = null;
        }
        setDefaultView();
        markerStack.clear();
        closeFragment();
    }

    public void setDefaultView(){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Spot spot : spotList) {
            LatLng marker = new LatLng(spot.getLatitude(),spot.getLongitude());
            builder.include(marker);
        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }
}
