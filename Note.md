./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=bernhardwinner/jobappimage

./mvnw spring-boot:build-image "-Dspring-boot.build-image.imageName=bernhardwinner/jobappimage"

docker login

docker push bernhardwinner/jobappimage

docker run -p 8080:8080 bernhardwinner/jobappimage

detached mode
docker run -d -p 8080:8080 bernhardwinner/jobappimage
docker ps

docker run -d --name db -e POSTGRES_PASSWORD=postgres postgres

docker run -d --name pgadmin -e PGADMIN_DEFAULT_EMAIL=bernhardwinner@gmail.com -e PGADMIN_DEFAULT_PASSWORD=pgadmin dpage/pgadmin4

docker exec -it pgadmin ping db => ping: bad address 'db'

docker rm -f db pgadmin

docker network create my-network

USING NETWORKS:
docker run -d --name db --network my-network -e POSTGRES_PASSWORD=postgres postgres

docker run -d --name pgadmin --network my-network -e PGADMIN_DEFAULT_EMAIL=bernhardwinner@gmail.com -e PGADMIN_DEFAULT_PASSWORD=pgadmin dpage/pgadmin4

Start the PostgreSQL service:
docker run -d \
--name postgres_container \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-e PGDATA=/data/postgres \
-v postgres:/data/postgres \
-p 5432:5432 \
--network postgres \
--restart unless-stopped \
postgres

Start the pgAdmin service:
docker run -d \
--name pgadmin_container \
-e PGADMIN_DEFAULT_EMAIL=bernhardwinner@gmail.com \
-e PGADMIN_DEFAULT_PASSWORD=pgadmin \
-e PGADMIN_CONFIG_SERVER_MODE=False \
-v pgadmin:/var/lib/pgadmin \
-p 5050:80 \
--network postgres \
--restart unless-stopped \
dpage/pgadmin4





Dockerfile

FROM openjdk:11
VOLUME /tmp
ADD target/my-app.jar my-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/my-app.jar"]

