plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "dam.a51560.doggalleryapp.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room
    api("androidx.room:room-runtime:2.6.1")
    api("androidx.room:room-ktx:2.6.1")
    // Note: If using Room compiler it needs KAPT, but let's check if the current project uses kapt for room. Currently app didn't have room-compiler defined. I will add it or leave it as it was if it compiled.

    // Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // UI states and viewmodels if we expose flow/livedata for viewmodels
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
}
