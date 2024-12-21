plugins {
    id("jfx-rasterization.java-conventions")
    `kotlin-dsl`

    id("base")
    id("java-library")
    id("java-library-distribution")

    id("io.deepmedia.tools.deployer") version "0.15.0"
}

group = "io.github.shimeoki.jfx"
version = "2.0.1"

base {
    archivesName = rootProject.name
}

java {
    withJavadocJar()
    withSourcesJar()
}

javafx {
    modules("javafx.graphics")
    configuration = "api"
}

distributions {
    main {
        distributionBaseName = rootProject.name
    }
}

deployer {
    content {
        component {
            fromJava()
        }
    }

    projectInfo {
        name.set(rootProject.name)
        description.set("A JavaFX shape rasterization library.")
        url.set("https://github.com/shimeoki/jfx-rasterization")

        artifactId.set("rasterization")

        scm {
            fromGithub("shimeoki", "jfx-rasterization")
        }

        license(MIT)

        developer("shimeoki", "shimeoki@gmail.com")
    }

    localSpec("m2") {
    }

    localSpec("artifact") {
        directory.set(file("build/artifact"))
    }

    centralPortalSpec {
        auth.user.set(secret("CENTRAL_PORTAL_USERNAME"))
        auth.password.set(secret("CENTRAL_PORTAL_PASSWORD"))

        signing {
            key.set(secret("GPG_KEY"))
            password.set(secret("GPG_PWD"))
        }
    }

    githubSpec {
        owner.set("shimeoki")
        repository.set("jfx-rasterization")

        auth.user.set(secret("GITHUB_ACTOR"))
        auth.token.set(secret("GITHUB_TOKEN"))
    }
}
