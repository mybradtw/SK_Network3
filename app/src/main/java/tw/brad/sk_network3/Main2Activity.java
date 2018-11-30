package tw.brad.sk_network3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {
    private WebView webView;
    private LocationManager lmgr;
    private MyListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);


    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }else{
            finish();
        }
    }

    private void init(){

        lmgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myListener = new MyListener();

        lmgr.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,0,0,
                myListener);


        webView = findViewById(R.id.webview);
        initWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    },
                    123);
        }else{
            init();
        }






    }

    @Override
    protected void onStop() {
        super.onStop();
        lmgr.removeUpdates(myListener);
    }

    private class MyListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.v("brad", lat + ", " + lng);

            webView.loadUrl("javascript:gotoKD(" + lat + "," + lng + ")");

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }



    private void initWebView(){
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //webView.loadUrl("https://www.sakura.com.tw");
        webView.loadUrl("file:///android_asset/mymap.html");

    }

    public void test1(View view){
//        webView.loadUrl("javascript:createLottery()");
        // 24.001420, 121.606043
        double lat = 24.001420;
        double lng = 121.606043;
        webView.loadUrl("javascript:gotoKD(" + lat + "," + lng + ")");
    }

    public void test2(View view){

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
