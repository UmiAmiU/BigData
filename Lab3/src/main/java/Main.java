import eu.bitwalker.useragentutils.UserAgent;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;
import org.apache.spark.api.java.JavaPairRDD;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Lab3");
        conf.set("spark.testing.memory", "480000000");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> arr = jsc.textFile("D:\\University\\LastStand\\input");
        counter(arr);
        ipParse(arr);
    }

    public static JavaPairRDD<String,Integer> counter(JavaRDD<String> arr){
        JavaPairRDD<String,Integer> counts=arr
                .mapToPair(p->{
                    UserAgent userAgent = UserAgent.parseUserAgentString(p);
                    String browser = userAgent.getBrowser().getGroup().getName();
                    return new Tuple2<>(browser, 1);
                }).reduceByKey((p,c)->p+c);
        counts.foreach((p)->System.out.printf("%-20s : %-5d%n",p._1,p._2));
        return counts;
    }

    public static void ipParse(JavaRDD<String> arr) {
        JavaPairRDD<String,Ip> items=arr
                .mapToPair(p->{
                    UserAgent userAgent = UserAgent.parseUserAgentString(p);
                    String ip = p.split(" ")[0];
                    int size = 0;
                    try {
                        size = Integer.parseInt(p.split(" ")[9]);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    return new Tuple2<String,Ip>(ip,new Ip(1,size));
                }).reduceByKey((p,c)->new Ip(p.Count+c.Count,p.Sum+c.Sum))
                .sortByKey();
        items.saveAsTextFile(".\\output");
    }
}
