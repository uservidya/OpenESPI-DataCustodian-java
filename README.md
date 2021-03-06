[![Build Status](https://travis-ci.org/energyos/OpenESPI-DataCustodian-java.png)](https://travis-ci.org/energyos/OpenESPI-DataCustodian-java)

OpenESPI-DataCustodian
======================

The Open Energy Services Provider Interface (ESPI) Data Custodian Repository Providing implementations of the interface used to provide energy usage information to retail customers and third parties.

## Setup

Note: You need to download and install [OpenESPI-Common-Java](https://github.com/energyos/OpenESPI-Common-java) into your local Maven repository to build this project.

First clone the project from github:

```bash
git clone https://github.com/energyos/OpenESPI-DataCustodian.git
cd OpenESPI-DataCustodian/
```

Start tomcat7 using maven:

```bash
mvn tomcat7:run
```

Now the application should be available at [http://localhost:8080/retailcustomers](http://localhost:8080/retailcustomers).

## IDE Setup

### Eclipse Setup

Open Eclipse and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### Spring Tool Suite Setup

Open Spring Tool Suite and import a Maven project (File > Import... > Maven > Existing Maven Projects).

### IntelliJ Setup

Open IntelliJ and open the project (File > Open...).

## Testing

### Unit Tests

To run all Unit tests:

```bash
mvn test
```

Run a single test class:

```bash
mvn -Dtest=<TestClassName> test
mvn -Dtest=HomeControllerTests test
```

Run a single test in a single class:

```bash
mvn -Dtest=<TestClassName>#<testMethodName> testMethodName
mvn -Dtest=HomeControllerTests#index_shouldDisplayHomePage test
```

### Cucumber Features

To run all Cucumber features:

```bash
mvn verify
```
