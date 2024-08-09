plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.rejomy"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Placeholder
    compileOnly("me.clip:placeholderapi:2.11.5")

    // Bukkit api
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // Custom Materials for support all MC versions
    implementation("com.github.cryptomorin:XSeries:11.0.0") { isTransitive = false }
}