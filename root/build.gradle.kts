plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"

    id("org.beryx.jlink") version "2.25.0"
}

group = "it.unicam.cs.giacomopessolano.formula1"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":app"))
    implementation(project(":ui"))
    implementation("org.openjfx:javafx-controls:10.0")
    implementation("org.openjfx:javafx-fxml:10.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}

application {
    mainClass.set("it.unicam.cs.giacomopessolano.formula1.main.Main")
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.graphics")
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
    test {
        java {
            srcDirs("src/test/java")
        }
        resources {
            srcDirs("src/test/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}