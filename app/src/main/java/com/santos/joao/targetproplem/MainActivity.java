package com.santos.joao.targetproplem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static Tuple tupla;
    private MainActivity mLocationListener;
    private LocationManager mLocationManager;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tupla = new Tuple();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        mLocationListener = new MainActivity();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        setUpMap(googleMap);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    if (tupla.name != null){
                        Geocoder gc = new Geocoder(MainActivity.this.getBaseContext(), Locale.getDefault());
                        List<Address> fromLocation = gc.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        if (!fromLocation.isEmpty()) {
                            Address en = fromLocation.get(0);
                            googleMap.addMarker(new MarkerOptions().position(latLng).title(tupla.name).snippet(en.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(tupla.getColorAviso())));
                            tupla.name = Constants.NONE;
                        } else {
                            Log.d(MainActivity.class.getSimpleName(), "Sem local");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                // Remove the marker
                //marker.showInfoWindow();
                marker.remove();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_batida:
                tupla.name = Constants.BATIDA;
                return true;
            case R.id.action_buraco:
                tupla.name = Constants.BURACO;
                return true;
            case R.id.action_desvio:
                tupla.name = Constants.DESVIO;
                return true;
            case R.id.action_passeata:
                tupla.name = Constants.PASSEATA;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpMap(GoogleMap mMap) {
        LatLng state = new LatLng(-8.05388889, -34.88111111);
        //mMap.addMarker(new MarkerOptions().position(state).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(state));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
    }

    public void createItem(View vista) {
        String[] array = new String[]{Constants.BURACO, Constants.DESVIO, Constants.PASSEATA, Constants.BATIDA};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.arrayOptions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                switch (which) {
                    case 0:
                        tupla.name = Constants.BURACO;
                        break;
                    case 1:
                        tupla.name = Constants.DESVIO;
                        break;
                    case 2:
                        tupla.name = Constants.PASSEATA;
                        break;
                    case 3:
                        tupla.name = Constants.BATIDA;
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }
}
