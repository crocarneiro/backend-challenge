## Building the application
To build the application just make sure you have a distribution of the JDK 17, open the root folder of the repository in your terminal and run:

```
mvn package
```

## Running the application
Once you have built the application you can run the resulting jar with the following command:

```sh
java -jar backend_challenge-0.0.1-SNAPSHOT.jar
```

This application works with two profiles: "production" and "dev". The default profile is "production", so before running the application you have to set the environment variable "MONGO_DATABASE_URL" with the URL connection of your MongoDB instance.

**Linux:**
```
export MONGO_DATABASE_URL={your_URL_here}
```

**Windows:**
```
set MONGO_DATABASE_URL={your_URL_here}
```

To run the application in a development environment run the following command:
```
java -jar -Dspring.profiles.active=dev backend_challenge-0.0.1-SNAPSHOT.jar
```

If you have the env variable MONGO_DATABASE_URL set, then the application will connect to this database, otherwise it will starts a Mongo Container by itself and use it.

So the options are:
1. You set up a Mongo DB instance by yourself: You have to set the var MONGO_DATABASE_URL.
2. You don't want to set up a instance by yourself: Do not set the var MONGO_DATABASE_URL, but you have to have Docker installed and running in your machine.
3. In production you do not have options: You have to set the var MONGO_DATABASE_URL.