
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.text.SimpleDateFormat

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("io.objectbox")
}

android {

    signingConfigs {
        getByName("debug") {
            storeFile = file("$rootDir/debug.keystore")
        }
    }

    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        //app package name
        applicationId = "com.ddu"

        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)

        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                includeCompileClasspath = true
            }
        }
        multiDexEnabled = true
    }

    buildTypes {
        getByName("debug") {
            //debug模式
            manifestPlaceholders = mapOf("BUILD_TYPE_VALUE" to "deubg")
            signingConfig = signingConfigs.getByName("debug")
            versionNameSuffix = "(debug)"
//            applicationIdSuffix ".debug"
        }
        getByName("release") {
            //是否进行混淆
            isMinifyEnabled = false
            //Zipalign优化
            isZipAlignEnabled = true
            //混淆文件位置
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders = mapOf("BUILD_TYPE_VALUE" to "release")
        }

        create("qa") {
            initWith(getByName("release"))
            manifestPlaceholders.replace("BUILD_TYPE_VALUE", "qa")
            matchingFallbacks = arrayListOf("release")
        }
    }

    applicationVariants.all {
        outputs.all {
            val buildName = buildType.name
            val formattedDate = SimpleDateFormat("yyyy.MM.dd.HH.mm")
            if (this is ApkVariantOutputImpl) {
                this.outputFileName = "app-$buildName-${defaultConfig.versionName}-$formattedDate.apk"
            }
        }
    }

    //移除lint检查的error
    lintOptions {
        isAbortOnError = false
    }

    dataBinding {
        isEnabled = true
    }

    packagingOptions {
        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
    productFlavors {
    }


    configurations.all {
        resolutionStrategy.force("androidx.vectordrawable:vectordrawable:1.0.1")
        resolutionStrategy.force("androidx.appcompat:appcompat:1.0.2")

//        resolutionStrategy {
//            // 3 显示版本冲突
//            failOnVersionConflict()
//        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":icore"))
    implementation(project(":iannotation"))
    kapt(project(":iprocessors"))

    implementation("com.facebook.rebound:rebound:0.3.8")

    implementation("com.google.zxing:core:3.3.3")
    implementation("com.google.zxing:android-core:3.3.0")

    implementation("io.objectbox:objectbox-android:${Versions.objectBox}")
    implementation("io.objectbox:objectbox-kotlin:${Versions.objectBox}")
    kapt("io.objectbox:objectbox-processor:${Versions.objectBox}")
    annotationProcessor("io.objectbox:objectbox-processor:${Versions.objectBox}")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:1.6.1")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:1.6.1")

    implementation("com.github.bumptech.glide:glide:4.8.0")
    implementation("com.github.bumptech.glide:compiler:4.8.0")
    kapt("com.github.bumptech.glide:compiler:4.8.0")

    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
    implementation("com.squareup.okio:okio:2.2.2")

    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.palette:palette:1.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")

    implementation(Libs.constraintlayout)
    implementation("com.orhanobut:logger:2.1.1")

    implementation("pub.devrel:easypermissions:2.0.0")

    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    implementation(Libs.lifecycle)

    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1")

}
