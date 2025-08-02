# 🧪 Sensor Test App for Android 🚀  
_A real-time, interactive sensor monitoring app using Jetpack Compose & Android's Sensor Framework_

## 🌟 Overview

**Sensor Test App** is a sleek and modern Android application designed to showcase **real-time sensor data** using Android’s native **Sensor Framework** and Jetpack Compose for UI. Built as part of an academic project, this app highlights three key hardware sensors in Android devices:

- 🌞 Ambient **Light Sensor**
- 📶 **Proximity Sensor**
- 🧲 **Magnetometer** availability check

This isn't your average utility app—it's a polished demonstration of reactive programming, sensor integration, and elegant Material 3 UI design.

## 💡 Features

- Real-time **Light Level** monitoring with animated progress  
- Dynamic **Proximity** feedback visualization  
- Smart **Magnetometer detection**  
- Fully **Composable UI** with themed cards and gradient styling  
- Responsive layout optimized for modern Android devices  
- Clean, structured Kotlin code with Jetpack Compose best practices

## 🧠 Technical Stack

- **Language:** Kotlin  
- **UI Framework:** Jetpack Compose  
- **Architecture:** Declarative UI with `mutableStateOf` for reactivity  
- **Sensors Used:**  
  - `TYPE_LIGHT`  
  - `TYPE_PROXIMITY`  
  - `TYPE_MAGNETIC_FIELD` (availability only)

## 🚀 How It Works

1. App initializes the **SensorManager** and registers listeners for light and proximity sensors.
2. Sensor readings update `mutableState` variables.
3. The UI re-composes automatically via Jetpack Compose to reflect current values.
4. The app checks for **magnetometer presence** and displays a user-friendly message.

## ✅ Requirements

- Android Studio Giraffe or higher  
- Kotlin 1.9+  
- Minimum SDK 21  
- A physical device with supported sensors (recommended)

## 📌 Why This App?

Perfect for:

- Developers learning Android’s Sensor Framework  
- Students building interactive UI with Compose  
- Anyone interested in device-aware Android development
