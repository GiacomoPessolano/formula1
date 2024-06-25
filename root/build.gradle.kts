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
    implementation(project(":app"))
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