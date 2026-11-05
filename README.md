# MusicHub pentru Android

MusicHub este o aplicație Android care oferă acces la playerul web MusicHub într-o interfață dedicată, folosind **Mozilla GeckoView**. Proiectul urmărește să păstreze experiența de redare a muzicii și comenzile media Android, oferind în același timp o bază pentru distribuirea mai eficientă a aplicației pe dispozitive diferite.

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

Prin urmare, Android 8.0 și versiunile ulterioare sunt incluse în intervalul de compatibilitate configurat. Funcționarea efectivă a redării în fundal și a comenzilor media poate depinde de versiunea Android, de producătorul dispozitivului și de comportamentul playerului web.

## Compilare

Proiectul poate fi compilat cu Android Studio sau cu Gradle, folosind un JDK compatibil cu versiunea Android Gradle Plugin configurată.

Pentru un APK de depanare:

```bash
./gradlew assembleDebug
```

Pentru un Android App Bundle de lansare:

```bash
./gradlew bundleRelease
```

Fișierele rezultate sunt generate în directoarele standard Gradle din `app/build/outputs/`.

## APK și Android App Bundle (AAB)

- **APK:** pachet instalabil direct pe un dispozitiv Android.
- **AAB:** format de publicare pentru Google Play. Google Play poate genera și livra componentele potrivite configurației dispozitivului, inclusiv arhitecturii procesorului, în loc să distribuie obligatoriu toate variantele într-un singur APK universal.
- Un fișier **AAB nu se instalează direct** prin atingere, precum un APK; pentru testare directă se folosește APK-ul sau un pachet generat din AAB.

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
