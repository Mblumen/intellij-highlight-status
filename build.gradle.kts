plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.3.0"
}

group = "de.hd"
version = "1.0.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
    gradlePluginPortal()
}


// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1.1.1")

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
      Initial version
    """.trimIndent()
    }

}

java {
    targetCompatibility = JavaVersion.VERSION_21
    sourceCompatibility = JavaVersion.VERSION_21
}


kotlin {
    jvmToolchain(21)
}

// add description
description = "Adds a status marker icon and/or a text behind the class name in the project view based on comment tags. E.g. // STATUS: DONE, // STATUS: TODO, // STATUS: INACTIVE, // STATUS: WIP. Also supports // ICON to show a custom icon. \n" +
        "The plugin is designed to be used in a team environment, where multiple developers are working on the same codebase. It helps to keep track of the status of each class and to communicate the progress of the work to other team members.\n" +
        "\n" +
        "The plugin is not intended to be used as a task management tool, but rather as a way to visualize the status of the code in the project view.\n" +
        "\n" +
        "If you want to exclude a specifc file from the status marker, you can add the comment // EXCLUDE FROM STATUS to the file. This will remove the status marker from the file in the project view.\n" +

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}
    patchPluginXml {
        changeNotes.set("First version, see description on how to use.")
    }
}
