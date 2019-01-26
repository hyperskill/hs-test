# hs-test
A small test framework for simple testing of Hyperskill educational projects (built on top of JUnit).

## Requirements and build

It needs Java 11, Maven 3.3.9 or later (for building).

Build this project locally:
```
mvn clean package
```

## Using from educational projects

To access all classes from edu projects, you may use **jitpack**. It allows downloading and building all sources from here.

1) Add JitPack repo to your **gradle.build** file:

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
``` 

2) Add a dependency on **hs-test**:

```
dependencies {
    ...
    implementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
    ...
}
```

3) You may configure synchronization to automatically download and build the latest version of **hs-test** from GitHub:

```
configurations.all {
    resolutionStrategy.cacheChangingModulesFor 30, 'seconds'
}
```

An example:

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
