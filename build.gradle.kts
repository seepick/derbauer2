import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val appVersion: String = (project.findProperty("appVersion") as? String)?.takeIf { it.isNotBlank() } ?: "9.9.9"
val debugTests = project.findProperty("debugTests") != null
val enableUiTests = project.findProperty("enableUiTests") != null
val failOnDetektIssue = project.findProperty("failOnDetektIssue") != null

val uiTestCategoryFqn = "com.github.seepick.derbauer2.game.testInfra.uitest.UiTestCategory"
val mainClassFqn = "com.github.seepick.derbauer2.game.DerBauer2"

logger.debug("DerBauer2 appVersion=[$appVersion]")

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
    implementation(libs.jlayer) // audio player
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
        mainClass = mainClassFqn
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
    useJUnitPlatform {
        excludeEngines("junit-vintage") // handled by the `uiTest` task
    }
    if (debugTests) {
        enableTestLogging()
    }
}

if (enableUiTests) {
    val uiTest by tasks.registering(Test::class) {
        logger.lifecycle("Registering UI test task")
        description = "Run Compose UI Tests with JUnit4"
        group = "verification"
        testClassesDirs = sourceSets["test"].output.classesDirs
        classpath = sourceSets["test"].runtimeClasspath
        useJUnit()
        if (debugTests) {
            enableTestLogging()
        }
    }
}

fun AbstractTestTask.enableTestLogging() {
    testLogging {
        events("failed", "skipped", "passed")
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
    }
    afterTest(KotlinClosure2<TestDescriptor, TestResult, Any>({ desc, result ->
        if (result.resultType == TestResult.ResultType.FAILURE) {
            println("FAILED: ${desc.className}.${desc.name} (${desc.displayName})")
            result.exception?.let {
                println(it.stackTraceToString())
            } ?: println("No exception available for failed test.")
        }
    }))
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

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = 0.6.toBigDecimal()
            }
        }
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    source.setFrom("src/main/kotlin", "src/test/kotlin")
    config.setFrom(project.rootDir.absolutePath + "/config/detekt.yml")
    parallel = true
    ignoreFailures = !failOnDetektIssue // ignore by default, only fail if explicitly set
}

sonar {
    properties {
        property("sonar.organization", "seepick")
        property("sonar.projectKey", "seepick_derbauer2")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.kotlin.detekt.reportPaths", "build/reports/detekt/detekt.xml")
        property("sonar.exclusions", "docs/index.html")
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
