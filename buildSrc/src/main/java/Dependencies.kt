object Versions {

    const val compileSdkVersion = 29

    const val minSdkVersion = 19
    const val targetSdkVersion = 29

    const val versionCode = 1
    const val versionName = "1.0"

    const val supportLibraryVersion = "1.1.0"
    const val materialVersion = "1.0.0"
    const val recyclerViewVersion = "1.1.0-rc01"
    const val cardViewVersion = "1.0.0"

    const val kotlinVersion = "1.3.60"
    const val coroutinesVersion = "1.3.0-M2"

    const val ktxVersion = "1.1.0"
    const val fragmentKtxVersion = "1.2.0-beta02"

    const val lifecycleVersion = "2.2.0-beta01"
    const val pagingVersion = "2.1.0"

    const val objectBox = "2.3.3"

    const val glideVersion = "4.9.0"
    const val retrofit = "2.6.1"
}

object Libs {
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"

    const val rxjava = "io.reactivex.rxjava2:rxjava:2.2.11"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0-beta05"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.1.0"
    const val okio = "com.squareup.okio:okio:2.3.0"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:3.14.1"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitAdapterRxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    const val smartRefreshLayout = "com.scwang.smartrefresh:SmartRefreshLayout:1.1.0"
}

object Deps {
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.supportLibraryVersion}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"

    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.ktxVersion}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtxVersion}"

    const val preference = "androidx.preference:preference:1.1.0"

    const val lifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val lifecycleLiveDataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"

    const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"


    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"

    const val paging = "androidx.paging:paging-runtime:${Versions.pagingVersion}"
    const val pagingRx = "androidx.paging:paging-rxjava2:${Versions.pagingVersion}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"


    const val gson = "com.google.code.gson:gson:2.8.5"

}