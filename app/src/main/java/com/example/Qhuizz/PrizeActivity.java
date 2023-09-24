package com.example.Qhuizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.app.R;

public class PrizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize);
    }

    public void openPrize1(View view) {
        String url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (isConnected()){

            startActivity(intent);}
            else{
                Toast.makeText(this, "Internet not Available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cannot Display the Prize", Toast.LENGTH_SHORT).show();
        }
    }

    public void openPrize2(View view) {
        String url = "https://youtu.be/8r4ulmq5P1c?t=14";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if ((intent.resolveActivity(getPackageManager()) != null)) {
            if (isConnected()){
                startActivity(intent);
            }else{
                Toast.makeText(this, "Internet not Available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cannot Display the Prize", Toast.LENGTH_SHORT).show();
        }
    }

    public void openPrize3(View view){
        String url = "https://www.youtube.com/watch?v=mLirlyAUtJQ";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if ((intent.resolveActivity(getPackageManager())!=null)){
            if (isConnected()){
                startActivity(intent);
            }else{
                Toast.makeText(this, "Internet not Available", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Cannot Display your Reward", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void returnHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}