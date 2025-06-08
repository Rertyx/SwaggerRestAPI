plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("idea")
}

group = "ru.dimov"
version = "0.0.1-SNAPSHOT"

idea {
	module {
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-web:3.5.0")
	implementation("org.springframework.boot:spring-boot-devtools:3.5.0")

	// Lombok
	annotationProcessor("org.projectlombok:lombok:1.18.38")
	compileOnly("org.projectlombok:lombok")

	// PostgreSQL драйвер
	runtimeOnly("org.postgresql:postgresql")

	// liquibase
	implementation("org.liquibase:liquibase-core")

	// Swagger
	implementation("io.swagger.parser.v3:swagger-parser:2.1.16")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
