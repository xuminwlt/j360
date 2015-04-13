package me.j360.core.bean;

public class EnumManage {


    public enum DutyEnum {
        Default ("组员",1),
        Principal ("负责人",2),
        Deputy  ("副职",3);

        private final String value;
        private final int key;

        private DutyEnum(final String value, final int key) {
            this.value = value;
            this.key = key;
        }
        public String value() {
            return this.value;
        }
        public int key() {
            return this.key;
        }
    };

	/**
	 * 批注类型
	 * */
	public static enum CommentModelEnum{
        /**
         * 文本
         */
        text("文本", 0),
        /**
         * 图片
         */
        image("图片", 1),
        /**
         * 语音
         */
        voice("语音", 2),
        /**
         * 视频
         */
        video("视频", 3),
        /**
         * 地理位置
         */
        location("地理位置", 4),
        /**
         * 链接
         */
        link("链接", 5),
		/**
		 * 其他文件，打开时用文件表示
		 */
		file("其他文件", 6);

		private final String value;
		private final int key;

		private CommentModelEnum(final String value, final int key) {
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
	 * 订单发起
	 */
	public static enum CreateTypeEnum {
		/**
		 * APP端
		 */
		app("APP端" , 1),
		
		/**
		 * 网站
		 */
		website("网站", 2),
		
		/**
		 * 微信
		 */
		mp("微信", 3);
		
		private final String value;
		private final int key;
		
		private CreateTypeEnum(final String value, final int key) {
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
	 * 坐标类型
	 * bd09 百度墨卡托坐标系
	 * bd09ll 百度经纬度坐标系（百度地图）
	 * gcj02 中国国家测绘局坐标系
	 */
	public static enum CoordTypeEnum {
		/**
		 * 百度墨卡托坐标系
		 */
		bd09("百度墨卡托坐标系" , 1),
		
		/**
		 * 百度经纬度坐标系（百度地图）
		 */
		bd09ll("百度经纬度坐标系" , 2),
		
		/**
		 * 中国国家测绘局
		 */
		gcj02("中国国家测绘局" , 3),
		/**
		 * 国际坐标系
		 */
		wgs84("国际坐标系" , 4);
		
		private final String value;
		private final int key;
		
		private CoordTypeEnum(final String value, final int key) {
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
	 * 百度定位结果LocType
	 * 61 ： GPS定位结果
	 * 62 ： 扫描整合定位依据失败。此时定位结果无效。
	 * 63 ： 网络异常，没有成功向服务器发起请求。此时定位结果无效。
	 * 65 ： 定位缓存的结果。
	 * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
	 * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
	 * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果
	 * 161： 表示网络定位结果
	 * 162~167： 服务端定位失败。 
	 */
	public static enum LocTypeEnum {
		/**
		 * GPS定位结果 
		 */
		GpsLocation("GPS定位结果 " , 61),
		
		/**
		 * 扫描整合定位依据失败。此时定位结果无效
		 */
		bd09ll("扫描整合定位依据失败。此时定位结果无效" , 62),
		
		/**
		 * 网络异常，没有成功向服务器发起请求。此时定位结果无效
		 */
		NetWorkError("网络异常，没有成功向服务器发起请求。此时定位结果无效" , 63),
		
		/**
		 * 定位缓存的结果
		 */
		CacheLocation("定位缓存的结果" , 65),
		
		/**
		 * 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
		 */
		OfflineLocaiton("网络异常，离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果" , 66),
		
		/**
		 * 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果
		 */
		OfflineLocaitonError("离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果" , 67),
		
		/**
		 * 网络连接失败时，查找本地离线定位时对应的返回结果
		 */
		NetConnectErrorReturnOfflineLocaiton("网络连接失败时，查找本地离线定位时对应的返回结果" , 68),
		
		/**
		 * 表示网络定位结果 
		 */
		NetWorkLocation("表示网络定位结果 " , 161);
		
		private final String value;
		private final int key;
		
		private LocTypeEnum(final String value, final int key) {
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
