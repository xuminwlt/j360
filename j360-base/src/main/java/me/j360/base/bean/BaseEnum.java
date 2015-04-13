package me.j360.base.bean;

public class BaseEnum {
    /**
     * 定义账号类型，通过类型来进行匹配和查找
     * */
    public static enum AccountEnum {
        MAIL("邮箱", 1),
        MOBILE("手机号", 2),
        WEIXIN("微信",3),
        UNKNOW("未知",9);

        private final String value;
        private final int key;

        private AccountEnum(final String value, final int key) {
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
     * 定义系统的角色
     * */
    public static enum RoleEnum {
        /* ROLE_ADMIN
        * ROLE_USER
        * ROLE_MANAGER
        * ROLE_MAIN*/
        ROLE_ADMIN("系统管理员", 1),
        ROLE_USER("企业普通用户", 2),
        ROLE_MANAGER("企业管理员", 3),
        ROLE_MAIN("客服平台管理员", 5);

        private final String value;
        private final int key;

        private RoleEnum(final String value, final int key) {
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
     * 定义系统的资源
     * */
    public static enum AppEnum {
        /* ROLE_ADMIN
        * ROLE_USER
        * -->ROLE_COMPANY--delete
        * ROLE_MANAGER
        * ROLE_CMG
        * ROLE_MAIN*/
        MAIN("客服平台", 1),
        COM("企业后台", 2),
        APP("企业APP", 3),
        MP("企业微信", 4);

        private final String value;
        private final int key;

        private AppEnum(final String value, final int key) {
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
     * 排序方式
     */
    public enum OrderType{
        /**
         * 升序
         */
        asc, /**
         * 降序
         */
        desc
    }

    /**
     * 文档状态
     */
    public static enum StateEnum {
        /**
         * 删除
         */
        delete("删除", 1),
        /**
         * 启用
         */
        enable("启用", 2),
        /**
         * 停用
         */
        disabled("停用", 3),
        /**
         * 历史
         */
        history("历史",4);

        private final String value;
        private final int key;

        private StateEnum(final String value, final int key) {
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
     * 性别
     */
    public static enum SexEnum {
        /**
         * 男
         */
        male("男", 1),

        /**
         * 女
         */
        female("女", 2);

        private final String value;
        private final int key;

        private SexEnum(final String value, final int key) {
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
