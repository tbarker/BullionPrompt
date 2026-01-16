BullionPrompt
======================

A simple API library and command-line tool for using [BullionVault](http://www.bullionvault.com)'s trading API.


Usage
------

From the command-line

    java -jar build/libs/bullionprompt-2.0.0-all.jar help

Available commands:
- `balance` - Display account property balances and valuations
- `market` - Show the markets prices
- `cancel` - Cancel an existing market order
- `orders` - Display your (by default only active) orders or a single given order
- `place` - Place a live market order
- `deals` - Display the last deals on the market
- `tail` - Show changes to the markets
- `depth` - Show the market pricing depth
- `spot` - Check the spot price
- `help` - Display help information

See http://www.bullionvault.com/help/?xml_api.html for more about the BullionVault API.


Alternatively...

Fire up your favourite Java IDE and start at com.thomasbarker.bullionprompt.xmlapi.BVSession

    BVSession session = new BVSession();
    session.login( "testuser", "apassword" );
    List<Order> orders = session.orders();


Build
-----

### Requirements

- Java 21 or later (uses modern Java features like Records and public fields)
- Gradle 8.5+ (wrapper included)

### Building from Source

Build the executable JAR:

    ./gradlew shadowJar

The JAR will be created at `build/libs/bullionprompt-2.0.0-all.jar`

### Running Tests

    ./gradlew test

### Dependencies

- Jakarta XML Binding (JAXB) 4.0.1 - XML serialization
- JCommander 1.82 - Command-line parsing
- SLF4J 2.0.9 - Logging facade
- JUnit Jupiter 5.10.1 - Testing framework
- Java HttpClient (built-in) - HTTP networking


Modernization
-------------

This project has been modernized from Java 8 to Java 21 with the following improvements:

- **Build System**: Migrated from Ant+Ivy to Gradle with Kotlin DSL
- **Java Version**: Upgraded to Java 21 LTS with modern language features
- **Dependency Updates**:
  - Jakarta XML Binding 4.0 (from javax.xml.bind)
  - Java HttpClient (from Apache HttpClient 4.3)
  - JUnit 5 (from JUnit 4.8.2)
  - SLF4J 2.0 (from commons-logging)
- **Code Modernization**:
  - Removed Lombok dependency
  - Used Java Records for immutable data classes
  - Used public fields for mutable beans (cleaner than verbose getters/setters)
  - Applied modern Java 21 patterns throughout

Disclaimer
----------

This is not an official BullionVault product. Bugs may remain. Prices vary and speculation can result in losses.


Future Work
-----------

+ Quote function that predicts an order's execution price
+ Better client-side validation
+ Automatic data refreshes and a listener-style event-change API

