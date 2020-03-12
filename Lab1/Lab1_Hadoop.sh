hadoop fs -mkdir -p /user/root/
hadoop fs -put /input.txt /user/root/
hadoop fs -rm -R out
hadoop jar lab1-1.0-jar-with-dependencies.jar input.txt out
hadoop fs -cat out/part-r-00000
