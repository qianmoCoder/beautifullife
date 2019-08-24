object Versions {

    const val compileSdkVersion = 28

    const val minSdkVersion = 19
    const val targetSdkVersion = 28

    const val versionCode = 1
    const val versionName = "1.0"

    const val materialVersion = "1.0.0"

    const val kotlinVersion = "1.3.41"

    const val supportLibraryVersion = "1.0.2"

    const val coroutinesVersion = "1.1.0"

    const val lifecycleVersion = "2.0.0"

    const val pagingVersion = "2.1.0"

    const val objectBox = "2.3.3"

    const val glideVersion = "4.9.0"

    const val retrofit = "2.6.1"
}

object Libs {
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val androidxKtx = "androidx.core:core-ktx:1.0.1"

    const val rxjava = "io.reactivex.rxjava2:rxjava:2.2.11"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val pagingRx = "androidx.paging:paging-rxjava2:${Versions.pagingVersion}"

    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0-beta05"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:glide:${Versions.glideVersion}"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.1.0"
    const val okio = "com.squareup.okio:okio:2.3.0"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:3.14.1"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson =  "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitAdapterRxjava =  "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
}

object Deps {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"

    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.supportLibraryVersion}"

    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.materialVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.materialVersion}"

    const val gson = "com.google.code.gson:gson:2.8.5"

}