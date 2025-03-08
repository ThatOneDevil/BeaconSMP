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
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/net.minestom/minestom-snapshots

    implementation("net.minestom:minestom-snapshots:1_21-9219e96f76")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}