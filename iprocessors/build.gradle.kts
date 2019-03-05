plugins {
    java
}

dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("com.google.auto.service:auto-service:1.0-rc4")
    implementation ("com.squareup:javapoet:1.11.1")
    implementation (project(":iannotation"))
}
