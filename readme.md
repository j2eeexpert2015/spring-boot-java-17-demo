# Spring Boot Java 17 Demo

This project demonstrates the use of Java 17 features in a Spring Boot application.

## Features showcased

* **Records:** Immutable data classes for concise code and enhanced readability.
    * Examples: `UserRecord`, `UserAddress`, `UserSummary`, `AppConfig`, `NetworkResult` subtypes (Success, Failure, Timeout), `UserRepository.UserDbRow`
    * Benefits: Reduced boilerplate, compact syntax, immutability.
    * How to explore:
        * Create instances of record classes and print their values.
        * Use the compact constructor for `UserAddress` to see validation.
        * Observe how records are used in `switch` expressions (with preview features enabled).
* **Sealed Interfaces:** Restricted class hierarchies for improved type safety.
    * Example: `NetworkResult`
    * Benefits: Controlled inheritance, enhanced type safety.
    * How to explore:
        * Create instances of different `NetworkResult` subtypes.
        * Use a `switch` expression or pattern matching with `instanceof` to handle different result types.
* **Pattern Matching for switch (Preview Feature):** Enhanced `switch` expressions for concise handling of different object types.
    * Example: `UserController.validateAddress`
    * Benefits: Simplified type checking and extraction, improved readability.
    * How to explore:
        * Call `validateAddress` with different `NetworkResult` types and observe the output.
        * Enable preview features in IntelliJ IDEA (see below) and at runtime (`java --enable-preview -jar your-application.jar`).
* **Helpful NullPointerExceptions:** More informative error messages for easier debugging.
    * Benefits: Improved