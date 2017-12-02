/*
 * Copyright (C) 2021 Benzo Rom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

public class ProximityOnWakePreferenceController extends TogglePreferenceController {

    private static final String PROXIMITY_ON_WAKE = "proximity_on_wake";

    private static final int ON = 1;
    private static final int OFF = 0;

    private Fragment mParent;

    public ProximityOnWakePreferenceController(Context context, String key) {
        super(context, key);
    }

    public void init(Fragment fragment) {
        mParent = fragment;
    }

    @Override
    public int getAvailabilityStatus() {
        return mContext.getResources().getBoolean(
                com.android.internal.R.bool.config_proximityCheckOnWake)
                        ? AVAILABLE : UNSUPPORTED_ON_DEVICE;
    }

    @Override
    public boolean isChecked() {
        return Settings.Secure.getInt(mContext.getContentResolver(),
                Settings.Secure.PROXIMITY_ON_WAKE,
                OFF) == ON;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        Settings.Secure.putInt(mContext.getContentResolver(),
                Settings.Secure.PROXIMITY_ON_WAKE,
                isChecked ? ON : OFF);
        return true;
    }
}
