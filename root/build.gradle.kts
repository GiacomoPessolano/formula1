plugins {
    id("java")
    id("application")
}

group = "it.unicam.cs.giacomopessolano.formula1"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "it.unicam.cs.giacomopessolano.formula1.root.main.Main"
}