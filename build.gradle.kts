import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val appVersion: String = (project.findProperty("app_version") as? String)?.takeIf { it.isNotBlank() } ?: "9.9.9"
logger.debug("Gradle appVersion=[$appVersion]")

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose.core)
    alias(libs.plugins.compose.kotlin)
    alias(libs.plugins.benManes)
    id("jacoco")
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.detekt)
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(compose.desktop.currentOs)
    implementation(libs.koin.compose)
    implementation(libs.koin.composeViewmodel)
    implementation(libs.jlayer) // play mp3s
    implementation(libs.logging.kotlin)
    implementation(libs.logging.logback)
    detektPlugins(libs.detektFormatting)
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
    testImplementation(libs.koin.test)
    testImplementation(libs.bundles.uiTests)
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
        jvmArgs += listOf("-Xmx512M", "--add-exports", "java.desktop/com.apple.eawt=ALL-UNNAMED")
        nativeDistributions {
            packageName = "DerBauer2"
            packageVersion = appVersion
            vendor = "DerBauer"
            copyright = "2000-whatever it's yours"
            modules("java.naming")
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            macOS {
                appStore = false
                iconFile.set(project.file("src/main/distribution/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/main/distribution/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/distribution/icon.png"))
                // will generate something like: `derbauer2_{APP_VERSION}-1_amd64.deb`
            }
        }
    }
}

configure<ProcessResources>("processResources") {
    from("src/main/resources") {
        include("app.properties")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filter<ReplaceTokens>(
            "tokens" to mapOf(
                "version" to appVersion,
            ),
        )
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
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
    toolVersion = libs.versions.detekt.get()
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
        ".*-ea.*", ".*RC.*", ".*rc.*", ".*M1", ".*check",
        ".*dev.*", ".*[Bb]eta.*", ".*[Aa]lpha.*", ".*SNAPSHOT.*",
    ).map { Regex(it) }
    rejectVersionIf {
        rejectPatterns.any {
            it.matches(candidate.version)
        }
    }
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}
