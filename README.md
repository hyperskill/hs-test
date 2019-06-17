# hs-test
It is a small framework that simplifies testing educational projects for Hyperskill. It is built on top of JUnit.

It is required to use for Hyperskill projects. Main features:
- black box testing (only need to know user's main method)
- multiple types of tests in a simple unified way (without stdin, with stdin, files, swing)
- generating learner-friendly feedback (filtering stack-traces, hints)

For the documentation you can go here:


## Requirements and build

The project needs Java 8, Gradle 5.0+.

Build it locally:
```
gradle build
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
    testImplementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
    ...
}
```

A better choice would be don't connect to the latest commit on master, but use a special commit in which everything is guaranteed to work (commit hash will be updated here on every stable release):

- `testImplementation 'com.github.hyperskill:hs-test:d8b18a9d3f5caea636fd374a8ff8fdda2f3ec0b9'`

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
        testImplementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 30, 'seconds'
    }

    ...
}
```

## Downloading

If you would not use Gradle, you may just download the sources in place them in your project with a test for a program.

## Examples

Examples are available within the test directory: https://github.com/hyperskill/hs-test/tree/master/src/test/java/examples.
