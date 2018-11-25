package com.example.david.circle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Image_Page extends AppCompatActivity {

    ImageView imageView2;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__page);
        Intent intent = getIntent();
        bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setImageBitmap(bitmap);

        Button btnReveal = (Button) findViewById(R.id.btnReveal);
        btnReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Results.class);
                startActivity(intent);
                intent.putExtra("BitmapImage", bitmap);
                startActivity(intent);
            }
        });

    }


}
