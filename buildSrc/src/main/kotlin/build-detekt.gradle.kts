// ATTENTION!!! duplicate version number in buildSrc/build.gradle.kts
val detektVersion = "1.23.8"

plugins {
//    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    // more plugins here: https://detekt.dev/marketplace/
//    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

//detekt {
//    toolVersion = detektVersion
//    source.setFrom("src/main/kotlin", "src/test/kotlin")
//    parallel = true
//    config.setFrom(project.rootDir.absolutePath + "src/main/config/detekt.yml")
////    debug = true
//    ignoreFailures = false
////    failOnSeverity = dev.detekt.gradle.extensions.FailOnSeverity.Error
//    // Specify the base path for file paths in the formatted reports.
//    // If not set, all file paths reported will be absolute file path.
////    basePath.set(projectDir)
//}

//tasks.withType<Detekt>().configureEach {
//    reports {
//        html.required.set(true)
//        sarif.required.set(false)
//        md.required.set(false)
//        txt.required.set(false)
//        xml.required.set(false)
//        // checkstyle.required.set(true) // starting with v2.0.0
//    }
//}

//tasks.withType<Detekt>().configureEach {
    // TODO set JDK for type resolution to be enabled (more rules)
    // or actually should be done, as it is a jvm/kotlin project... but rules seem not to be active?!
//    jvmTarget.set("1.8")
//    jvmTarget = "1.8"
//    jdkHome.set(file("path/to/jdkHome"))
//    // include("**/special/package/**") // only analyze a sub package inside src/main/kotlin
//    exclude("**/special/package/internal/**") // but exclude our legacy internal package
//}
