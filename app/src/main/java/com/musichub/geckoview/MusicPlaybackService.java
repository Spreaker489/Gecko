package com.musichub.geckoview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

public class MusicPlaybackService extends Service {
    private static final String CHANNEL = "musichub_playback";
    private static final int NOTIFICATION_ID = 71;
    private static MusicPlaybackService instance;
    private static CommandListener listener;
    private MediaSessionCompat mediaSession;
    private String title = "MusicHub";
    private String artist = "";
    private boolean playing;

    public interface CommandListener { void play(); void pause(); void next(); void previous(); }
    public static void setCommandListener(CommandListener value) { listener = value; }
    public static MusicPlaybackService getInstance() { return instance; }

    @Override public void onCreate() {
        super.onCreate();
        instance = this;
        createChannel();
        mediaSession = new MediaSessionCompat(this, "MusicHub");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override public void onPlay() { if (listener != null) listener.play(); }
            @Override public void onPause() { if (listener != null) listener.pause(); }
            @Override public void onSkipToNext() { if (listener != null) listener.next(); }
            @Override public void onSkipToPrevious() { if (listener != null) listener.previous(); }
        });
        mediaSession.setActive(true);
        if (Build.VERSION.SDK_INT >= 29)
            startForeground(NOTIFICATION_ID, buildNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK);
        else startForeground(NOTIFICATION_ID, buildNotification());
    }

    public void updateMetadata(String t, String a) {
        title = t == null || t.isEmpty() ? "MusicHub" : t;
        artist = a == null ? "" : a;
        mediaSession.setMetadata(new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist).build());
        refresh();
    }
    public void updatePlaybackState(boolean value) {
        playing = value;
        long actions = PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
        mediaSession.setPlaybackState(new PlaybackStateCompat.Builder().setActions(actions)
                .setState(playing ? PlaybackStateCompat.STATE_PLAYING : PlaybackStateCompat.STATE_PAUSED,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1f).build());
        refresh();
    }
    private Notification buildNotification() {
        Intent open = new Intent(this, MainActivity.class);
        PendingIntent content = PendingIntent.getActivity(this, 0, open,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        int icon = playing ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play;
        return new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(android.R.drawable.ic_media_play).setContentTitle(title).setContentText(artist)
                .setContentIntent(content).setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setOnlyAlertOnce(true)
                .addAction(android.R.drawable.ic_media_previous, "Previous",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))
                .addAction(icon, playing ? "Pause" : "Play",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE))
                .addAction(android.R.drawable.ic_media_next, "Next",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
                .setStyle(new MediaStyle().setMediaSession(mediaSession.getSessionToken())
                        .setShowActionsInCompactView(0, 1, 2)).build();
    }
    private void refresh() {
        NotificationManager nm = getSystemService(NotificationManager.class);
        if (nm != null) nm.notify(NOTIFICATION_ID, buildNotification());
    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel c = new NotificationChannel(CHANNEL,
                    getString(R.string.media_channel_name), NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(c);
        }
    }
    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) MediaButtonReceiver.handleIntent(mediaSession, intent);
        return START_STICKY;
    }
    @Override public void onDestroy() {
        listener = null;
        if (mediaSession != null) { mediaSession.setActive(false); mediaSession.release(); }
        instance = null;
        super.onDestroy();
    }
    @Override public IBinder onBind(Intent intent) { return null; }
}
