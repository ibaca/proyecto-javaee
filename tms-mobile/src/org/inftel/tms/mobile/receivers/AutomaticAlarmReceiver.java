/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.inftel.tms.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * The manifest Receiver is used for Alarm Manager. When the Alarm Manager
 * broadcasts a pending intent we sends an automatic message to the server.
 */
public class AutomaticAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AutomaticAlarmReceiver"; // for debug

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "STARTED");
        // Alarm sent by service
        Intent service = new Intent();
        service.setAction("org.inftel.tms.mobile.services.AutomaticAlarmService");
        context.startService(service);
        Log.d(TAG, "GO BACK");
    }
}
