
package org.inftel.tms.mobile.ui;

import org.inftel.tms.mobile.R;
import org.inftel.tms.mobile.TmsConstants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ExampleAlarmUserActivity extends Activity {

    private static final String TAG = "ExampleAlarmUserActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmuser);

        // Preferencias de usurio parte de agustin creo
        // SharedPreferences prefs = getSharedPreferences("MisPreferencias",
        // MODE_PRIVATE);
        // SharedPreferences.Editor editor = prefs.edit();

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ExampleAlarmUserActivity.this);
        Log.i(TAG, prefs.getString(TmsConstants.KEY_IP_PREFERENCE, ""));
        Log.i(TAG, prefs.getString(TmsConstants.KEY_PORT_PREFERENCE, ""));

        // Aqui van los datos de prueba de SharedPreference
        // editor.putString("NAME", "Cristian Jimenez");
        // editor.putString("URI", "www.mipaginilla.com");
        // editor.commit();

        ((Button) findViewById(R.id.button1)).setOnClickListener(sendAUListener);
    }

    OnClickListener sendAUListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(ExampleAlarmUserActivity.this, SendUserAlertActivity.class);
            SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

            // Recolectamos los datos necesarios de las SharedPreferences
            String name = prefs.getString("NAME", "");
            String uri = prefs.getString("URI", "");

            // Los a�adimos al activity
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name);
            i.putExtra("USERDATA", bundle);
            bundle.putString("URI", uri);
            i.putExtra("SERVERDATA", bundle);

            // Enviamos el activity
            startActivity(i);
        }
    };
}
