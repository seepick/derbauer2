import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val distributionPackageVersion = "1.0.0"

plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.0"
    id("org.jetbrains.compose") version "1.10.0"
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
    val detekt = "1.23.8"
    val jacoco = "0.8.14"
    val jlayer = "1.0.1"
    val koin = "4.0.2" // NO! 4.1.1 UnsatisfiedLinkError in combination with compose desktop
    val kotest = "6.1.1"
    val kotlinLogging = "7.0.13"
    val logback = "1.5.23"
    val mockk = "1.14.7"
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(compose.desktop.currentOs)
    listOf("compose", "compose-viewmodel").forEach {
        implementation("io.insert-koin:koin-$it:${Versions.koin}")
    }
    implementation("javazoom:jlayer:${Versions.jlayer}") // play mp3s
    implementation("io.github.oshai:kotlin-logging:${Versions.kotlinLogging}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
    // test
    listOf("assertions-core", "property", "runner-junit5", "extensions-koin").forEach {
        testImplementation("io.kotest:kotest-$it:${Versions.kotest}")
    }
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("io.insert-koin:koin-test:${Versions.koin}")
    // ui tests
    testImplementation(compose.desktop.uiTestJUnit4)
//    testImplementation("org.jetbrains.compose.ui:ui-test-junit4:1.10.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.vintage:junit-vintage-engine:6.0.2") // to run JUnit4 with JUnit5
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

compose.desktop {
    application {
        mainClass = "com.github.seepick.derbauer2.game.DerBauer2"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "DerBauer2"
            packageVersion = distributionPackageVersion
            modules("java.naming")

            macOS {
                appStore = false
                iconFile.set(project.file("src/main/distribution/icon.icns"))
            }
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
        xml.required = true // will be used by sonarqube
    }
}

detekt {
    toolVersion = Versions.detekt
    source.setFrom("src/main/kotlin", "src/test/kotlin")
    config.setFrom(project.rootDir.absolutePath + "/config/detekt.yml")
    parallel = true
    ignoreFailures = true // don't fail build, pass results to sonarqube which acts as quality gate enforcing 0 findings
}

sonar {
    properties {
        property("sonar.organization", "seepick")
        property("sonar.projectKey", "seepick_derbauer2")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.kotlin.detekt.reportPaths", "build/reports/detekt/detekt.xml")
    }
}

tasks.withType<DependencyUpdatesTask> {
    val rejectPatterns = listOf(
        ".*-ea.*", ".*RC", ".*rc.*", ".*M1", ".*check",
        ".*dev.*", ".*[Bb]eta.*", ".*[Aa]lpha.*", ".*SNAPSHOT.*",
    ).map { Regex(it) }
    rejectVersionIf {
        rejectPatterns.any {
            it.matches(candidate.version)
        }
    }
}
