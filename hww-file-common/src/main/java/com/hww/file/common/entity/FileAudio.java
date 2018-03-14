package com.hww.file.common.entity;

import javax.persistence.*;

import com.hww.base.common.entity.AbsBaseEntity;
/**
 * 音频文件
 *
 */
@Entity
@Table(name="file_audio")
public class FileAudio extends AbsBaseEntity<Long>{
	private Long fileId;//统一的fileid 主键和外键
	private Long memberId;//作者id 会员id（与SysFile里重复）
	private Long setId;//图集id 与 mediaSet的主键一致
	
	private FileImg cover;//封面,每一个视频均有自己的方面
	
	private Integer length;//音视频时长 单位秒
	
	private FileInfo fileInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToOne
	@JoinColumn(name="cover_file_id")
	public FileImg getCover() {
		return cover;
	}

	public void setCover(FileImg cover) {
		this.cover = cover;
	}

	@Column(name="length")
	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
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
