#!/bin/bash

# Build script for Kalamz APK
echo "🎮 Building Kalamz APK..."

# Set Android SDK path
export ANDROID_HOME=/Users/navan/Library/Android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# Navigate to project directory
cd /Users/navan/AndroidStudioProjects/Kalamz

echo "🔧 Cleaning previous builds..."
./gradlew clean

echo "🔨 Building debug APK..."
./gradlew assembleDebug

echo "📱 APK will be located at:"
echo "app/build/outputs/apk/debug/app-debug.apk"

echo "✅ Build complete!"
