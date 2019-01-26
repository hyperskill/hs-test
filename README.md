# hs-test
A small framework that simplifies testing educational projects for Hyperskill. It is built on top of JUnit.

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
