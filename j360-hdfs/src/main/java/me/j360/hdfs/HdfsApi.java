package me.j360.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

/**
 * Created with j360 -> me.j360.hdfs.
 * User: min_xu
 * Date: 2015/4/14
 * Time: 22:27
 * 说明：
 */
public class HdfsApi {
    public static void main(String[] args) throws Exception {
        createFile();
    }

    public static void createFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        byte[] buff = "hello hadoop world!\n".getBytes();
        Path dfs = new Path("/test");
        FSDataOutputStream outputStream = hdfs.create(dfs);
        outputStream.write(buff, 0, buff.length);
    }


    public static void createDir() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path dfs = new Path("/TestDir");
        hdfs.mkdirs(dfs);
    }

    public static void rename() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path frpaht = new Path("/test");    //旧的文件名
        Path topath = new Path("/test1");    //新的文件名
        boolean isRename = hdfs.rename(frpaht, topath);
        String result = isRename ? "成功" : "失败";
        System.out.println("文件重命名结果为：" + result);
    }

    public static void deleteFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path delef = new Path("/test1");
    }

    public static void checkFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path findf = new Path("/test1");
        boolean isExists = hdfs.exists(findf);
        System.out.println("Exist?" + isExists);
    }

    public static void getModifyTime() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path fpath = new Path("/user/hadoop/test/file1.txt");
        FileStatus fileStatus = hdfs.getFileStatus(fpath);
        long modiTime = fileStatus.getModificationTime();
        System.out.println("file1.txt的修改时间是" + modiTime);
    }

    /**
     * 通过"FileSystem.getFileBlockLocation（FileStatus file，long start，long len）
     * 可查找指定文件在HDFS集群上的位置，其中file为文件的完整路径，start和len来标识查找文件的路径。具体实现如下
     */
    public static void getFileLocation() throws Exception {
        Configuration conf = new Configuration();
        FileSystem hdfs = FileSystem.get(conf);
        Path fpath = new Path("/user/hadoop/cygwin");
        FileStatus filestatus = hdfs.getFileStatus(fpath);
        BlockLocation[] blkLocations = hdfs.getFileBlockLocations(filestatus, 0, filestatus.getLen());
        int blockLen = blkLocations.length;
        for (int i = 0; i < blockLen; i++) {
            String[] hosts = blkLocations[i].getHosts();
            System.out.println("block_" + i + "_location:" + hosts[0]);
        }
    }


    /**
     * 通过"DatanodeInfo.getHostName（）"可获取HDFS集群上的所有节点名称
     * */
    public static void getList() throws Exception {
        Configuration conf=new Configuration();
        FileSystem fs=FileSystem.get(conf);
        DistributedFileSystem hdfs = (DistributedFileSystem)fs;
        DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();
        for(int i=0;i<dataNodeStats.length;i++){
            System.out.println("DataNode_"+i+"_Name:"+dataNodeStats[i].getHostName());
        }
    }




}
