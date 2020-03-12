cd ~/docker-hive
sudo docker-compose up -d
sudo docker cp ~/input.txt 273e4834ce36:/opt
sudo docker cp ~/Lab2_HQL.sh 273e4834ce36:/opt
sleep 70s
sudo docker exec -it 273e4834ce36 hive -f Lab2_HQL.sh
sudo docker-compose stop
