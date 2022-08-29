import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.bloodyhan"
version = "5.0.1"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation("com.zaxxer:HikariCP:4.0.3")
    compileOnly("org.projectlombok:lombok:1.18.22")
    compileOnly("me.clip:placeholderapi:2.10.10")

    annotationProcessor("org.projectlombok:lombok:1.18.22")
    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks {

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    val fatJar by named("shadowJar", ShadowJar::class) {
        archiveFileName.set("${project.name}-${project.version}.jar")

        dependencies {
            exclude(dependency("org.slf4j:.*"))
        }

        relocate("com.zaxxer", "me.bloodyhan.libs.com.zaxxer")
        minimize()
    }

    artifacts {
        add("archives", fatJar)
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    build {
        dependsOn(shadowJar)
    }

}