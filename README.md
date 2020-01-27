# Importfile

This applications is an example that how to import files using Spring-Batch.

Why did we use Spring-Batch in this project ?
    The answer is simple. With spring-batch is possible makes a cluster to process the files more faster.
    

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### How to configure folder of files that it going to be processed

```
Open docker-composer and set diretory file like you can see below:
    volumes:
    - /home/paulo/Documents/massa:/tmp 
Only change "/home/paulo/Documents/massa" to your folder

Observation:
As we use spring-batch in this project then the files have to be 
an array so if you are using your own files just remove {"data":} from them"
     
```
## Getting application up
```
Execute "mvn package"
Execute "docker-compose up --build -d"
```

## Running the tests

How to run the automated tests for this system


```
Execute "mvn package"
Execute "docker-compose up --build -d"
Execute "mvn test"
```

## How to import a file
```
Make a post at below endpoint and do not forget to pass file name:
http://localhost:8080/productJob/file/{input_file_name}/execute

```

## How to get avg price 
```
Make a get at below endpoint and do not forget to pass product name and quantity of lojistas:
http://localhost:8080/product/{name}/lojistas/{quantity}

```


## Built With

* [Spring](https://spring.io/guides)        - The web framework used
* [Maven](https://maven.apache.org/)        - Dependency Management
* [Assured](http://rest-assured.io/)        - Used to make request
* [Cucumber](https://cucumber.io/)          - Used to make tests
* [Lombok](https://projectlombok.org/)     - Used to reducing BOILERPLATE code.

## Author

* **Paulo Augusto**  - *Initial work* - [fileimport](https://github.com/paulo3425/fileimport)





