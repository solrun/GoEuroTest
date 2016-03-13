# GoEuroTest

Solution to GoEuro's Java Developer Test, as described at https://github.com/goeuro/dev-test.

A standalone Java application that takes a string representing a location as an input parameter, queries GoEuro's Location JSON API with the input and creates a CSV file containing data from the response. 

The [Gson library](https://github.com/google/gson) is used for JSON handling.

Tested with Java 1.7 SE.

# Usage:

    $ java -jar GoEuroTest.jar "CITY_NAME"

where "CITY_NAME" is the name of a location in English. 

The name may be incomplete. If the name contains spaces it must be enclosed in quotes or everything after the first space will be ignored.
