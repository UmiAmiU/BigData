cd ~/Lab1
mvn package

cd ~/docker-hadoop
sudo docker-compose up -d

sudo docker cp ~/Lab1/target/lab1-1.0-jar-with-dependencies.jar 8e09b211f4fc:/
sudo docker cp ~/input.txt 8e09b211f4fc:/
sudo docker cp ~/Lab1_Hadoop.sh 8e09b211f4fc:/

sudo docker exec -it 8e09b211f4fc bash Lab1_Hadoop.sh
sudo docker-compose stop

