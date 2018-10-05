import Versions.anko_version
import Versions.kotlin_version
import Versions.objectboxVersion
import Versions.retrofit2Version
import Versions.supportLibraryVersion

/**
 * Created by yzbzz on 2018/6/4.
 */
object Versions {
    var gradle_version = "3.2.0"
    val kotlin_version = "1.2.71"
    val anko_version = "0.10.5"

    val compileSdkVersion = 28
    val buildToolsVersion = "27.0.3"
    val supportLibraryVersion = "27.1.0"

    val minSdkVersion = 19
    val targetSdkVersion = 28
    val versionCode = 1
    val versionName = "1.0"

    val lifecycleVersion = "1.1.1"

    val objectboxVersion = "2.1.0"

    val retrofit2Version = "2.4.0"
}

object Dependencies {

    val rxpermissions = "com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar"

    val rebound = "com.facebook.rebound:rebound:0.3.8"

    val mpandroidchart = "org.quanqi:mpandroidchart:1.7.5"

    val flexbox = "com.google.android:flexbox:0.3.2"

    val zxing_core = "com.google.zxing:core:3.3.3"
    val zxing_android_core = "com.google.zxing:android-core:3.3.0"


    val objectbox_android = "io.objectbox:objectbox-android:$objectboxVersion"
    val objectbox_kotlin = "io.objectbox:objectbox-kotlin:$objectboxVersion"
    val objectbox_processor = "io.objectbox:objectbox-processor:$objectboxVersion"

    val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:1.5.4"
    val leakcanary_android_no_op = "com.squareup.leakcanary:leakcanary-android-no-op:1.5.4"

    val glide = "com.github.bumptech.glide:glide:4.5.0"
    val glide_compiler = "com.github.bumptech.glide:compiler:4.5.0"

    val okhttp3 = "com.squareup.okhttp3:okhttp:3.11.0"
    val okhttp3Logging = "com.squareup.okhttp3:logging-interceptor:3.11.0"
    val okio = "com.squareup.okio:okio:2.0.0"

    val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofit2Version"
    val retrofit2_converter_gson = "com.squareup.retrofit2:converter-gson:$retrofit2Version"
    val retrofit2_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:$retrofit2Version"

    val retrofit2_converter_fastjson_android = "org.ligboy.retrofit2:converter-fastjson-android:2.1.0"


    val ultra_ptr = "in.srain.cube:ultra-ptr:1.0.11"

    val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.0"
    val logger = "com.orhanobut:logger:2.1.1"

    val kotlinx_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.5"

    val rxbinding2 = "com.jakewharton.rxbinding2:rxbinding-kotlin:2.1.1"

    val easypermissions = "pub.devrel:easypermissions:1.2.0"

    val aspectjrt = "org.aspectj:aspectjrt:1.8.10"

    val support_cardview = "com.android.support:cardview-v7:$supportLibraryVersion"
    val support_palette = "com.android.support:palette-v7:$supportLibraryVersion"


}

object CommonDependencies {

    val support_v4 = "com.android.support:support-v4:$supportLibraryVersion"
    val support_appcompat_v7 = "com.android.support:appcompat-v7:$supportLibraryVersion"

    val rxJava = "io.reactivex.rxjava2:rxjava:2.2.1"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.0"

    val support_recyclerview = "com.android.support:recyclerview-v7:$supportLibraryVersion"
    val support_design = "com.android.support:design:$supportLibraryVersion"

    val androidKtx = "androidx.core:core-ktx:0.3"

    val anko = "org.jetbrains.anko:anko:$anko_version"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

    val gson = "com.google.code.gson:gson:2.8.3"
    val fastjson_android = "com.alibaba:fastjson:1.1.68.android"

    val commonDependencies = mutableListOf(
            support_v4, support_appcompat_v7, rxJava, rxAndroid,
            support_recyclerview, support_design, androidKtx, anko, kotlin_stdlib,
            gson, fastjson_android)

}