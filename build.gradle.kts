import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val dmgPackageVersion = "1.0.0"

plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    id("com.github.ben-manes.versions") version "0.53.0"
    id("jacoco")
    id("org.sonarqube") version "7.2.2.6593"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

@Suppress("MayBeConstant")
object Versions {
    val kotlinLogging = "7.0.13"
    val logback = "1.5.23"
    val kotest = "6.1.1"
    val koin = "4.0.2" // NO! 4.1.1 UnsatisfiedLinkError
    val mockk = "1.14.7"
    val jacoco = "0.8.14"
    val detekt = "1.23.8"
}

dependencies {
    implementation(compose.desktop.currentOs)
    listOf("compose", "compose-viewmodel").forEach {
        implementation("io.insert-koin:koin-$it:${Versions.koin}")
    }
    implementation("io.github.oshai:kotlin-logging:${Versions.kotlinLogging}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    // TEST
    listOf("assertions-core", "property", "runner-junit5", "extensions-koin").forEach {
        testImplementation("io.kotest:kotest-$it:${Versions.kotest}")
    }
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("io.insert-koin:koin-test:${Versions.koin}")

    // DETEKT
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

jacoco {
    toolVersion = Versions.jacoco
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        html.required = false
        csv.required = false
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.6".toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn(tasks.named("jacocoTestCoverageVerification"))
}

detekt {
    toolVersion = Versions.detekt
    source.setFrom("src/main/kotlin", "src/test/kotlin")
    config.setFrom(project.rootDir.absolutePath + "/src/main/config/detekt.yml")
    parallel = true
    ignoreFailures = false
}

sonar {
    properties {
        property("sonar.organization", "seepick")
        property("sonar.projectKey", "derbauer2")
        property("sonar.host.url", "https://sonarcloud.io")
    }
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