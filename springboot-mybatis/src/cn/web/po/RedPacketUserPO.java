package cn.web.po;

public class RedPacketUserPO {

	private Integer id;
	private Integer redPacketId;
	private Integer userId;
	private double amount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(Integer redPacketId) {
		this.redPacketId = redPacketId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
