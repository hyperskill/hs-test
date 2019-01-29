# hs-test
A small framework that simplifies testing educational projects for Hyperskill. It is built on top of JUnit.

It is recommended but not required to use it for HS projects. Main features: 
- multiple types of tests in a single unified way (without stdin, with stdin, files, swing)
- less boilerplate for stdin tests
- generating learner-friendly feedback (filtering stack-traces, hints)

But you may also use a standard test framework like JUnit or testNG.

## Requirements and build

The project needs Java 11, Maven 3.3.9 or later.

Build it locally:
```
mvn clean package
```

## Using in educational projects

To access all classes in educational projects, you may use **jitpack**. It allows downloading and building all sources from here. To start use it, follow these steps:

1) add JitPack repo to your **gradle.build** file:

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
``` 

2) add one dependency on **hs-test**:

```
dependencies {
    ...
    implementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
    ...
}
```

3) optionally, you may also configure synchronization to automatically get the latest version of **hs-test** from GitHub:

```
configurations.all {
    resolutionStrategy.cacheChangingModulesFor 30, 'seconds'
}
```

A fragment example of **gradle.build** file:

```
subprojects {
    ...
 
    repositories {
        mavenCentral()
        maven { url "https://jitpack.io" }
    }

    dependencies {
        implementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 30, 'seconds'
    }

    ...
}
```

## Examples

Examples are available within the test directory: https://github.com/hyperskill/hs-test/tree/master/src/test/java/org/hyperskill/hstest.
