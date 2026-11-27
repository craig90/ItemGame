# Item Game

This is a simple game which allows for character creation, random item generation, and the ability to equip and compare the stats of different items.

The game has two different front-ends which both use a shared back-end. The back-end
is written in Java and there is a front-end desktop app which uses Java Swing. The Swing GUI  is not polished. There is a also a React web-based UI which looks nicer. Springboot is used to send API requests from the front-end to the Java back-end.

## React SpringBoot App

### Run instructions

Springboot Backend

cd to project root
./gradlew build
./gradlew bootRun

React Frontend
cd frontend
npm run dev

## Desktop App

### Run instructions
There are two options:

1. Run the project through the Launcher in VSCode.
Literally open the Launcher.java program and select run.

2. The project can also be run using gradlew.
cd to project root
./gradlew build
./gradlew desktop:run

### Credits

See [CREDITS.md](CREDITS.md) for third-party art attributions.
