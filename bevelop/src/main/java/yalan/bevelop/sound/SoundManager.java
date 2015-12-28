/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.SeekBar;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class SoundManager implements MediaPlayer.OnCompletionListener {

    public interface OnSoundStateListener {

        public void onStart(SoundInfo info);

        public void onPause(SoundInfo info);

        public void onStop(SoundInfo info);

    }
    private static SoundManager instance;
    protected final static String TAG = "SoundManager";
    protected MediaPlayer player;
    protected HashMap<String, SoundInfo> soundMap;
    protected boolean isPlaying = false;
    protected String currenMessageId;
    protected SeekBar currenSeekBar;
    protected Handler handler;
    protected SoundInfo currentSoundInfo;
    protected OnSoundStateListener onSoundStateListener;

    private SoundManager() {
        player = new MediaPlayer();
        soundMap = new HashMap<String, SoundInfo>();
        handler = new Handler(Looper.getMainLooper());
        player.setOnCompletionListener(this);
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public SoundInfo getSoundInfo(String messageId) {
        return soundMap.get(messageId);
    }

    public void checkMap(String messageId, String path, Uri uri) {
        if (soundMap.containsKey(messageId)) {
            soundMap.get(messageId).setPath(path);
            soundMap.get(messageId).setUri(uri);
            //player.seekTo(soundMap.get(messageId).getProccess());
        } else {
            SoundInfo sound = new SoundInfo();
            sound.setMessageId(messageId);
            sound.setPath(path);
            sound.setUri(uri);
            soundMap.put(messageId, sound);
        }
    }

    public void pause() {
        if (currentSoundInfo != null && player != null && player.isPlaying()) {
            currentSoundInfo.setProccess(player.getCurrentPosition());
            soundMap.put(currentSoundInfo.getMessageId(), currentSoundInfo);
            stop();
            if (onSoundStateListener != null) {
                onSoundStateListener.onPause(currentSoundInfo);
            }
        }
    }

    public boolean setTrack(Context context, File media, String messageId, SeekBar bar) {
        try {
            stop();
            if (soundMap.containsKey(messageId)) {
                currentSoundInfo = soundMap.get(messageId);
            } else {
                currentSoundInfo = new SoundInfo();
                currentSoundInfo.setMessageId(messageId);
                currentSoundInfo.setPath(media.getPath());
                currentSoundInfo.setProccess(0);
            }
            if (player == null) {
                player = new MediaPlayer();
            }
            player.setOnCompletionListener(this);
            player.setDataSource(media.getPath());
        } catch (Exception ex) {
            Log.e("bevelop", "", ex);
        }
        return prepare(messageId, bar);
    }

    public boolean prepare(String messageId, SeekBar bar) {
        try {
            player.prepare();
            currenMessageId = messageId;
            if (bar != null) {
                currenSeekBar = bar;
                currenSeekBar.setMax(player.getDuration());
                currenSeekBar.setProgress(currentSoundInfo.getProccess());
            }
            currentSoundInfo.setMax(player.getDuration());
            // Log.i(TAG, "setTrack success:" + messageId + "/" + media.getPath() + " , " + (player.getDuration()));
            return true;
        } catch (IOException ex) {
            Log.e(TAG, null, ex);
            return false;
        } catch (IllegalStateException ex) {
            Log.e(TAG, null, ex);
            return true;
        }
    }

    public void play() {
        //
        isPlaying = true;
        player.seekTo(currentSoundInfo.getProccess());
        player.start();
        if (onSoundStateListener != null) {
            onSoundStateListener.onStart(currentSoundInfo);
        }
        //
        handler.post(run);

    }

    public boolean isPlaying() {

        return isPlaying;

    }

    public String getPlayingId() {
        return currentSoundInfo.getMessageId();
    }
    protected Thread run = new Thread(new Runnable() {

        public void run() {
            if (player != null && player.isPlaying() && isPlaying) {
                Log.i(TAG, "sound playing:" + player.getCurrentPosition());
                if (currenSeekBar != null) {
                    currenSeekBar.setProgress(player.getCurrentPosition());
                    currenSeekBar.invalidate();
                }
                //bStop.setText(song1.getCurrentPosition());
                handler.postDelayed(run, 100);
            }
        }
    });

    public void stop() {
        Log.i(TAG, "onStop! Player is null?" + (player == null));
        if (player != null) {
            if (player.isPlaying() && isPlaying) {
                isPlaying = false;
                Log.i(TAG, "stop player");
                player.stop();
                if (onSoundStateListener != null) {
                    onSoundStateListener.onStop(currentSoundInfo);
                }
            }
            player.release();
            player = null;
        }
    }

    public void onCompletion(MediaPlayer arg0) {
        isPlaying = false;
        if (onSoundStateListener != null) {
            onSoundStateListener.onStop(currentSoundInfo);
        }
        Log.i(TAG, "MediaPlayer onCompletion");
        if (currentSoundInfo != null) {
            this.soundMap.remove(currentSoundInfo.getMessageId());
        }
        if (currenSeekBar != null) {
            currenSeekBar.setProgress(0);
        }
        stop();
    }

    public OnSoundStateListener getOnSoundStateListener() {
        return onSoundStateListener;
    }

    public void setOnSoundStateListener(OnSoundStateListener onSoundStateListener) {
        this.onSoundStateListener = onSoundStateListener;
    }

}
