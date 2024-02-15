package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button toggleButton;
    private boolean flashlightOn;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Check if the device has a flashlight
        PackageManager pm = getPackageManager();
        flashlightOn = false;
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            // Device doesn't have a flashlight
            // Handle accordingly, maybe show a message or disable the toggle button
            return;
        }

        toggleButton = findViewById(R.id.toggle_button);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flashlightOn) {
                    flashlightOn = true;
                    toggleButton.setText("Turn Off");
                    turnOnFlashlight();
                } else {
                    flashlightOn = false;
                    toggleButton.setText("Turn On");
                    turnOffFlashlight();
                }
            }
        });
    }

    private void turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


    }
}