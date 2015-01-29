/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

public class MediaScannerOnBootPreferenceController extends
        DeveloperOptionsPreferenceController implements Preference.OnPreferenceChangeListener,
        PreferenceControllerMixin {

    private static final String KEY_MEDIA_SCANNER_ON_BOOT = "media_scanner_on_boot";

    private final String[] mListValues;
    private final String[] mListSummaries;

    public MediaScannerOnBootPreferenceController(Context context) {
        super(context);
        mListValues = context.getResources().getStringArray(R.array.media_scanner_on_boot_values);
        mListSummaries = context.getResources().getStringArray(R.array.media_scanner_on_boot_entries);
    }

    @Override
    public String getPreferenceKey() {
        return KEY_MEDIA_SCANNER_ON_BOOT;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        writeMSOBOptions(newValue);
        updateMSOBOptions();
        return true;
    }

    @Override
    public void updateState(Preference preference) {
        updateMSOBOptions();
    }

    @Override
    protected void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        writeMSOBOptions(0);
    }

    private void updateMSOBOptions() {
        int value = Settings.System.getInt(mContext.getContentResolver(),
                        Settings.System.MEDIA_SCANNER_ON_BOOT, 0);
        int index = 0;
        for (int i = 0; i < mListValues.length; i++) {
            int val = Integer.parseInt(mListValues[i]);
            if (val >= value) {
                index = i;
                break;
            }
        }
        final ListPreference listPreference = (ListPreference) mPreference;
        listPreference.setValue(mListValues[index]);
        listPreference.setSummary(mListSummaries[index]);
    }

    private void writeMSOBOptions(Object newValue) {
        Settings.System.putInt(mContext.getContentResolver(),
            Settings.System.MEDIA_SCANNER_ON_BOOT,
            Integer.valueOf((String) newValue));
        updateMSOBOptions();
    }
}
