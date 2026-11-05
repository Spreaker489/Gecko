package com.musichub.geckoview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoSessionSettings;
import org.mozilla.geckoview.GeckoView;
import org.mozilla.geckoview.MediaSession;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String HOME_URL = "https://musichub.shares.zrok.io";
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static GeckoRuntime runtime;
    private static GeckoSession session;
    private static MediaSession activeMedia;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 33 &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 42);
        }
        Intent service = new Intent(this, MusicPlaybackService.class);
        if (Build.VERSION.SDK_INT >= 26) startForegroundService(service); else startService(service);

        if (runtime == null) runtime = GeckoRuntime.create(getApplicationContext());
        if (session == null) {
            GeckoSessionSettings settings = new GeckoSessionSettings.Builder()
                    .userAgentOverride("MusicHubAndroid/1.2.1").build();
            session = new GeckoSession(settings);
            session.setMediaSessionDelegate(new MediaSession.Delegate() {
                @Override public void onActivated(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                }
                @Override public void onDeactivated(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    if (activeMedia == media) activeMedia = null;
                }
                @Override public void onMetadata(@NonNull GeckoSession s, @NonNull MediaSession media,
                                                 @NonNull MediaSession.Metadata metadata) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) {
                        svc.updateMetadata(metadata.title, metadata.artist, metadata.album);
                        if (metadata.artwork != null) {
                            metadata.artwork.getBitmap(512).accept(
                                    bitmap -> {
                                        MusicPlaybackService current = MusicPlaybackService.getInstance();
                                        if (current != null) current.updateArtwork(bitmap);
                                    },
                                    error -> android.util.Log.w("MusicHub", "Could not load track artwork", error));
                        } else {
                            svc.updateArtwork(null);
                        }
                    }
                }
                @Override public void onPlay(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(true);
                }
                @Override public void onPause(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(false);
                }
                @Override public void onStop(@NonNull GeckoSession s, @NonNull MediaSession media) {
                    activeMedia = media;
                    MusicPlaybackService svc = MusicPlaybackService.getInstance();
                    if (svc != null) svc.updatePlaybackState(false);
                }
            });
            session.open(runtime);
            // Documented zrok interstitial opt-in only; it does not bypass authentication.
            session.load(new GeckoSession.Loader().uri(HOME_URL)
                    .headerFilter(GeckoSession.HEADER_FILTER_UNRESTRICTED_UNSAFE)
                    .additionalHeaders(Collections.singletonMap("skip_zrok_interstitial", "1")));
        }
        GeckoView view = findViewById(R.id.geckoview);
        view.setSession(session);
        MusicPlaybackService.setCommandListener(new MusicPlaybackService.CommandListener() {
            @Override public void play() { MAIN.post(() -> { if (activeMedia != null) activeMedia.play(); }); }
            @Override public void pause() { MAIN.post(() -> { if (activeMedia != null) activeMedia.pause(); }); }
            @Override public void next() { MAIN.post(() -> { if (activeMedia != null) activeMedia.nextTrack(); }); }
            @Override public void previous() { MAIN.post(() -> { if (activeMedia != null) activeMedia.previousTrack(); }); }
        });
    }
    @Override protected void onDestroy() {
        // The static Gecko session remains open to support background web audio.
        super.onDestroy();
    }
}
