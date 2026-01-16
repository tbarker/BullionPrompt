plugins {
    java
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.thomasbarker"
version = "2.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }
    }
    test {
        java {
            setSrcDirs(listOf("test"))
        }
        resources {
            setSrcDirs(listOf("test"))
        }
    }
}

dependencies {
    // Jakarta XML Binding (JAXB replacement)
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.1")
    implementation("jakarta.activation:jakarta.activation-api:2.1.2")
    runtimeOnly("org.glassfish.jaxb:jaxb-runtime:4.0.4")

    // CLI framework
    implementation("com.beust:jcommander:1.82")

    // Logging (SLF4J replaces commons-logging)
    implementation("org.slf4j:slf4j-api:2.0.9")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.9")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.shadowJar {
    archiveBaseName.set("bullionprompt")
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "com.thomasbarker.bullionprompt.cli.Main"
    }

    // Relocate dependencies to avoid conflicts (similar to JarJar)
    relocate("com.beust", "com.thomasbarker.bullionprompt.shaded.com.beust")
    relocate("org.slf4j", "com.thomasbarker.bullionprompt.shaded.org.slf4j")
}

tasks.named("build") {
    dependsOn(tasks.shadowJar)
}
