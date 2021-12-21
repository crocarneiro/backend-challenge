# Back-end Challenge 🏅 2021 - Space Flight News

This application is an API for updating and consuming a database with articles about space flight news. It syncs every day with the [Space Flight API](https://spaceflightnewsapi.net/), but it's possible to add, update and remove articles.

> This is a challenge by [Coodesh](https://coodesh.com/)

## Tech Stack
![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![](https://img.shields.io/badge/Heroku-430098?style=for-the-badge&logo=heroku&logoColor=white)

## Demo
You can test the API in our [online Swagger UI](https://crocarneiro-backend-challenge.herokuapp.com/swagger-ui.html).

## Running the application with Docker Compose
Open a terminal session, navigate to the Docker folder in this directory and run the following command:
```sh
docker-compose up
```

That's it. The API is now accessible through the URL `localhost:8080/`.

## Running the application manually
You can download the jar file from one of the releases: [https://github.com/crocarneiro/backend-challenge/releases](https://github.com/crocarneiro/backend-challenge/releases)

Or you can build the application yourself.

### Building
To build the application just make sure you have a distribution of the JDK 17 and the latest Maven version installed, then open the root folder of the repository in your terminal and run:

```sh
mvn package
```

### Running
Once you have a valid build of the the application you can run it with the following command:

```sh
java -jar backend_challenge-0.0.1-SNAPSHOT.jar
```

This application works with two profiles: "production" and "dev". The default profile is "production", so before running the application you have to set the environment variable "MONGO_DATABASE_URL" with the URL connection of your MongoDB instance.

**Linux:**
```sh
export MONGO_DATABASE_URL={your_URL_here}
```

**Windows:**
```sh
set MONGO_DATABASE_URL={your_URL_here}
```

To run the application in a development environment run the following command:
```sh
java -jar -Dspring.profiles.active=dev backend_challenge-0.0.1-SNAPSHOT.jar # You may change the jar name according to the version
```

If you have the env variable `MONGO_DATABASE_URL` set, then the application will connect to this database, otherwise it will start a Mongo Container by itself and use it.

So the options are:
1. You set up a Mongo DB instance by yourself: You **have to set** the var `MONGO_DATABASE_URL`.
2. You don't want to set up a instance by yourself: **Do not set** the var `MONGO_DATABASE_URL`, but you have to have Docker installed and running in your machine.
3. In production you do not have options: You **have to set** the var `MONGO_DATABASE_URL`.