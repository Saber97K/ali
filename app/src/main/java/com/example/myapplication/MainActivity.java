package com.example.myapplication;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private ActivityMainBinding binding;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private boolean screenshot = false;
    private NavController navController;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Check the acceleration in each dimension
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Set the threshold for a shake (adjust as desired)
        float threshold = 15.0f;

        // If the acceleration in any dimension exceeds the threshold, consider it a shake
        if (Math.abs(x) > threshold || Math.abs(y) > threshold || Math.abs(z) > threshold) {
            // Run your desired method
//            Toast.makeText(getApplicationContext(), "Shake detected", Toast.LENGTH_SHORT).show();
            // take screenshot of view
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

            // take screenshot
            if (!screenshot) {
                takeScreenshot(rootView);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method is not used in this example
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        boolean shake = prefs.getBoolean("shake", false);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        // Get the sensor manager and the accelerometer sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (prefs.getBoolean("shake", false)) {
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                //Toast.makeText(getApplicationContext(), "Shake is enabled", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "Shake is disabled", Toast.LENGTH_SHORT).show();
                sensorManager.unregisterListener(this);
            }
        });
        // Register a SensorEventListener to receive updates from the accelerometer

        if (shake) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getApplicationContext(), "Shake is disabled", Toast.LENGTH_SHORT).show();
        }
    }
    // take screenshot of current activity and save to gallary
    public void takeScreenshot(View view) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bitmap = getScreenShot(rootView);
        screenshot = true;
        store(bitmap, "screenshot.png");
    }

    // get screenshot of current activity
    private Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    // save screenshot to gallary
    private void store(Bitmap bm, String fileName) {
        // Get the content resolver
        ContentResolver resolver = getContentResolver();

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageData = stream.toByteArray();

        // Create a new image file
        String filename = "image.jpg";
        String mimeType = "image/jpeg";
        String description = "This is my image";

        // Insert the image into the MediaStore
        Uri url = Uri.parse(MediaStore.Images.Media.insertImage(resolver, bm, filename, description));

        // Get the path to the saved image
        String path = url.getPath();
        Log.d("path", path);
        Toast.makeText(getApplicationContext(), "Screenshot saved to " + path, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (prefs.getBoolean("shake", false)) {
                sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                //Toast.makeText(getApplicationContext(), "Shake is enabled", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getApplicationContext(), "Shake is disabled", Toast.LENGTH_SHORT).show();
                sensorManager.unregisterListener(MainActivity.this);
            }
        });
        if (prefs.getBoolean("shake", false)) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            //Toast.makeText(getApplicationContext(), "Shake is enabled", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Shake is disabled", Toast.LENGTH_SHORT).show();
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}