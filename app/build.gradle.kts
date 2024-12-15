import java.io.FileNotFoundException
import java.util.Properties
import java.io.File
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.0"
}

val localProperties = project.rootProject.file("local.properties")

val propertiesp = Properties()
propertiesp.load(localProperties.inputStream())
val apiKey = propertiesp.getProperty("API_KEY")


android {
    namespace = "com.example.weather"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weather"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField( "String", "API_KEY", "\"${apiKey}\"")
        packagingOptions {
            exclude ("META-INF/INDEX.LIST")
            exclude ("META-INF/io.netty.versions.properties")
        }
    }



    buildTypes {
        debug {
            buildConfigField("String", "BASE", "\"https://api.openweathermap.org/data/2.5/weather?\"")
            buildConfigField("String", "_URL", "\"&appid=${apiKey}&units=metric\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE", "\"https://api.openweathermap.org/data/2.5/weather?\"")
            buildConfigField("String", "_URL", "\"&appid=${apiKey}&units=metric\"")
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Ktor Core and Engine
    implementation(libs.ktor.client.core) // Core HTTP client
    implementation(libs.ktor.client.cio) // CIO engine for HTTP client

    // Ktor Serialization
    implementation(libs.ktor.client.serialization) // Legacy serialization
    implementation(libs.ktor.serialization.kotlinx.json) // Kotlinx JSON serialization

    // Ktor Server (if you’re building a backend with Ktor)
    implementation(libs.ktor.server.core) // Core server features
    implementation(libs.ktor.server.netty) // Netty engine for server
    implementation(libs.ktor.server.content.negotiation) // Content negotiation plugin

    // Ktor Testing (if needed)
    testImplementation(libs.ktor.server.tests)

    // Koin for Dependency Injection
    implementation(libs.koin.ktor) // Koin integration for Ktor
    implementation(libs.koin.logger.slf4j) // Koin logger with SLF4J

    // SLF4J Logging (required by Koin's logger)
    implementation(libs.logback.classic) // SLF4J implementation

    // Testing libraries (optional, for testing your application)
    testImplementation(libs.ktor.client.mock) // Mock HTTP client for tests

    implementation (libs.ktor.client.logging)
    implementation (libs.ktor.client.android)

    implementation (libs.ktor.client.content.negotiation)

    implementation (libs.play.services.location)
    implementation(libs.androidx.ui.text.google.fonts)


    implementation (libs.koin.core)
    implementation (libs.koin.android)
    implementation (libs.koin.androidx.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation (libs.androidx.datastore.preferences)
    implementation(libs.gson)







}