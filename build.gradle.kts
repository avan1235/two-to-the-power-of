buildscript {
    extra.apply{
        set("minSdkVersion", 26)
        set("targetSdkVersion", 27)
        set("composeVersion", "1.0.5")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    }
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}