
# Study Room Booking

A Java application for managing study room bookings.

## Requirements

- Java JDK
- The JUnit standalone JAR file named `junit.jar`

Do not commit `junit.jar` to the repository.

## Compile the project

Run this command from the project root:

```cmd
javac -d out -cp junit.jar src/main/java/*.java src/test/java/*.java
```

## Run the tests

Run this command from the project root:

```cmd
java -jar junit.jar execute --class-path out --scan-class-path
```

## Main functionality

- Validate that a room exists before booking.
- Reject bookings that start in the past.
- Enforce a maximum booking duration of three hours.
- Prevent overlapping bookings for the same room.
- Prevent a user from making overlapping bookings across different rooms.
- Allow users to cancel their own bookings.
- Return a user's bookings in chronological order.

## Design

The application uses repository interfaces to separate booking logic from data storage. In-memory repositories are provided for storing rooms and bookings during execution.

`BookingService` receives a `Clock` through its constructor so that time-dependent behaviour can be tested predictably.
