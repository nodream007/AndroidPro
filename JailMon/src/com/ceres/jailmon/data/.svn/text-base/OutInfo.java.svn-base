package com.ceres.jailmon.data;

import android.graphics.Bitmap;

public class OutInfo extends BaseData {
	String pId;
	String txId;
	String time_begin;
	String time_end;
	String room;
	String prisoner;
	String police;
	String type;
	String txroom;
	String hjroom;
	String comment;
	String desp;
	Bitmap photo;
	String txFlag;
	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}
	public String getTxFlag() {
		return txFlag;
	}

	public void setTxFlag(String txFlag) {
		this.txFlag = txFlag;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {

		if (desp == null)
			desp = String.format("%s - %s, %s, %s, %s, %s, %s, %s", time_begin,
					time_end, room, prisoner, police, type, txroom, comment);

		return desp;
	}

	@Override
	public String getHeadline() {
		if (m_strHeadline == null) {
			m_strHeadline = String.format("人员：%s", prisoner);
		}

		return m_strHeadline;
	}

	@Override
	public String getContent() {
		if (m_strContent == null) {
			m_strContent = String.format("处置民警：%s\n提讯房间：%s\n备注说明：%s\n", police,
					txroom, comment);
		}

		return m_strContent;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getTime_begin() {
		return time_begin;
	}

	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPrisoner() {
		return prisoner;
	}

	public void setPrisoner(String prisoner) {
		this.prisoner = prisoner;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTxroom() {
		return txroom;
	}

	public void setTxroom(String txroom) {
		this.txroom = txroom;
	}

	public String getHjroom() {
		return hjroom;
	}

	public void setHjroom(String hjroom) {
		this.hjroom = hjroom;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}
}
