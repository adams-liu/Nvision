package com.example.david.circle;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import static android.app.Activity.RESULT_CANCELED;


public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 0;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //For the Credits button
        Button btn_Credits = (Button) findViewById(R.id.btn_Credits);

        image = (ImageView)findViewById(R.id.image);

        btn_Credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starting = new Intent(getApplicationContext(), Credits.class);
                startActivity(starting);
            }
        });

        //For the Camerabutton
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              startActivityForResult(intent,0);

            }
        });


    }
    Bitmap bitmap;
@Override
// requestCode is for the cancel, resultcode is for request camera storage
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode !=RESULT_CANCELED) {
            if (requestCode == CAMERA_REQUEST) {
                bitmap = (Bitmap) data.getExtras().get("data");
                openActivityImage();


            }

        }

    }

    public void openActivityImage(){
    Intent intent = new Intent(this,Image_Page.class);
    intent.putExtra("BitmapImage", bitmap);
    startActivity(intent);
    }


}
