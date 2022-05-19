package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    AudioManager audioManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("com.example.myapplication.IN_POCKET_TRIGGER".equals(intent.getAction())) {
            Toast.makeText(context,"ses arttırıldı",Toast.LENGTH_SHORT).show();
            AudioManager audio = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float percent = 0.9f;
            int ninetyVolume = (int) (maxVolume*percent);
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, ninetyVolume, 0);

        }
        if ("com.example.myapplication.ON_TABLE_TRIGGER".equals(intent.getAction())) {
            Toast.makeText(context,"ses kısıldı",Toast.LENGTH_SHORT).show();
            AudioManager audio = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float percent = 0.1f;
            int tenVolume = (int) (maxVolume*percent);
            audio.setStreamVolume(AudioManager.STREAM_MUSIC, tenVolume, 0);

        }

    }
}
