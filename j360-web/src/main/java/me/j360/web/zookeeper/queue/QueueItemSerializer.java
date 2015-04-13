package me.j360.web.zookeeper.queue;

import org.apache.curator.framework.recipes.queue.QueueSerializer;

/**
 * Created with j360 -> me.j360.web.zookeeper.queue.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 23:13
 * 说明：
 */
public class QueueItemSerializer implements QueueSerializer<String>
{
    @Override
    public byte[] serialize(String item)
    {
        return item.getBytes();
    }

    @Override
    public String deserialize(byte[] bytes)
    {
        return new String(new String(bytes));
    }
}