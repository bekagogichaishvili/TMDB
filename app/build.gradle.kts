import org.jetbrains.kotlin.utils.addToStdlib.safeAs

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navsafeargs)
    kotlin("kapt")
}

android {
    namespace = "ge.gogichaishvili.tmdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "ge.gogichaishvili.tmdb"
        minSdk = 28
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // RecyclerView
    implementation(libs.recyclerview)

    // Kotlin Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Fragment KTX
    implementation(libs.fragment.ktx)

    // ViewModel and LiveData
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    runtimeOnly(libs.lifecycle.extensions)

    // Glide for image loading
    implementation(libs.glide)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.glide.compiler) // Glide annotation processor for code generation

    // Circular ImageView
    implementation(libs.circularimageview)

    // EncryptedSharedPreferences
    implementation(libs.security.crypto)

    // Koin
    implementation(libs.koin.core)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.koin.test.junit5)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    // OkHttp
    implementation(libs.okhttp) // OkHttp core
    implementation(libs.logging.interceptor) // Logging interceptor for OkHttp

    // Retrofit
    implementation(libs.retrofit) // Retrofit core
    implementation(libs.converter.gson) // Gson converter for Retrofit

    // Android Splash Screen
    implementation(libs.splashscreen)

    // required to avoid crash on Android 12 API 31
    implementation(libs.crash)

    // EncryptedSharedPreferences
    implementation(libs.preferences)

    //paging
    implementation(libs.paging.common.ktx)
    implementation(libs.paging.runtime.ktx)

    //Toasty
    implementation(libs.toasty)

    //Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

}