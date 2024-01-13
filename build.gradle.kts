plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "kr.eme.invensave.InvenSave"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
}

// 빌드된 JAR 파일을 복사하는 작업 정의
val copyJarToDir by tasks.registering(Copy::class) {
    // 빌드된 JAR 파일 경로
    from(tasks.named<Jar>("jar").flatMap { it.archiveFile })

    // 복사할 대상 경로.
    into("D:\\minecraft\\1. 버킷 관련\\1.20.2 spigot\\plugins")

    // 파일 이름 변경.
    rename { "InvenSave.jar" }
}

// 빌드 작업이 완료된 후 JAR 파일 복사 작업 실행
tasks.named("build") {
    finalizedBy(copyJarToDir)
}