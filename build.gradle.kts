import java.nio.file.Files
import java.net.URLClassLoader

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
}

group = "com.rivancic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.google.guava:guava:30.1.1-jre")
    api("org.apache.tomcat.embed:tomcat-embed-core:9.0.37")
}

tasks.jar {
    from(configurations.runtimeClasspath.map { configuration ->
        configuration.asFileTree.fold(files().asFileTree) { collection, file ->
            if (file.isDirectory) collection else collection.plus(zipTree(file))
        }
    })
}

val packageToProcess: String by project

tasks.register("processClasses") {

    group = "process"
    description = "Process classes form a specific package"
    dependsOn(tasks.named("assemble"))
    doLast {

        // Instantiate classloader from jar as you will need other dependencies for loading classes
        val file: File = project.projectDir.toPath().resolve(tasks.jar.get().archiveFile.get().toString()).toFile()
        println("Packaged Kotlin project jar file: $file")
        val classloader: ClassLoader = URLClassLoader(arrayOf(file.toURI().toURL()))

        // Iterate through all of the class files in the specified package
        val packageToScan: String = packageToProcess.replace(".", File.separator)
        val path: java.nio.file.Path = project.file("build/classes/kotlin/main/").toPath().resolve(packageToScan)
        val classesFromSpecificPackage = Files.walk(path)
            .filter {
                Files.isRegularFile(it)
            }
            .map {
                project.file("build/classes/kotlin/main/").toPath().relativize(it)
            }
            .map { it.toString().substring(0, it.toString().lastIndexOf(".")).replace(File.separator, ".") }
            .map {
                //println("Class = $it")  // print class if necessary before loading it
                classloader.loadClass(it)
            }

        // Do something with the classes form specific package
        // Now just basic information is printed directly to the console
        println("=======================================")
        println("Classes from package:  $packageToScan")
        println("")
        classesFromSpecificPackage.forEach {
            println("  Class: ${it.canonicalName}")
            val fields = it.declaredFields
            for (element in fields) {
                println("  - Declared field: $element")
            }
            println("")
        }
        println("=======================================")
    }
}