package me.j360.web.zookeeper.queue;

import org.apache.curator.framework.recipes.queue.QueueSerializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Created with j360 -> me.j360.web.zookeeper.queue.
 * User: min_xu
 * Date: 2015/3/30
 * Time: 23:15
 * 说明：
 */
public class ItemSerializer {
    private static final int VERSION = 0x00010001;

    private static final byte ITEM_OPCODE = 0x01;
    private static final byte EOF_OPCODE = 0x02;

    private static final int INITIAL_BUFFER_SIZE = 0x1000;

    public static <T> byte[] serialize(T item, QueueSerializer<T> serializer) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(INITIAL_BUFFER_SIZE);
        DataOutputStream out = new DataOutputStream(bytes);
        out.writeInt(VERSION);

        byte[] itemBytes = serializer.serialize(item);
        out.writeByte(ITEM_OPCODE);
        out.writeInt(itemBytes.length);
        if (itemBytes.length > 0) {
            out.write(itemBytes);
        }

        out.writeByte(EOF_OPCODE);
        out.close();

        return bytes.toByteArray();
    }
}