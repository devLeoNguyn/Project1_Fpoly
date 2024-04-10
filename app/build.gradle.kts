plugins {
    id("com.android.application")

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.project1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project1"
        minSdk = 33
        targetSdk = 34
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
        viewBinding{
            enable = true;
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//settingFirebase
}

dependencies {
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    // Import Glide&Picasso
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")




}