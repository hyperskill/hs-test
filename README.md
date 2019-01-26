# hs-test
A small test framework for simple testing of Hyperskill educational projects (built on top of JUnit).

## Requirements and build

It needs Java 11, Maven 3.3.9 or later (for building).

Build command:
```
mvn clean package
```

## Using from projects

To access it from projects, we use **jitpack** plugin to download and build all sources here.

To start use it from projects:

1) Add JitPack repo **build.gradle** to your **gradle.build** file:

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
``` 

2) Add the dependency:

```
dependencies {
    implementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
}
```

3) Configure synchronization (if you need), to automatically get the latest version of **hstest** from GitHub:

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
        ...
        implementation 'com.github.hyperskill:hs-test:master-SNAPSHOT'
	...
    }

    configurations.all {
        resolutionStrategy.cacheChangingModulesFor 30, 'seconds'
    }
    
    ...
}
```
