package com.example.imagedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.imagedownloader.databinding.ActivityMainBinding;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.SubmitButton.setOnClickListener(view -> {
            loadPic();
        });
    }

    private void stopLoading() {
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    private void startLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void loadPic() {
        String downloadURL = binding.urlEditText.getText().toString();

        startLoading();

        new DownloadThread(downloadURL, this, this::ShowLoadedPic).start();
    }

    private void ShowLoadedPic(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        stopLoading();

        binding.imageView.setImageBitmap(bitmap);
    }
}