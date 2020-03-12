import eu.bitwalker.useragentutils.UserAgent;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class Main {


    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, Item> {

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String line=value.toString();


            UserAgent userAgent = UserAgent.parseUserAgentString(line);
            String browser = userAgent.getBrowser().getGroup().getName();

            context.getCounter("Browsers",browser).increment(1);

            String ip = line.split(" ")[0];

            int size = 0;

            try {
                size = Integer.parseInt(line.split(" ")[9]);
            } catch (Exception ex) {
		System.out.println(ex);
            }

            Item item=new Item();
            item.setCount(1);
            item.setSum(size);
            context.write(new Text(ip),item );
        }
    }

    public static class IntSumReducer
            extends Reducer<Text,Item,Text,Item> {

        public void reduce(Text key, Iterable<Item> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            for (Item val : values) {
                sum += val.getSum();
                count+=val.getCount();
            }


            Item item=new Item();
            item.setSum(sum);
            item.setCount(count);
            context.write(key, item);
        }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Lab3");
        job.setJarByClass(Main.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setNumReduceTasks(3);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Item.class);
        //FileInputFormat.addInputPath(job, new Path("D:\\University\\LastStand\\1.txt"));
        //FileOutputFormat.setOutputPath(job, new Path("D:\\University\\LastStand\\2.txt"));
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

