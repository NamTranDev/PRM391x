package com.example.screentalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==101){
            if (permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                initViews();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{

                    Manifest.permission.READ_PHONE_STATE

            }, 101);

        }else {
            initViews();
        }
    }

    private void initViews() {
        try {
            // khai báo intent-filer trong file Manifest
            // READ MORE: https://developer.android.com/guide/topics/manifest/intent-filter-element
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);


            /*Note: Apps can receive broadcasts in two ways: through manifest-declared receivers and context-registered receivers.
            * Read more: https://developer.android.com/guide/components/broadcasts#receiving-broadcasts
            * =============================================================================================*
            * Dưới đây là đoạn code đăng ký BroadcastReceiver với context
            * READ MORE: https://developer.android.com/guide/components/broadcasts#context-registered-receivers*/
            BroadcastReceiver receiver = new BroadcastReceiver() {
                // khi nhận được tín hiệu từ biến receiver phương thức onReceiver() sẽ thực thi
                @Override
                public void onReceive(Context context, Intent intent) {
                    doScreenTalk(intent.getAction());
                }
            };
            // Đây là câu lệnh đăng ký receiver cùng intent-filter
            /* Với câu lệnh này thì khi xảy ra 1 action đã đăng ký trong biến filter
            * thì BroadcastReceiver sẽ nhận được tín hiệu thông qua biến receiver và chuyển flow control
            * thực thi phương thức onReceiver()*/
            registerReceiver(receiver, filter);

        }catch (Exception ignored){

        }
    }

    /*trong phương thức dưới đây thực thi hành động
    * khi screen on sẽ gọi phương thức sayHello()
    * khi screen off sẽ gọi phương thức sayGoodbye()*/
    private void doScreenTalk(String action) {
        if(action.equals(Intent.ACTION_SCREEN_ON)){
            sayHello();
        }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
            sayGoodbye();
        }
    }

    //Phương thức thực thi khởi chạy file âm thanh bye_bye.mp3
    private void sayGoodbye() {
        MediaPlayer.create(this, R.raw.bye_bye).start();
    }

    //Phương thức thực thi khởi chạy file âm thanh hello.mp3
    private void sayHello() {
        MediaPlayer.create(this, R.raw.hello).start();
    }
}