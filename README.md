# Routines
> Enqueue routines for execution.
Exposes an api for registration of routines that perform periodically. Routines execute two basic commands:
**WRITE_TO_FILE_ONE** - Write a message to file *one.txt*
**WRITE_TO_FILE_TWO** - Write a message to file *two.txt*
## Compilation

Compiling the project
```sh
./gradlew clean && ./gradlew build
```

Compiling the project and building a docker image
```sh
./gradlew clean && ./gradlew build && docker build --no-cache -t crisun/routines .
```

Compiling the project and building a docker image with docker-compose
```sh
./gradlew clean && ./gradlew build && docker-compose build
```

## Initialization

Using docker-compose:
```sh
docker-compose up
```

Using docker:
```sh
docker run --rm -it  -v /tmp:/app/data -p 8080:8080 crisun/routines
```

Running manually:
```sh
java -jar routines-1.0.jar
```

## Example

After initialization we have the following operations:

Query routines:
```sh
curl -H "Content-type: application/json" "http://localhost:8080/routines"
```

Query a routine:
```sh
curl -H "Content-type: application/json" "http://localhost:8080/routine/1"
```
Insert routines:
```sh
curl -v -XPOST -H "Content-type: application/json" "http://localhost:8080/routines" -d '{"interval_in_seconds":1,"command":"WRITE_TO_FILE_ONE","mensagem":"rotina de teste 1"}'
```
Delete a routine:
```sh
curl -v -XDELETE -H "Content-type: application/json" "http://localhost:8080/routine/1"
```
*Note:
By default the files are generated in **/tmp***

## Libraries
flywaydb: https://flywaydb.org

Spring Framework: https://spring.io

h2database: https://www.h2database.com

HikariCP: https://brettwooldridge.github.io/HikariCP