# The Oven — Customer App (Android)

Customer ordering app for **The Oven** restaurant (Mian Channu), built with Kotlin,
Jetpack Compose and Firebase. Customers browse the full real menu (90 items + deals),
build a cart, place delivery/pickup/dine-in orders, pay online or cash on delivery,
and track their orders live. Orders sync to Firestore, where the admin panel manages them.

## Features

- Full real menu with search, categories and orderable deals (from the restaurant flyers)
- Pizza customisation with size-based pricing (S/M/L) and extra toppings
- Persistent cart, login session and order history (works offline, syncs when online)
- Online payment (JazzCash/Easypaisa/bank transfer with screenshot) and COD
- Live order tracker — status updates pushed from the admin panel via Firestore
- Zoomable full-menu flyer viewer and deal banners on the home screen

## Build in Android Studio

1. Open **Android Studio** → **Open**.
2. Select the **`TheOven`** folder inside this repository (the nested project folder,
   not the repo root) and let Gradle sync finish.
3. **Build → Build App Bundle(s) / APK(s) → Build APK(s)**.
4. The APK is at `app/build/outputs/apk/debug/app-debug.apk` — copy it to your phone
   and install it.

Requirements: Android Studio with JDK 17 (the project uses Gradle 8.13, AGP 8.13.2,
`compileSdk`/`targetSdk` 36, `minSdk` 24).

## Get the APK from GitHub Actions

Every push to `main` builds the app automatically via `.github/workflows/build-apk.yml`
(also runnable manually from the Actions tab → "Build Android APK"):

1. Go to the repo's **Actions** tab → the latest "Build Android APK" run.
2. Download the **`the-oven-apk`** artifact (kept for 30 days).
3. Unzip it, install `app-debug.apk` on your phone.

## Firebase notes

- `app/google-services.json` is committed in this repository so the CI build can
  connect to Firebase without extra setup. It identifies the Firebase project
  (`the-oven-7d3ec`); it is not a secret credential — access is still controlled
  by Firebase Auth and Firestore security rules.
- The app uses Firebase Auth (email/password, with local demo/offline fallback),
  Firestore for orders and the live menu, and Firebase Cloud Messaging for order
  notifications.
- **Spark (free) plan limits:**
  - **No Storage uploads** — payment screenshots and menu/banner image uploads need
    the Blaze plan. Orders still save without the screenshot URL.
  - **No automatic push notifications** — Cloud Functions are not deployed on Spark,
    so server-triggered FCM pushes are not live (the in-app order tracker still
    updates in real time via Firestore listeners).
  - The app falls back to its bundled local menu and local order history when offline.

## Project layout

- `app/src/main/java/com/example/theoven/` — Kotlin sources (activities, Firebase
  repositories, FCM service)
- `app/src/main/res/` — layouts, drawables (incl. menu flyers), strings
- `app/src/main/assets/` — menu flyer images
- `app/google-services.json` — Firebase Android app config
