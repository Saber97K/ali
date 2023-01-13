package com.example.myapplication.added_wallet;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMain7WalletAddedBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity7_wallet extends AppCompatActivity implements SensorEventListener {


    private ActivityMain7WalletAddedBinding binding;
    private SensorManager mSensorManager;
    private boolean screenshot = false;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain7WalletAddedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        // Get the sensor manager and the accelerometer sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register a SensorEventListener to receive updates from the accelerometer
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
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
        String title = "My Image";
        String description = "This is my image";

        // Insert the image into the MediaStore
        Uri url = Uri.parse(MediaStore.Images.Media.insertImage(resolver, bm, title, description));

        // Get the path to the saved image
        String path = url.getPath();
        Log.d("path", path);
        Toast.makeText(getApplicationContext(), "Screenshot saved to " + path, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method is not used in this example
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }

}