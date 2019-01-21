object Versions {

    const val compileSdkVersion = 28

    const val minSdkVersion = 19
    const val targetSdkVersion = 28

    const val versionCode = 1
    const val versionName = "1.0"

    const val materialVersion = "1.0.0"

    const val kotlinVersion = "1.3.11"

    const val supportLibraryVersion = "1.0.2"

    const val coroutinesVersion = "1.1.0"

    const val lifecycleVersion = "2.0.0"
}

object Libs {
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val androidxKtx = "androidx.core:core-ktx:1.0.1"

    const val rxjava = "io.reactivex.rxjava2:rxjava:2.2.2"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.0"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
}

object Deps {
    const val androidxLegacySupportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.supportLibraryVersion}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.materialVersion}"

    const val gson = "com.google.code.gson:gson:2.8.5"
    const val fastjson = "com.alibaba:fastjson:1.1.68.android"
}