plugins {
    kotlin("jvm") version "2.0.0"

}

group = "me.thatonedevil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    //implementation("net.minestom:minestom-snapshots:1_21-9219e96f76")
    implementation("net.minestom:minestom-snapshots:1_21_4-6490538291")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("com.google.code.gson:gson:2.12.1")

}

kotlin {
    jvmToolchain(21)
}