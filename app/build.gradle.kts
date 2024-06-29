plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "it.unicam.cs.giacomopessolano.formula1"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-controls:10.0")
    implementation("org.openjfx:javafx-fxml:10.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
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

tasks.processTestResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}