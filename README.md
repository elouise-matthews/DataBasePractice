# DataBasePractice
How to setup and connect to databases using Java, sql, dbeaver, docker, squirrel sql, postgresql

## 1. Docker
No Installation for postgresql is needed if docker containers are used. </b>
copy the DBPractice_DockerDir folder to local computer and start the docker-compose file. This will start a postgres Database management system (dbms) at the address localhost:5432. By default, this also starts one database named postgres. </b> 

## 2. DBeaver
Follow [instructions](https://computingforgeeks.com/install-and-configure-dbeaver-on-ubuntu-debian/) to download DBeaver community edition (free) and follow [instructions](https://www.devart.com/odbc/postgresql/docs/dbeaver.htm) to create a connection to your local database. </b>
Keep in mind that `jdbc:postgresql` identifies the type of system you are trying to locate, while `localhost:5432` is the location of this system, and `postgres` is the database itself. </b>
