buildscript {
    extra.apply{
        set("minSdkVersion", 26)
        set("targetSdkVersion", 27)
        set("composeVersion", "1.1.0-rc03")
    }
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}
