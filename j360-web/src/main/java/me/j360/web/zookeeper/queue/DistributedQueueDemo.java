package me.j360.web.zookeeper.queue;

import me.j360.web.zookeeper.log.IZKListener;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;

/**
 * Created with j360 -> me.j360.web.zookeeper.queue.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 23:12
 * 说明：
 */
public class DistributedQueueDemo implements IZKListener {

    //申明两个队列实例
    private DistributedQueue<String> queue1 = null;
    private DistributedQueue<String> queue2 = null;

    //数据系列化转换工具类
    private QueueSerializer<String> serializer = new QueueItemSerializer();

    //消费者处理方法
    private QueueConsumer<String> consumer = new QueueConsumer<String>() {
        @Override
        public void consumeMessage(String message) throws Exception {
            //线程等待5秒，模拟数据处理，以达到数据抢夺的目的
            Thread.sleep(5000);
            //打印出是哪个线程处理了哪些数据
            System.out.println(Thread.currentThread().getId() +  " consume: " + message);
        }

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            System.out.println("new state: " + newState);
        }
    };

    //Spring启动时调用此方法以启动所有队列实例
    @Override
    public void executor(CuratorFramework client) {
        //实例化所有队列，指定ZK队列数据获取地址，和其它参数
        //由于它们的地址是相同的，都是*/zk_queue_test*，所以Curator会根据它们的空闲状态来分配新的任务，上面通过线程暂停5秒来拉开它们的处理间隔。
        queue1 = QueueBuilder.builder(client, consumer, serializer, "/zk_queue_test").buildQueue();
        queue2 = QueueBuilder.builder(client, consumer, serializer, "/zk_queue_test").buildQueue();

        try {
            //启动所有队列实例，让它们开始工作，注意所有指定的动作只有在调用了queue1.start()方法之后才会被执行，比如queue.put()等。
            //Curator提供了queue.put()方法来往队列里添加数据，但它同时也会处理，但我们不想这样，所以添加的过程我们通过其它的方式来实现。
            queue1.start();
            queue2.start();
            System.out.println("Queues started!");
        } catch (Exception e) {

        }
    }
}