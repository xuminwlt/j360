package me.j360.ext.bean;

/**
 * Created with us-parent -> com.fz.us.dict.
 * User: min_xu
 * Date: 2014/9/10
 * Time: 21:45
 * 说明：
 */
public class EventBean {
    /**
     * 文档基础状态
     */
    public static enum EventEnum {
        create("创建", 1),
        update("修改",2);

        private final String value;
        private final int key;

        private EventEnum(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    }


    public static enum EventLevel {
        info("信息", 1),
        warn("提醒",2),
        urgent("紧急",3);

        private final String value;
        private final int key;

        private EventLevel(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    }

    /**
     * 履历操作的类型，目前分为四大类
     * 1、后台维护的企业数据
     * 3、用户账户类
     * 4、其他类型（暂不使用）
     * */
    public static enum TargetTypeEnum {
        company("企业数据类", 1),
        users("用户类信息",3),
        other("其他信息类",4);

        private final String value;
        private final int key;

        private TargetTypeEnum(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    }

    public static enum ServiceEnum {
        create("创建", 1),
        update("发生修改",2);

        private final String value;
        private final int key;

        private ServiceEnum(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    }

}
