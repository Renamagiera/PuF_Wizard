# PuF_Wizard

## Inhaltsverzeichnis
1. [Allgemeine Info](#allgemeine-info)
2. [Technologien](#technologien)
3. [Installation](#installation)

## Allgemeine Info
<a name="allgemeine-info"></a>


## Technologien
<a name="technologien"></a>
Die Anwendung haben wir in der Programmiersprache Java in der Version 19 entwickelt. Als JDK haben wir uns für das 
[Oracle OpenJDK version 19.0.1](https://jdk.java.net/19/) entschieden. Zur Erstellung der Java-App wurde das JavaFX-Framework, 
ebenfalls in der Version 19 verwendet.

## Installation
<a name="installation"></a>
Für eine einfache Installation des Projektes haben wir eine Schritt-für-Schritt Anleitung verfasst.\
Da wir das Projekt mit der IDE IntelliJ erstellt und weiterentwickelt haben empfehlen wir diese zu benutzten. Sie können das Repository clonen und 
in Ihrer IDE als Projekt erstellen. Dafür klicken Sie auf den grünen Button "Clone" und erstellen in Ihrere IDE ein Projekt aus einer Versionskontrolle.
Bei der IDE IntelliJ gibt es dafür den Button "Get from VCS".\
Für die Ausführung des Programms wird eine MySQL-Instanz benötigt. Wir empfehlen dafür "XAMPP". Die URL, Nutzername und Passwort müssen mit dem übereinstimmen, 
was in der Datei application.properties steht. 

Nutzername:   **root**\
**kein Passwort**

### 1. GitHub Repo clone IntelliJ (Unlimited Version)
1. Neues Projekt in IntelliJ öffnen "File > New > Project from Version Control"
2. Die GitHub URL klonen und eingeben
3. Projekt erstellen
4. Spring Application starten\
              1. [xampp](https://www.apachefriends.org/de/download.html) herunterladen & installieren\
              2. Die Module "Apache" & "MySQL" starten\
              3. Terminal öffnen und in den Ordner des Repos navigieren oder in IntelliJ Terminal öffnen\
              4. Server starten mit dem Befehl **./mvnw spring-boot:run**\
              5. sollte es hier zu einem JAVA_HOME not found Error kommen: File -> Settings -> Tools -> Terminal Environment Variable eintragen: 
              "JAVA_HOME=**JDK-Pfad**"
