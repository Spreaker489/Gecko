# MusicHub Android

Clean Android wrapper for the MusicHub web player using Mozilla GeckoView.

- Android 7.0 (API 24) or newer.
- GeckoView adapts to phone and tablet screen sizes.
- Foreground media service exposes lock-screen and notification transport controls.
- Uses zrok's documented `skip_zrok_interstitial: 1` request header only.

GitHub Actions builds a debug APK on pushes to `musichub-rebuild`.

Background playback and media controls must be validated on a physical Android device with the live web player.
