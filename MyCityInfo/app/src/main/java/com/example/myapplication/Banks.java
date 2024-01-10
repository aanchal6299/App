    package com.example.myapplication;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.location.Location;
    import android.os.Build;
    import android.os.Bundle;
    import android.webkit.GeolocationPermissions;
    import android.webkit.PermissionRequest;
    import android.webkit.WebChromeClient;
    import android.webkit.WebView;
    import android.webkit.WebViewClient;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;

    public class Banks extends AppCompatActivity {

        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transport); // Replace with the layout file for this activity

            Intent intent = getIntent();
    //        String from = intent.getStringExtra("sourceEditText");
    //        String to = intent.getStringExtra("destinationEditText");

            WebView webView = findViewById(R.id.irctc);
            webView.getSettings().setGeolocationEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

            // Set up WebChromeClient to handle geolocation permissions
            webView.setWebChromeClient(new WebChromeClient() {
                @SuppressLint("NewApi")
                @Override
                public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                    // Request location permissions if not granted
                    if (ContextCompat.checkSelfPermission(Banks.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Banks.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    } else {
                        // Location permissions already granted
                        callback.invoke(origin, true, false);
                    }
                }

                @Override
                public void onPermissionRequest(PermissionRequest request) {
                    // Handle permission request
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        request.grant(request.getResources());
                    }
                }
            });

            // Load the URL with the parameters
            webView.loadUrl("https://www.google.com/maps/search/banks");
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Reload the WebView after location permissions are granted
                    WebView webView = findViewById(R.id.irctc);
                    webView.reload();
                }
            }
        }
    }
