package batteria.bubbleworld.entity;

import java.util.Date;

public class Message {
	private int mid;
	private String content;
	private Date OccurDate;
	private int speakerid;
	private int receiverid;
	private int state;
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getOccurDate() {
		return OccurDate;
	}
	public void setOccurDate(Date datetime) {
		this.OccurDate = datetime;
	}
	public int getSpeakerid() {
		return speakerid;
	}
	public void setSpeakerid(int speakerid) {
		this.speakerid = speakerid;
	}
	public int getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(int receiverid) {
		this.receiverid = receiverid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
