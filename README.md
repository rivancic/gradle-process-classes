# Process generated classes

POC of a Gradle project that processes compiled classes under specific package name.

It's a really simple Kotlin based project. With a Gradle build script containing long task named **processClasses**
That can read package name form gradle.properties with the key of **packageToProcess** and then processes all the classes under this package name.
Current logic only prints out basic class information. 

TODO

- Make logic customizable
- Logic is assuming classes are read from vanilla Java or Kotlin jar. Logic will have to be adapted if files are read form Spring Boot assembled jar or Andorid one.

