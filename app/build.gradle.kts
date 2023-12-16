plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "kenharpe.iu.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "kenharpe.iu.finalproject"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.navigation:navigation-fragment:2.7.4")

    implementation("androidx.annotation:annotation:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:17.0.1")

    implementation("androidx.camera:camera-core:1.1.0-alpha06")
    implementation("androidx.camera:camera-camera2:1.1.0-alpha06")
    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha06")
    implementation("androidx.camera:camera-view:1.0.0-alpha27")
}