# MusicHub Android

Clean Android wrapper for the MusicHub web player using Mozilla GeckoView.

- Android 7.0 (API 24) or newer.
- GeckoView adapts to phone and tablet screen sizes.
- Foreground media service exposes lock-screen and notification transport controls.
- Uses zrok's documented `skip_zrok_interstitial: 1` request header only.

GitHub Actions builds a debug APK on pushes to `musichub-rebuild`.

Background playback and media controls must be validated on a physical Android device with the live web player.

## Despre aplicație (About)

MusicHub 1.2.1 este o aplicație Android care aduce playerul web MusicHub într-o aplicație dedicată, folosind motorul Mozilla GeckoView. Scopul ei este să ofere acces simplu la muzică și să integreze redarea cu funcțiile media ale sistemului Android.

Aplicația include comenzi pentru piesa anterioară, redare/pauză și piesa următoare, precum și afișarea informațiilor despre melodie și a copertei albumului în notificare și pe ecranul de blocare, atunci când playerul și dispozitivul permit acest lucru.

Configurația proiectului folosește `minSdk 24` (Android 7.0 sau mai nou), incluzând Android 8.0+. Sunt prevăzute trei variante de distribuție:
- **Classic – Universal APK:** pachet universal pentru instalare directă.
- **Slim – ABI APK:** pachete separate după arhitectura procesorului; trebuie ales fișierul compatibil cu dispozitivul.
- **Google Play – AAB:** pachet pentru publicare prin Google Play, nu pentru instalare directă.

Variantele Slim și AAB trebuie verificate pe dispozitive reale înainte de a fi considerate stabile. Nu este garantată o anumită dimensiune finală a fișierului sau a instalării.

**Versiune documentată:** 1.2.1  
**Dezvoltator:** marius_489_spk

## About the App

MusicHub 1.2.1 brings the MusicHub web player into a dedicated Android application powered by Mozilla GeckoView. Its goal is to provide convenient access to music and integrate playback with Android media features.

The app includes previous-track, play/pause, and next-track controls, together with track information and album artwork in the media notification and on the lock screen whenever supported by the player and device.

The project is configured with `minSdk 24` (Android 7.0 or later), including Android 8.0+. Three distribution options are planned:
- **Classic – Universal APK:** a universal package for direct installation.
- **Slim – ABI APKs:** separate packages for specific CPU architectures; choose the package matching the device.
- **Google Play – AAB:** a bundle for Google Play distribution, not for direct installation.

The Slim and AAB variants must be validated on real devices before being considered stable. No specific final download or installation size is guaranteed.

**Documented version:** 1.2.1  
**Developer:** marius_489_spk

## Safe Optimization

Size and packaging optimization work is being developed separately on the `musichub-safe-optimization` branch so that the stable `main` branch remains protected while the Classic APK, Slim ABI APKs, and Google Play AAB are built and tested. Changes from that branch should be integrated into `main` only after successful build checks and real-device validation of playback, background playback, notifications, lock-screen artwork, and media controls.

## Safe Optimization (English)

Packaging and app-size optimization work is being developed separately on the `musichub-safe-optimization` branch. This keeps the stable `main` branch protected while the Classic APK, Slim ABI APKs, and Google Play AAB are built and tested. Changes should be merged into `main` only after successful build checks and real-device validation of playback, background playback, notifications, lock-screen artwork, and media controls.

