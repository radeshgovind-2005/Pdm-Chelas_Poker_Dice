plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "isel.pdm.pokerdice"
    compileSdk = 36

    defaultConfig {
        applicationId = "isel.pdm.pokerdice"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)
    implementation(libs.okhttp)
    implementation(libs.okhttp.sse)
    implementation(libs.gson)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.lifecycle.process)

    // Test dependencies
    testImplementation(libs.junit)  // JUnit for unit testing
    testImplementation(libs.mockito.kotlin)
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation(libs.kotlinx.coroutines.test.v150)  // Coroutines test dependency
    testImplementation(libs.kotlinx.coroutines.core)  // Core coroutines library for tests

    // Android test dependencies
    androidTestImplementation(libs.androidx.junit)  // Android JUnit
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom))  // Compose BOM for Android tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)  // JUnit4 for Compose UI testing
    debugImplementation(libs.androidx.compose.ui.tooling)  // Compose tooling for debugging
    debugImplementation(libs.androidx.compose.ui.test.manifest)  // Manifest for Compose UI tests
}
