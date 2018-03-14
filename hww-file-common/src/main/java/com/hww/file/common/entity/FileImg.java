package com.hww.file.common.entity;

import com.hww.base.common.entity.AbsBaseEntity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
/**
 * 图像文件
 *
 */
@Entity
@Table(name="file_img")
public class FileImg extends AbsBaseEntity<Long>{
	private Long fileId;//统一的fileid 主键和外键
	private Long memberId;//作者id 会员id
	private Long setId;//统一的集合id 与 fileset的主键一致
	
	private Integer imgFileWidth;//图片宽度
	private Integer imgFileHight;//图片高度
	
	private Short imgType;//与作者类型一样
	
	private Integer displayOrder;//顺序 在图集列表里
	
	private FileInfo fileInfo;

	@Id
	@GeneratedValue(generator = "snowFlake")
    @GenericGenerator(name = "snowFlake", strategy = "com.hww.framework.common.idgen.SnowFlakeIdGenerator")
	@Column(name="file_id")
	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	@Column(name="member_id")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name="set_id")
	public Long getSetId() {
		return setId;
	}

	public void setSetId(Long setId) {
		this.setId = setId;
	}

	@Column(name="img_file_with")
	public Integer getImgFileWidth() {
		return imgFileWidth;
	}

	public void setImgFileWidth(Integer imgFileWidth) {
		this.imgFileWidth = imgFileWidth;
	}

	@Column(name="img_file_hight")
	public Integer getImgFileHight() {
		return imgFileHight;
	}

	public void setImgFileHight(Integer imgFileHight) {
		this.imgFileHight = imgFileHight;
	}

	@Column(name="img_type")
	public Short getImgType() {
		return imgType;
	}

	public void setImgType(Short imgType) {
		this.imgType = imgType;
	}

	@Column(name="display_order")
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}


	@Override
	@Transient
	public Long getId() {
		// TODO Auto-generated method stub
		return fileId;
	}
	
}
