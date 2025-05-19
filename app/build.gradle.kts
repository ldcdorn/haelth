plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.github.ldcdorn.haelth"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.ldcdorn.haelth"
        minSdk = 24
        targetSdk = 35
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
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0" // Match with your Compose version
    }
    buildToolsVersion = "35.0.0"
}

dependencies {
    // AndroidX Core und Lifecycle
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose Core
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation(libs.androidx.compiler)

    // Test-Abhängigkeiten
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM auch hier aktiv
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")


    // Debugging
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:32.0.1-jre")
        force("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    }
}

