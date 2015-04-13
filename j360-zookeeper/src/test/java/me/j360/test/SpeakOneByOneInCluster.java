package me.j360.test;

import org.apache.commons.io.FileUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
/**
 * Created with j360 -> me.j360.web.test.zookeeper.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 17:17
 * 说明：
 */

/**
 * 使用Zookeeper来控制集群中的服务器，同一时刻只会有一台做指定的事情，名称多台同时做会产生冲突的情况。<rb> 这个示例演示的是集群中的服务器，同一时刻只会有一台说话，当这台服务服务器说话时，其它的服务器不可以说话。<br>
 * 如果在操作的过程中，连接Zookeeper过期了，会自动进行重连；并确保其在同一个服务器上面布署多个示例也可以正常运行。<br>
 * 运行该示例：导入Zookeeper下面的Jar包，并且其依赖commons-io，其它就不需要了
 *
 * @author fenglibin 2014-6-14 下午10:21:15
 */
public class SpeakOneByOneInCluster implements Watcher {

    // 连接Zookeeper Server的客户端连接
    static ZooKeeper            zk       = null;
    // 锁
    static Object               LOCK     = new Object();
    // 用户操作的根节点，与“/”是不同的，用户可以控制
    String                      root;
    // 当前节点的名称，由服务器的名称加随机数据组成，之所以使用这种方式，那是考虑到同一台服务器上可能会布署多个应用示例，而不一定是每个应用示例布署到集群的不同服务器上
    private static String       nodeName = "";
    // 节点锁，只有能够创建该节点的应用示例，才可以做指定的事情，如本例中的说话
    private static String       LOCK_NODE;
    // 用于表示当前应用是否拿到了节点锁，然后根据是否拿到节点锁，才可以做指定的事情，如本例中的说话
    static boolean              getLock;
    String                      address;
    // 日志文件
    File                        logFile  = new File("D:/tmp/logFile.txt");
    private static final Logger LOG;
    static {
        // Keep these two lines together to keep the initialization order explicit
        LOG = LoggerFactory.getLogger(SpeakOneByOneInCluster.class);
    }

    SpeakOneByOneInCluster(String address){
        this.address = address;
        if (zk == null) {
            try {
                connect(address);
            } catch (IOException e) {
                writeLog(e.toString());
                zk = null;
            }
        }
    }

    void connect(String address) throws IOException {
        writeLog("Starting ZK:");
        zk = new ZooKeeper(address, 2181, this);
        writeLog("Finished starting ZK: " + zk);
    }

    synchronized public void process(WatchedEvent event) {
        synchronized (LOCK) {
            if (!event.getPath().equals(LOCK_NODE)) {
                LOCK.notify();
            }
        }
    }

    /**
     * 用于选举可用于做事情的应用
     */
    static public class Barrier extends SpeakOneByOneInCluster implements Runnable {

        private static final Logger LOG;
        static {
            // Keep these two lines together to keep the initialization order explicit
            LOG = LoggerFactory.getLogger(Barrier.class);
        }
        String                      nodeName;

