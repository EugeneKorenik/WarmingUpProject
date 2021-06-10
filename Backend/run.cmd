mvn clean package spring-boot:repackage
docker image rm backend
docker image build -t backend .
docker container rm backend
docker container run --name backend -i -t -p 8080:8080 backend