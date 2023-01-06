package nam.tran.prm391x_project_1_nam_tran;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class PhoneStateEmoijReceiver extends BroadcastReceiver {
    private static String TAG = PhoneStateEmoijReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);

                if (state != TelephonyManager.CALL_STATE_RINGING) return;

                Log.i(TAG, "This is phone number: " + phoneNumber);
                Log.i(TAG, "This is state: " + state);


                SharedPreferences sharedPref = context.getSharedPreferences("FILE_SAVED", Context.MODE_PRIVATE);
                String path = sharedPref.getString(phoneNumber, "");

                Log.i(TAG, "This is path :" + path);
                try {
                    Bitmap photo = BitmapFactory.decodeStream(context.getAssets().open(path));
                    View iv_toast = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);

                    ((ImageView) iv_toast.findViewById(R.id.iv_toast)).setImageBitmap(photo);


                    Toast toast = new Toast(context);
                    toast.setView(iv_toast);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