        /**
         * Barrier constructor
         *
         * @param address
         * @param root
         */
        Barrier(String address, String root){
            super(address);
            this.root = root;
            // Create barrier node
            if (zk != null) {
                try {
                    Stat s = zk.exists(root, false);
                    if (s == null) {
                        zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                } catch (KeeperException e) {
                    writeLog("Keeper exception when instantiating queue: " + e.toString());
                } catch (InterruptedException e) {
                    writeLog("Interrupted exception");
                }
            }
            // My node name
            try {
                this.nodeName = new StringBuilder(InetAddress.getLocalHost().getCanonicalHostName()).append((int) (Math.random() * 100)).toString();
            } catch (UnknownHostException e) {
                writeLog(e.toString());
            }
        }

        /**
         * Join barrier
         *
         * @return
         * @throws org.apache.zookeeper.KeeperException
         * @throws InterruptedException
         */

        void lock() throws KeeperException, InterruptedException {
            Stat stat = zk.exists(LOCK_NODE, false);
            if (stat != null) {
                return;
            }
            zk.create(LOCK_NODE, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            writeLog(SpeakOneByOneInCluster.nodeName + ":create node " + LOCK_NODE);
            getLock = Boolean.TRUE;
            List<String> childs = zk.getChildren(root, true);
            // 节点存在了就返回不处理
            if (!(childs == null || childs.size() == 0)) {
                return;
            }
            zk.create(root + "/" + nodeName, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            writeLog(SpeakOneByOneInCluster.nodeName + ":create node " + root + "/" + nodeName);
            SpeakOneByOneInCluster.nodeName = this.nodeName;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock();
                } catch (NodeExistsException e) {
                } catch (SessionExpiredException e) {
                    try {
                        // Session过期了，进行重连
                        connect(address);
                    } catch (IOException e1) {
                        LOG.error(e1.getMessage(), e1);
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Producer-Consumer queue
     */
    static public class Speak extends SpeakOneByOneInCluster implements Runnable {

        private static final Logger LOG;
        static {
            // Keep these two lines together to keep the initialization order explicit
            LOG = LoggerFactory.getLogger(Speak.class);
        }

        /**
         * Constructor of speaker
         *
         * @param address
         * @param root
         */
        Speak(String address, String root){
            super(address);
            this.root = root;
            // Create ZK node name
            if (zk != null) {
                try {
                    Stat s = zk.exists(this.root, false);
                    if (s == null) {
                        zk.create(this.root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                } catch (SessionExpiredException e) {
                    try {
                        // Session过期了，进行重连
                        connect(address);
                    } catch (IOException e1) {
                        LOG.error(e1.getMessage(), e1);
                    }
                } catch (KeeperException e) {
                    writeLog("Keeper exception when instantiating queue: " + e.toString());
                } catch (InterruptedException e) {
                    writeLog("Interrupted exception");
                }
            }
        }

        /**
         * Add element to the queue.
         *
         * @return
         */

        boolean check() {
            try {
                if (getLock == Boolean.FALSE) {
                    return false;
                }
                List<String> childs = zk.getChildren(root, true);
                if (childs == null || childs.size() == 0) {
                    return false;
                }
                if (childs.size() != 1) {
                    throw new RuntimeException("childs's size is " + childs.size() + ", it must be 1.");
                }
                if (childs.get(0).endsWith(SpeakOneByOneInCluster.nodeName)) {
                    return true;
                }
                return false;
            } catch (SessionExpiredException e) {
                try {
                    // Session过期了，进行重连
                    connect(address);
                } catch (IOException e1) {
                    LOG.error(e1.getMessage(), e1);
                }
                return false;
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return false;
            }

        }

        void speak() throws InterruptedException {
            while (true) {
                // writeLog(CopyOneFileInMultiVM.choosedNodeName + ":try speak...");
                synchronized (LOCK) {
                    if (!check()) {
                        // writeLog(CopyOneFileInMultiVM.choosedNodeName + ":copy condition not ready,wait.");
                        LOCK.wait();
                    } else {
                        writeLog(SpeakOneByOneInCluster.nodeName + ":speak now");
                        doSpeak();
                    }
                }
            }
        }

        void doSpeak() {
            try {
                writeLog(SpeakOneByOneInCluster.nodeName + ": Now only I can speak. Let me sleep 5s.");
                Thread.sleep(5000);
                writeLog(SpeakOneByOneInCluster.nodeName + ": I week up now.");
                unlock();
            } catch (SessionExpiredException e) {
                try {
                    connect(address);
                    unlock();
                } catch (Exception e1) {
                    LOG.error(e1.getMessage(), e1);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

        /**
         * delete the lock node
         *
         * @return
         * @throws org.apache.zookeeper.KeeperException
         * @throws InterruptedException
         */

        void unlock() throws KeeperException, InterruptedException {
            writeLog(SpeakOneByOneInCluster.nodeName + ":Begin to delete node " + SpeakOneByOneInCluster.nodeName + " and node " + LOCK_NODE);
            zk.delete(root + "/" + SpeakOneByOneInCluster.nodeName, 0);
            zk.delete(LOCK_NODE, 0);
            getLock = Boolean.FALSE;
            writeLog(SpeakOneByOneInCluster.nodeName + ":Resource release OK. It's you turn, bye.");
        }

        @Override
        public void run() {
            try {
                speak();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void writeLog(String str) {
        try {
            System.out.println(str);
            FileUtils.writeStringToFile(logFile, str + "\n", true);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void main2(String[] args) throws InterruptedException {
        LOCK_NODE = "/zk_test/lock";
        new Thread(new Barrier("192.168.145.128", "/zk_test/chooseCopyMachine")).start();
        new Thread(new Speak("192.168.145.128", "/zk_test/chooseCopyMachine")).start();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                try {
                    if (zk.exists(LOCK_NODE, false) != null) {
                        zk.delete(LOCK_NODE, -1);
                    }
                    zk.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    LOG.error(e.getMessage(), e);
                }
            }
        });
    }

    private static String  CLIENT_PORT = "2181";
    public static void main(String[] args) throws Exception {
        // 创建一个与服务器的连接
        ZooKeeper zk = null;
            zk = new ZooKeeper("192.168.145.128:" + CLIENT_PORT,2000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });
        // 创建一个目录节点
        zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
                Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath",false,null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/testRootPath",true));
        // 修改子目录节点数据
        zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1);
        System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]");
        // 创建另外一个子目录节点
        zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),
                Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,null)));
        // 删除子目录节点
        zk.delete("/testRootPath/testChildPathTwo",-1);
        zk.delete("/testRootPath/testChildPathOne",-1);
        // 删除父目录节点
        zk.delete("/testRootPath",-1);
        // 关闭连接
        zk.close();
    }
}
