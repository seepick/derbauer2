import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask


plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    id("com.github.ben-manes.versions") version "0.53.0"
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
//    implementation(compose.components.resources)
//    implementation(compose.material3)
//    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4") // NO! 2.9.6 UnsatisfiedLinkError
//    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2") // when "Module with the Main dispatcher is missing"
    implementation("io.arrow-kt:arrow-core:1.2.0")

    // DEPENDENCY INJECTION - https://insert-koin.io/docs/reference/koin-compose/compose
    val versionKoin = "4.0.2" // NO! 4.1.1 UnsatisfiedLinkError
    listOf("compose", "compose-viewmodel").forEach {
        implementation("io.insert-koin:koin-$it:$versionKoin")
    }

    // LOGGING
    implementation("io.github.oshai:kotlin-logging:7.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.23")

    // TEST
    listOf("assertions-core", "property", "runner-junit5", "extensions-koin").forEach {
        testImplementation("io.kotest:kotest-$it:6.1.1")
    }
    testImplementation("io.mockk:mockk:1.14.7")
    testImplementation("io.insert-koin:koin-test:$versionKoin")
}


kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "com.github.seepick.derbauer2.Main"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
//                 org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
//                 org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "derbauer2"
            packageVersion = "1.0.0"
        }
    }
}

tasks.withType<Test>().configureEach { // to be able to run kotests
    useJUnitPlatform()
}

tasks.withType<DependencyUpdatesTask> {
    val rejectPatterns =
        listOf(
            ".*-ea.*", ".*RC", ".*rc.*", ".*M1", ".*check",
            ".*dev.*", ".*[Bb]eta.*", ".*[Aa]lpha.*", ".*SNAPSHOT.*",
        ).map { Regex(it) }
    rejectVersionIf {
        rejectPatterns.any {
            it.matches(candidate.version)
        }
    }
}