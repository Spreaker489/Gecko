package com.musichub.geckoview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoSessionSettings;
import org.mozilla.geckoview.GeckoView;
import org.mozilla.geckoview.MediaSession;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TARGET_URL = "https://musichub.shares.zrok.io";
    private GeckoSession session;
    private GeckoRuntime runtime;
    private MediaSession currentMediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 10);
        }

        Intent serviceIntent = new Intent(this, MusicPlaybackService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(serviceIntent);
        else startService(serviceIntent);

        GeckoView geckoView = findViewById(R.id.geckoview);
        session = new GeckoSession();
        runtime = GeckoRuntime.create(this);

        GeckoSessionSettings settings = new GeckoSessionSettings.Builder()
                .userAgentOverride("MusicHubApp/1.0")
                .build();
        session.setSettings(settings);

        session.setMediaSessionDelegate(new MediaSession.Delegate() {
            @Override
            public void onActivated(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
                currentMediaSession = mediaSession;
            }

            @Override
            public void onDeactivated(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
                if (currentMediaSession == mediaSession) {
                    currentMediaSession = null;
                }
            }

            @Override
            public void onMetadata(@NonNull GeckoSession session, @NonNull MediaSession mediaSession, @NonNull MediaSession.Metadata metadata) {
                currentMediaSession = mediaSession;
                MusicPlaybackService service = MusicPlaybackService.getInstance();
                if (service != null) {
                    String title = metadata.title != null ? metadata.title : "MusicHub";
                    String artist = metadata.artist != null ? metadata.artist : "";
                    service.updateMetadata(title, artist, null);
                }
            }

            @Override
            public void onPlay(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
                currentMediaSession = mediaSession;
                MusicPlaybackService service = MusicPlaybackService.getInstance();
                if (service != null) service.updatePlaybackState(true, 0);
            }

            @Override
            public void onPause(@NonNull GeckoSession session, @NonNull MediaSession mediaSession) {
                currentMediaSession = mediaSession;
                MusicPlaybackService service = MusicPlaybackService.getInstance();
                if (service != null) service.updatePlaybackState(false, 0);
            }
        });

        session.open(runtime);
        geckoView.setSession(session);

        Map<String, String> headers = new HashMap<>();
        headers.put("skip_zrok_interstitial", "true");
        
        GeckoSession.Loader loader = new GeckoSession.Loader();
        loader.uri(TARGET_URL);
        loader.additionalHeaders(headers);
        session.load(loader);

        MusicPlaybackService.setCommandListener(new MusicPlaybackService.PlaybackCommandListener() {
            @Override public void onPlay() {
                runOnUiThread(() -> { if (currentMediaSession != null) currentMediaSession.play(); });
            }
            @Override public void onPause() {
                runOnUiThread(() -> { if (currentMediaSession != null) currentMediaSession.pause(); });
            }
            @Override public void onNext() {
                runOnUiThread(() -> { if (currentMediaSession != null) currentMediaSession.nextTrack(); });
            }
            @Override public void onPrevious() {
                runOnUiThread(() -> { if (currentMediaSession != null) currentMediaSession.previousTrack(); });
            }
            @Override public void onSeek(long positionMs) {
                runOnUiThread(() -> { if (currentMediaSession != null) currentMediaSession.seekTo(positionMs / 1000.0, false); });
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (session != null) { 
            session.close(); 
            session = null; 
        }
        super.onDestroy();
    }
}
