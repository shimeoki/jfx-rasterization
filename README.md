# jfx-rasterization

A JavaFX shape rasterization library.

## Description

This repository contains two projects: the library itself and the "demo" app.

Main focus is the library project, which is located in the `lib` directory. It's
referred just as `jfx-rasterization` from now on.

The library defines a little framework and some classes for simple rasterization
for JavaFX.

## Notice

This project was done as a part of a university task, so it's archived for now.
I don't have the time and the desire to maintain/develop it right now.

The main issues are that the rasterization is very slow for resource-intensive
rendering, because it's done pixel by pixel. So, if you want to improve the
performance, it's the first recommended step. The source is available with a
permissive MIT license, so you are free in your actions.

## Prerequisites

- JDK 21+

The Java version probably can be downgraded, but it's not recommended.
Everything was tested on JDK 21.

The library depends on `javafx-graphics` module as an API (the consumer also
needs to use this module), but the library will download it automatically.

## Download

First of all, you need to get the repository itself.

You can download it from the GitHub using the "Code" button (and "Download ZIP"
afterwards) or just clone the repository, if you have `git` installed:

```sh
git clone https://github.com/shimeoki/jfx-rasterization
```

After downloading (and unzipping in the first case) the repository, go into the
root directory of the project. If you have used the `git clone` command, you can
do that with:

```sh
cd jfx-rasterization
```

## Build

As of version 1.0.0, the library supports only building (and publishing)
locally.

If you don't want to build, some versions of the library can be found at the
"Releases" page.

Build is platform independent, except for the Gradle wrapper. From now on, `gw`
is the alias for correct Gradle wrapper command for the platform.

Linux and MacOS:

```sh
./gradlew
```

Windows:

```powershell
.\gradlew.bat
```

The wrapper is located in the root directory, so all the commands should be done
from here.

### Runtime JAR

If you just want to get the runtime JAR file (no Javadoc and source files), you
can do the following:

```sh
gw :lib:jar
```

The JAR file is located at `lib/build/libs`.

### All JARs

If you want to get all the JARs (runtime, Javadoc and source files), use the
command:

```sh
gw :lib:build
```

All three JARs are located at `lib/build/libs`.

### Distributed archive

If you want to get the runtime JAR and the dependencies (JavaFX), you can use:

```sh
gw :lib:distZip
```

After this command, the ZIP file is created at `lib/build/distribution`.

Also, the ZIP is generated after the `build` command, alongside with the TAR
archive.

### Maven artifact

There is an option to get the library as a Maven artifact to the local
repository (`.m2` directory in the home directory):

```sh
gw :lib:publishToMavenLocal
```

The artifact includes all the JARs.

## Import

Depending on the IDE or build tool you are using and the way of the build, the
process of import can vary.

I, personally, don't use Maven or any IDE, so the following applies only for
Gradle. Additions to this section (and any other) are welcomed.

### Gradle

The examples are given with the Kotlin DSL. If the DSL is Groovy, syntax would
be different, but semantically it's the same.

Also, in the `dependencies` section, `implementation()` is used, but it can be
an `api()` function, based on the use case.

#### Maven artifact

The recommended way is the Maven artifact. It includes all the JARs, so Javadocs
are included for the usage with the LSPs. Also, it's the easiest and most
convenient approach.

After building the artifact and publishing it to the local repository, add the
following to the `build.gradle.kts`:

```kts
repositories {
    // other repositories

    mavenLocal()
}

dependencies {
    // other dependencies

    implementation("com.github.shimeoki:jfx-rasterization:1.0.0")
    // instead of 1.0.0 specify the built version
    // or any version in the local maven repository
}
```

#### Local JAR

If you don't need the Javadocs or don't want to use the Maven artifact variant,
you can just import the runtime JAR file.

After the build, copy (cut) the JAR to your project. For example, to `libs`
directory in the root.

Add to the `build.gradle.kts`:

```kts
dependencies {
    // other dependencies

    implementation(file("../libs/jfx-rasterization-1.0.0.jar"))
    // instead of 1.0.0 specify the built version (written in the filename)
}
```

As the path, valid relative path to the JAR with the respect to
`build.gradle.kts` file should be specified.

Instead of the `file()` call, `files()` can be used.

Also, with this approach you can import other JAR files, like the JavaFX from
the distributed archive.

## Usage

A couple of examples are listed in the Javadocs. However, as of version 1.0.0,
the main usage for the library is the triangle rasterization feature.

Most important classes for the user can be found in the `triangle` package. It's
the `Triangler` interface and a couple of implementations for the interface.

Example usage can be seen in the `demo` project's source code. Also, the
following example is included in the Javadoc:

```java
// any implementations can be used, look at the interfaces

final Triangler triangler = new DDATriangler();
final Canvas canvas; // javafx canvas to render on

final GraphicsContext ctx = canvas.getGraphicsContext2D();
final Triangle triangle = new StaticTriangle(
        new Point2f(0, 1),
        new Point2f(1, 0),
        new Point2f(1, 1));
final TriangleColorer colorer = new StaticMonotoneTriangleColorer();

triangler.draw(ctx, triangle, colorer);
```

## demo

This project is intended for internal testing. However, it can be used as a
showcase tool.

It has 3 modes:

- Static - grid of tiles with random triangles;
- Dynamic - canvas with the "DVD logo" moving vertices for the triangles;
- Interactive - triangles are drawn after 3 clicks on the canvas.

The project is in very early development stage, but if you want to check it out,
you can use the following command:

```sh
gw :demo:run
```

## Javadoc

Javadoc can be viewed as a separate website.

After the corresponding JAR build, unpack the JAR to any directory and open the
`index.html` file with your browser.
