package com.example.david.circle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import android.speech.tts.TextToSpeech;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;


import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;

import java.io.ByteArrayOutputStream;

public class Results extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private Switch mSwitchLang;
    private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?";
    private static final String API_KEY = "key=AIzaSyDys-WAvLqUr1FAFEmvq0q-66QaT8qU-NQ";
    private static final String img = "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";
    private static final Base64 Base64 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        String strBase64 = BitMapToString(bitmap);
//        JSONObject jsonObject = new JSONObject();
//        try {
//           jsonObject = postRequestJSON(strBase64);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String[] stringArr = new String[100];
//        stringArr = getStringArray(jsonObject);
//        speak(stringArr);


        Button btnBacktoMain = (Button) findViewById(R.id.btnBacktoMain);
        btnBacktoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
// Convert BitMap to String
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        System.out.print(temp);
        return temp;
    }

// Parse jSon files to string array
    public String[] getStringArray(JSONObject jsonObject) {
        JSONParser parser = new JSONParser();

        try {
//            Object obj = parser.parse(new FileReader("img2.json"));
//            JSONObject jsonObject = (JSONObject) ;

            JSONArray name = (JSONArray) jsonObject.get("labelAnnotations");
            //System.out.println("Name is: " + name);
            String[] bla = new String[name.size()];
            for (int i = 0; i < name.size(); i++) {
                bla[i] = (String) ((JSONObject) name.get(i)).get("description");
            }
            for (int i = 0; i < bla.length; ++i)
                System.out.println(bla[i]);
            return bla;
        }
//        catch(FileNotFoundException e) {e.printStackTrace();}
//        catch(IOException e) {e.printStackTrace();}
//		catch(ParseException e) {e.printStackTrace();}
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Google cloud API call
    public static JSONObject postRequestJSON(String base64Img) throws IOException, JSONException {
        String feature = "LABEL_DETECTION";
        String mode = "content";
        String content = base64Img;

        URL serverUrl = new URL(TARGET_URL + API_KEY);
        URLConnection urlConnection = serverUrl.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;

        httpConnection.setRequestMethod("POST");
        httpConnection.setRequestProperty("Content-Type", "application/json");

        httpConnection.setDoOutput(true);

        OutputStreamWriter o = new OutputStreamWriter(httpConnection.getOutputStream());

        BufferedWriter bw = new BufferedWriter(o);

        BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));

        httpRequestBodyWriter.write
                ("{\"requests\":  [{ \"features\":  [ {\"type\": \"" + feature + "\""
                        +"}], \"image\": {\"" + mode + "\":"
                        +" \"" + content + "\"}}]}");
        httpRequestBodyWriter.close();

        String response = httpConnection.getResponseMessage();

        if (httpConnection.getInputStream() == null) {
            System.out.println("No stream");
            return null;
        }

        Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());
        String resp = "";
        while (httpResponseScanner.hasNext()) {
            String line = httpResponseScanner.nextLine();
            resp += line;
            System.out.println(line);  //  alternatively, print the line of response
        }

        httpResponseScanner.close();
        JSONParser p = new JSONParser();
        try {
            JSONObject obj = (JSONObject) p.parse(resp);
            return obj;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Speech actually speaking
    private void speak(String textArray[]) {
//        String textArray[] = getStringArray(null);
        String text = mEditText.getText().toString();
        if (!mSwitchLang.isChecked()) {
            mTTS.setLanguage(Locale.ENGLISH);
        } else {
            mTTS.setLanguage(Locale.GERMAN);
        }

        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setSpeechRate(speed);
        String sumString = "";
        for(String foo:textArray){
            sumString += foo;
        }
        mTTS.speak(sumString, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

        @Override
    public void onBackPressed() {
        Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(startMain);
    }
}

