# Sonar Quality Gates Maven Plugin

## Description

This plugin will break the maven build if the project fails the quality gate. These checks should happen after analysis has been submitted to the server.

Recommend using [![https://github.com/adnovum/sonar-build-breaker](https://github.com/adnovum/sonar-build-breaker)], easier to integrate with sonarqube.

### Origin

Fork of https://github.com/slam-it/sonar-quality-gates-maven-plugin, added support for sonarqube 7.9.x, migrated package to com.hack23.maven released to maven central.

### Badges

[![license](https://img.shields.io/github/license/Hack23/sonar-quality-gates-maven-plugin.svg)](https://github.com/Hack23/sonar-quality-gates-maven-plugin/raw/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.hack23.maven/sonar-quality-gates-maven-plugin.svg)](http://mvnrepository.com/artifact/com.hack23.maven/sonar-quality-gates-maven-plugin)
[![Jenkins](https://img.shields.io/jenkins/s/https/www.hack23.com/jenkins/job/Hack23/job/sonar-quality-gates-maven-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-quality-gates-maven-plugin/job/master/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/www.hack23.com/jenkins/job/Hack23/job/sonar-quality-gates-maven-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-quality-gates-maven-plugin/job/master/lastCompletedBuild/testReport/)
[![SonarQube Coverage](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.maven%3Asonar-quality-gates-maven-plugin&metric=coverage)](https://www.hack23.com/sonar/component_measures/domain/Coverage?id=com.hack23.maven%3Asonar-quality-gates-maven-plugin)
[![Lines of Code](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.maven%3Asonar-quality-gates-maven-plugin&metric=ncloc)](https://www.hack23.com/sonar/dashboard?id=com.hack23.maven%3Asonar-quality-gates-maven-plugin)
[![SonarQube Tech Debt](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.maven%3Asonar-quality-gates-maven-plugin&metric=sqale_debt_ratio)](https://www.hack23.com/sonar/component_measures?id=com.hack23.maven%3Asonar-quality-gates-maven-plugin)
[![Quality Gate](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.maven%3Asonar-quality-gates-maven-plugin&metric=alert_status)](https://www.hack23.com/sonar/dashboard?id=com.hack23.maven%3Asonar-quality-gates-maven-plugin)
[![Black Duck Security Risk](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-quality-gates-maven-plugin/branches/master/badge-risk.svg)](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-quality-gates-maven-plugin/branches/master)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-quality-gates-maven-plugin.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FHack23%2Fsonar-quality-gates-maven-plugin?ref=badge_shield)

# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11 or higher](https://img.shields.io/badge/jdk-11-green.svg)]
[![JDK-12](https://img.shields.io/badge/jdk-12-orange.svg)]
[![JDK-13](https://img.shields.io/badge/jdk-13-orange.svg)]




### Usage

```bash
$ mvn sonar-quality-gates:inspect
```

### Configuration

* `sonarHostUrl`
 * The Sonar host url
 * Default: `http://localhost:9000`

*Note:* `sonarHostUrl` property is optional and will be inferred from the `sonar.host.url` property (in the pom.xml or as a property in a [settings.xml profile](#Example Sonar profile (settings.xml)) if not specified

### Example

```xml
<build>
  <plugins>
    <plugin>
      <groupId>com.hack23.maven</groupId>
      <artifactId>sonar-quality-gates-maven-plugin</artifactId>
      <version>1.0-SNAPSHOT</version>
      <!-- Optional configuration -->
      <configuration>
        <sonarHostUrl>SONAR-HOST-URL</sonarHostUrl>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### Example Sonar profile (settings.xml)

```xml
<profile>
  <id>sonar</id>
  <properties>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.jdbc.url>jdbc:postgresql://localhost/sonar</sonar.jdbc.url>
    <sonar.jdbc.username>sonar</sonar.jdbc.username>
    <sonar.jdbc.password>xxxxx</sonar.jdbc.password>
    <sonar.jdbc.driver>org.postgresql.Driver</sonar.jdbc.driver>
  </properties>
</profile>

<activeProfiles>
  <activeProfile>sonar</activeProfile>
</activeProfiles>
```
