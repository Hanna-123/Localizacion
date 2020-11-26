package com.example.localizacionejemplo;

import android.Manifest;
import android.app.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.BreakIterator;
import java.util.List;
import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends Activity {
    private LocationManager locManager;
    private LocationListener locListener;
    TextView jtv1, jtv2, jtv3, jtv4;
    Button jbn1, jbn2;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        jtv1 = (TextView) findViewById(R.id.xtv1);
        jtv2 = (TextView) findViewById(R.id.xtv2);
        jtv3 = (TextView) findViewById(R.id.xtv3);
        jtv4 = (TextView) findViewById(R.id.xtv4);
        jbn1 = (Button) findViewById(R.id.xbn1);
        jbn2 = (Button) findViewById(R.id.xbn2);
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locManager != null;List<String> listaProviders = locManager.getAllProviders();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2000);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2000);
        }
        jbn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rastreoGPS();
            }
        });

        jbn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locManager.removeUpdates(locListener);
            }
        });

    }
    private void rastreoGPS() {
        /*Se asigna a la clase LocationManager el servicio a nivel de sistema a partir del nombre.*/
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        /*Se declara y asigna a la clase Location la última posición conocida proporcionada por el proveedor.*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2000);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2000);
        }
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);

        //Se define la interfaz LocationListener, que deberá implementarse con los siguientes métodos.
        locListener = new LocationListener() {
            //Método que será llamado cuando cambie la localización.
            @Override
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            //Método que será llamado cuando se produzcan cambios en el estado del proveedor.
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                jtv4.setText("Estado del proveedor: " + status);
            }

            //Método que será llamado cuando el proveedor esté habilitado para el usuario.
            @Override
            public void onProviderEnabled(String provider) {
                jtv4.setText("Proveedor en ON");
            }

            //Método que será llamado cuando el proveedor esté deshabilitado para el usuario.
            @Override
            public void onProviderDisabled(String provider) {
                jtv4.setText("Proveedor en OFF");
            }
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locListener);

    }
    private String[] mostrarPosicion(Location loc) {
        String[] datos;
        if(loc != null)
        {
            jtv1.setText("Latitud: "+String.valueOf(loc.getLatitude()));
            jtv2.setText("Longitud: "+ String.valueOf(loc.getLongitude()));
            jtv3.setText("Presición: "+String.valueOf(loc.getAccuracy()));
            datos = new String[]{String.valueOf(loc.getLongitude()),String.valueOf(loc.getLatitude())};
        }
        else
        {
            datos = new String[]{String.valueOf(40.4167754), String.valueOf(-3.7037901999999576),"Posición por defecto"};
            jtv1.setText("Latitud: "+String.valueOf(40.4167754));
            jtv2.setText("Longitud: "+String.valueOf(-3.7037901999999576));
            jtv3.setText("Presición: "+String.valueOf(1.0));
        }
        return datos;
    }


}