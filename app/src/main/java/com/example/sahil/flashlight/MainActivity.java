package com.example.sahil.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    Camera camera;
   Camera.Parameters parameters;
    boolean isflash =false;
    boolean ison =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton)findViewById(R.id.imageButton);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera =  Camera.open();
            parameters = camera.getParameters();
            isflash = true;
        }

        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isflash)
                        {
                            if(!ison)
                            {
                                imageButton.setImageResource(R.drawable.on);
                                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                                camera.setParameters(parameters);
                                camera.startPreview();
                                ison = true;

                            }
                            else
                            {
                                imageButton.setImageResource(R.drawable.of);
                                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                                camera.setParameters(parameters);
                                camera.stopPreview();
                                ison = false;
                            }

                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Error...");
                            builder.setMessage("Flash is Not Available on this Device...");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    finish();

                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                }
        );

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera !=null)
        {
            camera.release();
            camera = null;
        }
    }
}
