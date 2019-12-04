# Now on Heroku
Visit [our heroku app](https://thebayofpirates.herokuapp.com/ "Heroku - TheBayOfPirates") tocheck out our latest deployed version.

# TheBayOfPirates
TheBayOfPirates is a Spring Boot application. It will eventually function as a webpage that allows users to share torrents of legal files, have discussions about them and rate them.

## Who we are
This project is a colaborative effort of Ali Aslan & Muhammed Ali Geldi.

## Prerequisits
Make sure you have git installed to clone into this repository. Also make sure you have a command line (Mac/Linux, or Git Bash on Windows or similar).

## Installation
Clone the project, cd into the root of the project, and then run ./gradlew bootRun to boot and run the application.
```
git clone https://github.com/Rankhole/TheBayOfPirates.git
cd TheBayOfPirates
./gradlew bootRun
```

## Running tests
To run tests, simply use ./gradlew test to run unit- and integration tests, and npx cypress run all of the cypress tests.
```
./gradlew test
npx cypress run
```
