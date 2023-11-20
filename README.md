
[<img src="https://logos-world.net/wp-content/uploads/2022/07/Java-Logo.png" height="200" width="300">](https://www.java.com/en/)
<img src="https://1.bp.blogspot.com/-X6BEL-1Ax60/V7MiBPXhZKI/AAAAAAAABn0/hcdjPzQP7KY7wgvAdYhTg8J-jBWZTUUeQCPcB/s1600/javaServletJsp.png" height="200" width="300">
# STC Novel - Novel Reading and Sharing Website

## What's STC Novel?

Online content sharing has always been a noteworthy concern, encompassing various forms such as articles, comments, reports, and even stories. However, they all require a platform for writing, publishing, and reading others' narratives. As a result, we undertook the project "STC Novel - Novel Reading and Sharing Website" to establish a foundation where users can discover and publish novels, connecting individuals with shared interests and a passion for storytelling.

## Actors

  - Guess 👽 - anonymous user
  - Member 👨‍👩‍👦‍👦 - users who have logged into the system
  - Moderator 👮 - members granted approval authority, tasked with reviewing content posted by members
  - Admin 👑 - members with system control privileges

## Use Case Details
<img src="./general usecase.png">

## Setup and run 
```sh
Software and tool required
  - Git
  - Java JDK 17
  - Tomcat
  - Gradle
  - MySQL Server
  - MySQL Workbench
  - IDE (Intelij, Eclipse ...)
```

1. Clone the repository
```sh
git clone https://github.com/Sang-Tan/NovelWebsite
```
2. Initialize database
```sh
for %%G in (./src/main/resources/db/migration/createTable/*.sql) do sqlcmd /S servername /d databaseName -U username -P password -i"%%G"
sqlcmd /S servername /d databaseName -U username -P password -i ./src/main/resources/db/migration/V1__Create_foreigh_key.sql
```
3. Create .env file in root folder same as [.env.example](./.env.example) and config their properties as your wish

4. Install JAR packages
```sh
gradle build
```
5. Build war file
```sh
jar -cvf novelwebsite.war *  
```
6. Delploy war file by put war file from previous step to webapps/Root in your tomcat folder
7. Open http://localhost:yourhost
