package com.timecapsule.app.geofence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.timecapsule.app.GoToMedia;

public class TimePlacePicker extends AppCompatActivity {

    private Button get_place;
    int PLACE_PICKER_REQUEST = 1;
    String mediaType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaType = getIntent().getExtras().getString("key");

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(TimePlacePicker.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesRepairableException e){
                    e.printStackTrace();
                }catch (GooglePlayServicesNotAvailableException e){
                    Log.d(this.getClass().getSimpleName(), "onClick: ");
                    e.printStackTrace();
                }



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        Log.d("TIMEPLACE", "onActivityResult: ");
        Toast.makeText(this, "place selected"+resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode==PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                LatLng locationLatLng = place.getLatLng();
                String address = (String) place.getAddress();

                double locationLat = locationLatLng.latitude;
                double locationLong = locationLatLng.longitude;

                Intent gotoMediaIntent = new Intent(getApplicationContext(), GoToMedia.class);
                gotoMediaIntent.putExtra("keyMediaType", mediaType);
                gotoMediaIntent.putExtra("keyLocationLat", locationLat);
                gotoMediaIntent.putExtra("keyLocationLong", locationLong);
                gotoMediaIntent.putExtra("keyAddress", address);
                startActivity(gotoMediaIntent);

            }
        }
    }
}
