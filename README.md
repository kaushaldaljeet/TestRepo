# TicketService

Implementation of simple ticket service that temporarily finds and holds seats for a customer and confirms the reservation of the seats for customer.

The application is developed using Java, Maven and IntelliJ IDE.

# Assumptions
1. Seats are stored in a 2D matrix form and the capacity of the venue i.e. the number of rows and columns are configurable.
2. Every seat has a row number, column number and status i.e. available, hold, reserved.
3. The maximum hold time for seats is configurable.
4. The hold seats is automatically made available when the maximum hold time is reached.
5. Seats are assigned sequentially starting from last row and first column. We assume the best seats starts from last row and      first column. 
6. Reservation of seats does not guarantee adjacent seats since seats are reserved based on availability.
7. No notification is given for the expiration of hold seats.
8. The TicketService Interface does not provide any specifications for Exception Handling. Method findAndHoldSeats returns null    when the specified number of seats is not assigned/hold. When fewer number of seats is requested, the method throws            IllegalArgumentException. 
9. Method reserveSeats returns null, if the SeatHoldId for a customer is not found, if the hold is expired or if the customer      email id is invalid.


# Building Project

# Test Results
Test cases are written using JUnit.

To run the test cases, Cd into the particular directory where project is.

Test are run using the following command:

Run mvn test


