
# STC Novel - Novel Reading and Sharing Website

## What's STC Novel?

Online content sharing has always been a noteworthy concern, encompassing various forms such as articles, comments, reports, and even stories. However, they all require a platform for writing, publishing, and reading others' narratives. As a result, we undertook the project "STC Novel - Novel Reading and Sharing Website" to establish a foundation where users can discover and publish novels, connecting individuals with shared interests and a passion for storytelling.
## Technologies Used
[![java][java-badge]](https://www.java.com)
![jsp-servlet][jsp-servlet-badge]
[![apache-tomcat][apache-tomcat-badge]](https://tomcat.apache.org/)
[![gradle][gradle-badge]](https://gradle.org/)

[![bootstrap][bootstrap-badge]](https://getbootstrap.com)
[![javascript][javascript-badge]](https://www.javascript.com)
[![html][html-badge]](https://html.com)

## Actors

  - Guess 👽 - anonymous user
  - Member 👨‍👩‍👦‍👦 - users who have logged into the system
  - Moderator 👮 - members granted approval authority, tasked with reviewing content posted by members
  - Admin 👑 - members with system control privileges

## Use Case Details
<img src="./general usecase.png">

## Usage
### Prerequisites
Before you begin, ensure you have installed:
1. Git 
2. JDK/JRE 17
3. Tomcat 9.x
4. Gradle
5. MySQL Server
### Running

1. Clone the repository
    ```sh
    git clone https://github.com/Sang-Tan/NovelWebsite
    ```
2. Create a database in MySQL
3. Run migration scripts in [migration](./src/main/resources/db/migration/createTable) folder
4. Rename [application.properties.example](./src/main/resources/application.example.properties) to application.properties and fill in required fields
5. Build the project
    ```sh
    gradle build
    ```
6. Deploy the war file in [build/libs](./build/libs) to Tomcat
7. Now the website is good to go :3

<!-- Badges -->
[java-badge]: https://img.shields.io/badge/JAVA-ff0000?style=for-the-badge
[jsp-servlet-badge]: https://img.shields.io/badge/JSP_SERVLET-ff0000?style=for-the-badge
[apache-tomcat-badge]: https://img.shields.io/badge/APACHE_TOMCAT-f8dc75?style=for-the-badge&logo=apachetomcat&logoColor=black
[gradle-badge]: https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=gradle&logoColor=white
[bootstrap-badge]: https://img.shields.io/badge/BOOTSTRAP-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[html-badge]: https://img.shields.io/badge/HTML-ff0000?style=for-the-badge&logo=html5&logoColor=white
[javascript-badge]: https://img.shields.io/badge/JAVASCRIPT-f7df1e?style=for-the-badge&logo=javascript&logoColor=black