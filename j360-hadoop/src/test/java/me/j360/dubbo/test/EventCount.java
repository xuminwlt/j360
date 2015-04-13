package me.j360.dubbo.test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
/**
 * Created with j360 -> me.j360.dubbo.test.
 * User: min_xu
 * Date: 2015/4/10
 * Time: 16:07
 * 说明：测试MapReduce作业
 * 运行“mvn package”命令产生jar包hadoopstudy-1.0-SNAPSHOT.jar，并将jar文件复制到hadoop安装目录下
 这里假定我们需要分析几个日志文件中的Event信息来统计各种Event个数，所以创建一下目录和文件
 运行“mvn package”命令产生jar包hadoopstudy-1.0-SNAPSHOT.jar，并将jar文件复制到hadoop安装目录下
 这里假定我们需要分析几个日志文件中的Event信息来统计各种Event个数，所以创建一下目录和文件
 [plain] view plaincopyprint?在CODE上查看代码片派生到我的代码片
 /tmp/input/event.log.1
 /tmp/input/event.log.2
 /tmp/input/event.log.3

 因为这里只是要做一个列子，所以每个文件内容可以都一样，假如内容如下
 [plain] view plaincopyprint?在CODE上查看代码片派生到我的代码片
 JOB_NEW ...
 JOB_NEW ...
 JOB_FINISH ...
 JOB_NEW ...
 JOB_FINISH ...

 然后把这些文件复制到HDFS上
 [plain] view plaincopyprint?在CODE上查看代码片派生到我的代码片
 $ bin/hdfs dfs -put /tmp/input /user/fkong/input

 运行mapreduce作业
 [plain] view plaincopyprint?在CODE上查看代码片派生到我的代码片
 $ bin/hadoop jar hadoopstudy-1.0-SNAPSHOT.jar my.hadoopstudy.mapreduce.EventCount /user/fkong/input /user/fkong/output

 查看执行结果
 [plain] view plaincopyprint?在CODE上查看代码片派生到我的代码片
 $ bin/hdfs dfs -cat /user/fkong/output/part-r-00000
 */



public class EventCount {

    public static class MyMapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1);
        private Text event = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            int idx = value.toString().indexOf(" ");
            if (idx > 0) {
                String e = value.toString().substring(0, idx);
                event.set(e);
                context.write(event, one);
            }
        }
    }

    public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: EventCount <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "event count");
        job.setJarByClass(EventCount.class);
        job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}