package me.j360.hdfs;

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
public class Test {
    public static void main(String[] args) throws Exception {
        String uri = "hdfs://192.168.145.128:9000/";
        Configuration config = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), config);

        // 列出hdfs上/tmp/input/目录下的所有文件和目录
        FileStatus[] statuses = fs.listStatus(new Path("/tmp/input"));
        for (FileStatus status : statuses) {
            System.out.println(status);
        }

        // 在hdfs的/tmp/input目录下创建一个文件，并写入一行文本
        FSDataOutputStream os = fs.create(new Path("/tmp/input/test.log"));
        os.write("Hello World!".getBytes());
        os.flush();
        os.close();

        // 显示在hdfs的/tmp/input下指定文件的内容
        InputStream is = fs.open(new Path("/tmp/input/test.log"));
        IOUtils.copyBytes(is, System.out, 1024, true);

        /*FileStatus[] statuses2 = fs.listStatus(new Path("/tmp/output"));
        System.out.println(statuses2.length);
        for (FileStatus status : statuses2) {
            System.out.println(status);
        }*/

    }
}
