package me.j360.core.entity.common;

import me.j360.core.entity.parent.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 实体类 - 文件
 * ============================================================================
 * 版权所有 2014 。
 * 
 * @author min_xu
 * 
 * @version 0.1 2014-01-20
 * ============================================================================
 */
@Entity
@Table(name="com_filemanage")
public class FileManage extends BaseEntity {

    private static final long serialVersionUID = 8101878280292901835L;

    /**
     * 目标对象
     */
    private String targetId;
    private String targetClass;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * gridFSId
	 */
	private String gridId;
	/**
	 * size
	 */
	private long size;
	/**
	 * 时长
	 * */
	private long timeLength;
	/**
	 * 补充文件(mp3文件，压缩过的小图片文件)
	 * */
	private String gridMarkId;
	@Transient
	public String getTmpFilePath() {
		return tmpFilePath;
	}

	public void setTmpFilePath(String tmpFilePath) {
		this.tmpFilePath = tmpFilePath;
	}
	private String tmpFilePath;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	public long getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(long timeLength) {
		this.timeLength = timeLength;
	}

	public String getGridMarkId() {
		return gridMarkId;
	}

	public void setGridMarkId(String gridMarkId) {
		this.gridMarkId = gridMarkId;
	}

}
