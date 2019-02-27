package com.kh.spring.board.model.vo;

import java.util.Date;

public class Attachment {
	private int attachmentNo;
	private int boardNo;
	private String originalFileName;
	private String reNamedFileName;
	private Date uploadDate;
	private int downLoadCount;
	private String status;
	public Attachment() {
		// TODO Auto-generated constructor stub
	}
	Attachment(int attachmentNo, int boardNo, String originalFileName, String reNamedFileName, Date uploadDate,
			int downLoadCount, String status) {
		super();
		this.attachmentNo = attachmentNo;
		this.boardNo = boardNo;
		this.originalFileName = originalFileName;
		this.reNamedFileName = reNamedFileName;
		this.uploadDate = uploadDate;
		this.downLoadCount = downLoadCount;
		this.status = status;
	}
	public int getAttachmentNo() {
		return attachmentNo;
	}
	public void setAttachmentNo(int attachmentNo) {
		this.attachmentNo = attachmentNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getReNamedFileName() {
		return reNamedFileName;
	}
	public void setReNamedFileName(String reNamedFileName) {
		this.reNamedFileName = reNamedFileName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public int getDownLoadCount() {
		return downLoadCount;
	}
	public void setDownLoadCount(int downLoadCount) {
		this.downLoadCount = downLoadCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Attachment [attachmentNo=" + attachmentNo + ", boardNo=" + boardNo + ", originalFileName="
				+ originalFileName + ", reNamedFileName=" + reNamedFileName + ", uploadDate=" + uploadDate
				+ ", downLoadCount=" + downLoadCount + ", status=" + status + "]";
	}
		
}
