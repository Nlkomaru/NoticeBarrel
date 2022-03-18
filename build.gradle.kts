/*
 * Copyright © 2021 Nikomaru
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

plugins {
    id("java")
    id("eclipse")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.0.1"
    kotlin("jvm") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.papermc.paperweight.userdev") version "1.3.4"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}
group = "com.notice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.onarandombox.com/content/groups/public/")
    maven("https://repo.minebench.de/")
    maven("https://repo.incendo.org/content/repositories/snapshots")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    compileOnly("org.maxgamer:QuickShop:5.1.0.5")
    compileOnly("com.github.TechFortress:GriefPrevention:16.18-RC1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:1.5.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:1.5.0")
    implementation("cloud.commandframework:cloud-core:1.6.1")
    implementation("cloud.commandframework:cloud-kotlin-extensions:1.6.1")
    implementation("cloud.commandframework:cloud-paper:1.6.1")
    implementation("cloud.commandframework:cloud-annotations:1.6.1")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("org.spongepowered:configurate-extra-kotlin:4.1.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.javaParameters = true
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks {
    runServer {
        minecraftVersion("1.18.1")
    }
}
