# calendarapp
Technologies used for creation of calendarapp:
- Backend:
  - Java 17
  - Spring Boot 3 (with maven)
- Frontend
  - React
- Database
  - MongoDB
- git

In order to run application, you will have to clone the project first. After cloning the application, you can run it using the IDE or through terminal using maven commands.

Steps to run it using IDE (in our case, we used InteliJ for Backend, and Visual Studio Code for Frontend):
- start MongoDB by opening it's GUI (MongoDB Compass) or by running: 
    - `sudo service mongod start` (on Mac) through terminal
    - on Windows, find the MongoDB service in the Services tab in Task Manager and start the service
- Open the CalendarAppBE directory in your IDE
- Go to CalendarAppApplication main class and run it by clicking the run button
- After clicking the run button, backend should be up and running

If you want to run the backend from the terminal instead, navigate to the CalendarAppBE directory and run the following command:
```
mvn spring-boot:run 
```

For frontend part of the application, open Visual Studio Code
- After clicking the File -> Open folder, navigate to the calendarappfe, and open it
- Open the terminal inside of Visual Studio Code, and run those 2 commands in order: 
  - `npm install`
  - `npm start`
- After npm start, both parts of the application should be up and running
