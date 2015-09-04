package me.j360.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URI;

/**
 * Created with j360 -> me.h360.hdfs.
 * User: min_xu
 * Date: 2015/4/14
 * Time: 9:05
 * 说明：测试hdfs的文件的情况
 */
public class Output {
    public static void main(String[] args) throws Exception {
        String uri = "hdfs://192.168.145.128:9000/";
        Configuration config = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), config);

        // 列出hdfs上/tmp/input/目录下的所有文件和目录
        FileStatus[] statuses = fs.listStatus(new Path("/tmp/output/yarn.out20"));
        for (FileStatus status : statuses) {
            System.out.println(status);
        }

        InputStream is = fs.open(new Path("/tmp/output/yarn.out20/part-r-00000"));
        IOUtils.copyBytes(is, System.out, 1024, true);
    }
}
