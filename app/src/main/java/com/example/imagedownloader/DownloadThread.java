package com.example.imagedownloader;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

public class DownloadThread extends Thread {
    private String downloadingURL;
    private Context context;
    private Consumer<InputStream> dataCallback;

    public DownloadThread(String downloadingURL, Context context, Consumer<InputStream> dataCallback) {
        this.downloadingURL = downloadingURL;
        this.context = context;
        this.dataCallback = dataCallback;
    }


    @Override
    public void run() {
        super.run();

        try {
            URL convertedDownloadingURL = createURL();

            InputStream inputStream = convertedDownloadingURL.openStream();

            ContextCompat.getMainExecutor(context).execute(() -> {
                if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.N) {
                    dataCallback.accept((inputStream));
                }
            });
        } catch (Exception exception) {
            ContextCompat.getMainExecutor(context).execute(() -> {
                Toast.makeText(context, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private URL createURL() throws MalformedURLException {
        return new URL(downloadingURL);
    }
}
