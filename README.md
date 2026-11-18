# TradebotAI

Android starter project for a Quotex-oriented signal-analysis prototype.

## Included
- Android app built with Kotlin and Jetpack Compose
- Forex / OTC selection in the interface
- CALL / PUT / WAIT demo signal from manually entered closing prices
- GitHub Actions workflow that builds a debug APK and stores it as a workflow artifact

## Important
This version is **not connected** to Quotex, live Forex data, or OTC candle feeds. It does not execute trades. The signal rule is a simple demonstration, not a validated trading strategy. Do not use it to risk real money.

OTC data is generally proprietary to the platform. A genuine live integration requires an authorized data source and must comply with the platform's terms.

## Build APK in GitHub
1. Upload the complete contents of this project to the root of your GitHub repository.
2. Open the repository's **Actions** tab.
3. Select **Build TradebotAI APK** and run it, or push a commit to `main`.
4. Open the completed workflow run and download the `TradebotAI-debug-apk` artifact.
5. Extract the artifact ZIP to get `app-debug.apk`.

The workflow builds a debug APK for testing. A release APK should be signed securely before distribution.
