![bowl-aquarium@480](http://imgur.com/AImb9E3.png)

Bowl Aquarium
=========

Bowl Aquarium is a fish farm online game project, written in Java.

Freshman project in class "Introduction of Computer Science". Last updated 11/28/2013.

How to play
-----------------------

- Register or login an user with a username and password.
- Click the `Shop` button to buy fishes, wallpaper, ...
- Feed fishes and click `State` button to see fishes' state.
- Sell fish and manage your aquarium!

Feature
-----------------------

### Fish ###

|FatOrange|SkinnyBlue|
|----|----|
|![fish1@130x100.png](http://imgur.com/09fMSTE.png)|![fish2@104x30.png](http://imgur.com/2Nmt3jX.png)|

### Database Connection ###

Save game progress to MySQL(MariaDB) database.

Use `lib/mysql-connector-java-5.1.28-bin.jar` to connect the database:

    // src/fp/Server.java
    public Server() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?user=" + db_user + "&password=" + db_password + "&autoReconnect=true"
            );
            createTable();

            System.out.println("Server database connected");
        } catch (ClassNotFoundException e) {
            ...

### User ###

Register or login an user with `User` table.

**Notice:** the password would not be hashed. Be careful!

    MariaDB [test]> SHOW COLUMNS FROM `User`;
    +-------------------+------------------+------+-----+---------+----------------+
    | Field             | Type             | Null | Key | Default | Extra          |
    +-------------------+------------------+------+-----+---------+----------------+
    | id                | int(10) unsigned | NO   | PRI | NULL    | auto_increment |
    | name              | varchar(20)      | NO   |     | NULL    |                |
    | passwd            | varchar(20)      | NO   |     | NULL    |                |
    | money             | int(10) unsigned | NO   |     | NULL    |                |
    | total_fish_number | int(10) unsigned | NO   |     | NULL    |                |
    | kind1_fish_number | int(10) unsigned | NO   |     | NULL    |                |
    | kind2_fish_number | int(10) unsigned | NO   |     | NULL    |                |
    +-------------------+------------------+------+-----+---------+----------------+

Dependency
-----------------------

JDK, MySQL(MariaDB)

    $ java -version

    java version "1.8.0_66"
    Java(TM) SE Runtime Environment (build 1.8.0_66-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.66-b17, mixed mode)

    $ mysql -u test -p

    Welcome to the MariaDB monitor.  Commands end with ; or \g.
    Your MariaDB connection id is {id}
    Server version: 10.1.9-MariaDB Homebrew
    ...

Build and run
-----------------------

### Configuration ###

The configurations of mysql database can be set below:

    // src/fp/Server.java
    ...
    private String hostname = "localhost";
    private int port = 3306;
    private String database = "bowl-aquarium";
    private String db_user = "test";
    private String db_password = "test";
    ...

### Compile ###

    javac -d bin -sourcepath src src/fp/*.java

### Run ###

    $ java -cp lib/mysql-connector-java-5.1.28-bin.jar:bin fp.Server # start server

    $ java -cp bin fp.Client # start client

### Create jar file ###

    $ jar cfem server.jar fp.Server Manifest.txt lib -C bin fp/Server.class -C bin fp/UserData.class
    $ jar cfe client.jar fp.Client images -C bin/ .

    $ jar tf server.jar # list table of contents for archive

    META-INF/
    META-INF/MANIFEST.MF
    lib/
    lib/mysql-connector-java-5.1.28-bin.jar
    fp/Server.class
    fp/UserData.class

    $ jar tf client.jar # list table of contents for archive

    META-INF/
    META-INF/MANIFEST.MF
    images/
    images/background/
    images/background/background.jpg
    ...
    fp/
    fp/Bowl.class
    fp/Client.class
    ...
    fp/UserData.class
    fp/UserVerification.class

Screenshot
-----------------------

![screenshot1](http://imgur.com/9etS8JT.png)

![screenshot2](http://imgur.com/ygisdn7.png)

![screenshot3](http://imgur.com/0bv8QMJ.png)

More details in [http://imgur.com/a/aABVK](http://imgur.com/a/aABVK)

Reference
-----------------------

[1]. Sergiy Kovalchuk, "[How to Compile and Run Java Code from a Command Line](http://www.sergiy.ca/how-to-compile-and-launch-java-code-from-command-line/)", 2011

[2]. StackOverFlow, "[Create jar file from command line](http://stackoverflow.com/questions/11243442/create-jar-file-from-command-line)", 2012

Credit
-----------------------

Character Graphics Credit: [Pigutou](https://www.facebook.com/pigutou/)
