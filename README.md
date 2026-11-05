# MusicHub pentru Android | MusicHub for Android

**Developer: marius_489_spk**

---

# Română

MusicHub este o aplicație Android care oferă acces la playerul web MusicHub într-o interfață dedicată, folosind **Mozilla GeckoView**. Proiectul urmărește să păstreze experiența de redare a muzicii și comenzile media Android, oferind în același timp o bază pentru distribuirea mai eficientă a aplicației pe dispozitive diferite.

**Developer: marius_489_spk**

## Caracteristici

- **Player web integrat:** încarcă MusicHub în aplicația Android prin GeckoView.
- **Compatibilitate Android:** configurația proiectului folosește `minSdk 24` (Android 7.0 sau mai nou), deci include și Android 8.0+.
- **Redare și control media:** integrare cu comenzile media Android pentru redare/pauză, piesa anterioară și piesa următoare.
- **Notificare media:** oferă comenzile de redare în notificarea Android.
- **Ecran de blocare:** integrarea media permite afișarea informațiilor și comenzilor de redare pe ecranul de blocare, în funcție de suportul versiunii Android și al dispozitivului.
- **Coperta albumului:** aplicația transmite metadatele media și imaginea asociată piesei către interfața media Android, atunci când playerul web furnizează aceste informații.
- **Interfață adaptabilă:** GeckoView afișează playerul web pe telefoane și tablete.

## Tehnologii

- Kotlin / Android
- Mozilla GeckoView
- Gradle și Android Gradle Plugin
- GitHub Actions pentru compilare automată

## Compatibilitate și versiuni

Proiectul este configurat cu:
- `minSdk 24` — Android 7.0+
- `compileSdk 34`
- `targetSdk 34`

Android 8.0 și versiunile ulterioare sunt incluse în intervalul de compatibilitate configurat. Funcționarea efectivă a redării în fundal și a comenzilor media poate depinde de versiunea Android, de producătorul dispozitivului și de comportamentul playerului web.

## Compilare

Proiectul poate fi compilat cu Android Studio sau cu Gradle, folosind un JDK compatibil cu versiunea Android Gradle Plugin configurată.

APK de depanare:

```bash
./gradlew assembleDebug
```

Android App Bundle de lansare:

```bash
./gradlew bundleRelease
```

Fișierele rezultate sunt generate în directoarele standard Gradle din `app/build/outputs/`.

## APK și Android App Bundle (AAB)

- **APK:** pachet instalabil direct pe un dispozitiv Android.
- **AAB:** format de publicare pentru Google Play. Google Play poate genera și livra componentele potrivite configurației dispozitivului, inclusiv arhitecturii procesorului.
- **AAB nu se instalează direct** prin atingere, precum un APK; pentru testare directă se folosește APK-ul sau un pachet generat din AAB.

Workflow-ul GitHub Actions este configurat să compileze APK-ul de depanare și AAB-ul de lansare și să le publice ca artefacte ale execuției workflow-ului.

## Redare în fundal și testare

Comenzile media și metadatele sunt integrate cu mecanismele media Android. Pentru validarea completă trebuie testate pe un dispozitiv fizic:
- pornirea și oprirea redării;
- redare/pauză și navigarea între melodii;
- redarea după trecerea aplicației în fundal;
- afișarea notificării și a comenzilor pe ecranul de blocare;
- afișarea corectă a copertei și a informațiilor piesei.

Comportamentul poate varia în funcție de dispozitiv și de playerul web. Orice optimizare a dimensiunii pachetului trebuie verificată astfel încât să nu afecteze încărcarea interfeței, redarea audio sau comenzile media.

## Scopul optimizărilor

Un obiectiv al proiectului este reducerea dimensiunii descărcării și instalării, păstrând calitatea și stabilitatea redării audio. Optimizările de arhitectură și distribuție trebuie validate prin compilări și teste înainte de a fi integrate în ramura principală.

## Licență

Nu este specificată încă o licență pentru acest repository. Până la adăugarea unui fișier `LICENSE`, nu presupuneți că reutilizarea sau redistribuirea codului este permisă.

---

# English

MusicHub is an Android application that provides access to the MusicHub web player through a dedicated app interface powered by **Mozilla GeckoView**. The project aims to preserve the music playback experience and Android media controls while providing a foundation for more efficient distribution across different devices.

**Developer: marius_489_spk**

## Features

- **Integrated web player:** loads MusicHub inside the Android app using GeckoView.
- **Android compatibility:** the project is configured with `minSdk 24` (Android 7.0 or later), which includes Android 8.0 and newer.
- **Playback and media controls:** integrates with Android media controls for play/pause, previous track, and next track.
- **Media notification:** exposes playback controls in the Android notification.
- **Lock screen support:** media integration can display track information and playback controls on the lock screen, depending on Android version and device support.
- **Album artwork:** the app passes media metadata and the track artwork to the Android media interface when that information is provided by the web player.
- **Adaptive interface:** GeckoView displays the web player on phones and tablets.

## Technologies

- Kotlin / Android
- Mozilla GeckoView
- Gradle and Android Gradle Plugin
- GitHub Actions for automated builds

## Compatibility and SDK versions

The project is configured with:
- `minSdk 24` — Android 7.0+
- `compileSdk 34`
- `targetSdk 34`

Android 8.0 and later are therefore within the configured compatibility range. Actual background playback and media-control behavior may depend on the Android version, device manufacturer, and web player behavior.

## Build

The project can be built with Android Studio or Gradle using a JDK compatible with the configured Android Gradle Plugin version.

Debug APK:

```bash
./gradlew assembleDebug
```

Release Android App Bundle:

```bash
./gradlew bundleRelease
```

Build outputs are generated in the standard Gradle directories under `app/build/outputs/`.

## APK and Android App Bundle (AAB)

- **APK:** a package that can be installed directly on an Android device.
- **AAB:** a publishing format for Google Play. Google Play can generate and deliver device-specific components, including those matching the device's CPU architecture.
- **An AAB cannot be installed directly** by tapping it like an APK. For direct device testing, use an APK or a package generated from the AAB.

The GitHub Actions workflow is configured to build the debug APK and release AAB and publish them as workflow artifacts.

## Background playback and testing

Media controls and metadata are integrated with Android media APIs. Full validation should be performed on a physical Android device and should cover:
- starting and stopping playback;
- play/pause and track navigation;
- playback after the app moves to the background;
- notification and lock-screen controls;
- correct display of album artwork and track information.

Behavior may vary by device and web player. Any package-size optimization must be verified to ensure it does not affect interface loading, audio playback, or media controls.

## Optimization goals

One project goal is to reduce download and installation size while preserving audio quality and playback stability. Architecture and distribution optimizations should be validated through builds and tests before being integrated into the main branch.

## License

No license has been specified for this repository yet. Until a `LICENSE` file is added, do not assume that reuse or redistribution of the code is permitted.

---

**Developer: marius_489_spk**
