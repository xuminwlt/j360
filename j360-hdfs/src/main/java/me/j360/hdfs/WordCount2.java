package me.j360.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

public class WordCount2 {
    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, IntWritable>{
        static enum CountersEnum { INPUT_WORDS }

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        private boolean caseSensitive;
        private Set<String> patternsToSkip = new HashSet<String>();

        private Configuration conf;
        private BufferedReader fis;

        @Override
        public void setup(Context context) throws IOException,
                InterruptedException {
            conf = context.getConfiguration();
            caseSensitive = conf.getBoolean("wordcount.case.sensitive", false);
            if (conf.getBoolean("wordcount.skip.patterns", false)) {
                URI[] patternsURIs = Job.getInstance(conf).getCacheFiles();
                for (URI patternsURI : patternsURIs) {
                    Path patternsPath = new Path(patternsURI.getPath());
                    String patternsFileName = patternsPath.getName().toString();
                    parseSkipFile(patternsFileName);
                }
            }
        }

        private void parseSkipFile(String fileName) {
            try {
                fis = new BufferedReader(new FileReader(fileName));
                String pattern = null;
                while ((pattern = fis.readLine()) != null) {
                    patternsToSkip.add(pattern);
                }
            } catch (IOException ioe) {
                System.err.println("Caught exception while parsing the cached file '"
                        + StringUtils.stringifyException(ioe));
            }
        }

        @Override
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String line = (caseSensitive) ?
                    value.toString() : value.toString().toLowerCase();
            for (String pattern : patternsToSkip) {
                line = line.replaceAll(pattern, "");
            }
            StringTokenizer itr = new StringTokenizer(line);
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
                Counter counter = context.getCounter(CountersEnum.class.getName(),
                        CountersEnum.INPUT_WORDS.toString());
                counter.increment(1);
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args0) throws Exception {
        String[] args1={"hdfs://192.168.145.128:9000/tmp/input",	"hdfs://192.168.145.128:9000/tmp/output/yarn.out10"};
        //String[] args1=args0;
        Configuration conf = new Configuration();
        conf.set("mapred.jar", "F:\\Maven\\repo\\me\\j360\\j360-hdfs\\1.0-SNAPSHOT\\j360-hdfs-1.0-SNAPSHOT.jar");

        conf.setBoolean("mapreduce.app-submission.cross-platform", true);      // 配置使用跨平台提交任务
        String flag=conf.get("mapreduce.app-submission.cross-platform");
        System.out.println(flag);
        conf.set("fs.defaultFS", "hdfs://192.168.145.128:9000");                       //指定namenode
        conf.set("mapreduce.framework.name", "yarn");                            // 指定使用yarn框架
        conf.set("yarn.resourcemanager.address", "192.168.145.128:8032");             // 指定resourcemanager
        conf.set("yarn.resourcemanager.scheduler.address", "192.168.145.128:8030");  // 指定资源分配器
        conf.set("mapreduce.jobhistory.address","192.168.145.128:10020");

        GenericOptionsParser optionParser = new GenericOptionsParser(conf, args1);
        String[] remainingArgs = optionParser.getRemainingArgs();
        if (!(remainingArgs.length != 2 || remainingArgs.length != 4)) {
            System.err.println("Usage: wordcount <in> <out> [-skip skipPatternFile]");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount2.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        List<String> otherArgs = new ArrayList<String>();
        for (int i=0; i < remainingArgs.length; ++i) {
            if ("-skip".equals(remainingArgs[i])) {
                job.addCacheFile(new Path(remainingArgs[++i]).toUri());
                job.getConfiguration().setBoolean("wordcount.skip.patterns", true);
            } else {
                otherArgs.add(remainingArgs[i]);
            }
        }
        FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));

        if(job.waitForCompletion(true)){
            System.out.println("Job done...");
            //打印出结果
            FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.145.128:9000"), conf);

            // 列出hdfs上/tmp/input/目录下的所有文件和目录
            FileStatus[] statuses = fs.listStatus(new Path("/tmp/output/yarn.out10"));
            for (FileStatus status : statuses) {
                System.out.println(status);
                System.out.println(status.getPath());

            }

            // 显示在hdfs的/tmp/input下指定文件的内容
            InputStream is = fs.open(new Path("/tmp/output/yarn.out10/part-r-00000"));
            IOUtils.copyBytes(is, System.out, 1024, true);

        }
    }
}