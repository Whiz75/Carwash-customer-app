package com.example.findcarwashapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.findcarwashapp.R;
import com.example.findcarwashapp.dialogs.ConfirmLocationDialogFragment;
import com.example.findcarwashapp.dialogs.EnterLocationDialogFragment;
import com.example.findcarwashapp.dialogs.LoadingDialogFragment;
import com.example.findcarwashapp.dialogs.ProfileDialogFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextInputLayout auto_pin_location;
    private MaterialButton confirm_location_btn, enter_location_btn;
    private CardView bottom_sheet_cv;

    private final int LOCATION_PERMISSION_CODE = 1;
    public static final int REQUEST_CHECK_CODE = 1;

    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //call methods
        init();
        navigationDrawer();
        GPSControl();
        getCurrentLocation();
        bottomSheetAnimation();
        //closeConfirmationDialog();
        openEnterLocationDialog();

        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapActivity.this, "You have already granted this permission!",
                    Toast.LENGTH_SHORT).show();

            SupportMapFragment supportMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(this);

        } else {
            requestLocationPermission();
        }
    }


    private void init() {

        toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        auto_pin_location = findViewById(R.id.auto_location_address);
        confirm_location_btn = findViewById(R.id.confirm_user_location);
        enter_location_btn = findViewById(R.id.enter_location_btn);

        bottom_sheet_cv = findViewById(R.id.bottomsheet_cv);
    }

    private void requestLocationPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(MapActivity.this,
                            new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }


    private void GPSControl()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> results = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        results.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                response.getLocationSettingsStates();

                Toast.makeText(getApplicationContext(), "GPS location is turned On", Toast.LENGTH_LONG).show();

            } catch (ApiException e) {
                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(MapActivity.this, REQUEST_CHECK_CODE);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
        results.addOnFailureListener(e ->
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_CODE)
        {
            switch (resultCode)
            {
                case Activity.RESULT_OK:
                    Toast.makeText(getApplicationContext(), "GPS is already On", Toast.LENGTH_LONG).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getApplicationContext(), "GPS is required for the service", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private  void getCurrentLocation()
    {
        try {/*--------cooordinates for current location--------*/
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10, (LocationListener) MapActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void navigationDrawer()
    {
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId())
            {
                case R.id.nav_home:
                    Toast.makeText(getApplicationContext(), "you clicked home", Toast.LENGTH_LONG).show();
                    break;

                case R.id.nav_profile:
                    //call profile frag
                    ProfileDialogFragment dialogFragment = new ProfileDialogFragment();
                    dialogFragment.show(getSupportFragmentManager().beginTransaction(), "PROFILE FRAG");
                    break;

                case R.id.nav_rate:
                    Toast.makeText(getApplicationContext(), "you clicked rate", Toast.LENGTH_LONG).show();
                    break;
                case R.id.nav_share:
                    Toast.makeText(getApplicationContext(), "you clicked share", Toast.LENGTH_LONG).show();
                    break;
                case R.id.nav_logout:
                    Toast.makeText(getApplicationContext(), "you clicked logout", Toast.LENGTH_LONG).show();
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                    builder.setTitle("LOGOUT");
                    builder.setMessage("Are you sure you want to logout ?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment("Logging user out...");
                        loadingDialogFragment.show(getSupportFragmentManager().beginTransaction(), "LOGOUT");

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),LoginSignupActivity.class));
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                    builder.show();

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }

    private void closeConfirmationDialog(String location) {

        confirm_location_btn.setOnClickListener(v -> {

            ConfirmLocationDialogFragment dialogFragment = new ConfirmLocationDialogFragment(location);
            dialogFragment.show(getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out), "CONFIRM LOCATION");
        });
    }

    private void openEnterLocationDialog()
    {
        enter_location_btn.setOnClickListener(v -> {

            EnterLocationDialogFragment dialogFragment =  new EnterLocationDialogFragment();
            dialogFragment.show(getSupportFragmentManager().beginTransaction(), "ENTER LOCATION");

        });
    }

    private void bottomSheetAnimation()
    {
        float v = 0;
        int y = 600;

        bottom_sheet_cv.setTranslationY(y);
        bottom_sheet_cv.setAlpha(v);
        bottom_sheet_cv.animate().translationY(0).alpha(1).setDuration(3000).setStartDelay(1000).start();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mGoogleMap = googleMap;

        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        mGoogleMap.setOnMyLocationChangeListener(location -> {
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(latlng);
            markerOptions.title("My Location");
            mGoogleMap.clear();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 17);
            mGoogleMap.animateCamera(cameraUpdate);
            mGoogleMap.addMarker(markerOptions);

            try {
                Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                final String address = addresses.get(0).getAddressLine(0);

                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

                //set current location to edit text
                Objects.requireNonNull(auto_pin_location.getEditText()).setText(address);
                closeConfirmationDialog(address);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}