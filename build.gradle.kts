import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val dmgPackageVersion = "1.0.0"

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
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

compose.desktop {
    application {
        mainClass = "com.github.seepick.derbauer2.Main"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "derbauer2"
            packageVersion = dmgPackageVersion
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