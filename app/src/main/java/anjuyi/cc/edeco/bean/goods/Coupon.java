package anjuyi.cc.edeco.bean.goods;
/** 
 * 优惠券
 * @author  liyue: 
 * @date 创建时间：2016年10月27日 下午8:05:00  
 * @parameter  
 * @return  
 *
 */
public class Coupon {
	
	private String coupon_id;// 优惠券ID
	private String discount_money;// 减多少钱
	private String usable_money;// 满多少钱可用
	private String start_time;// 开始时间
	private String end_time;// 结束时间
	private String use_range;// 使用范围
	private String isReceive;//用户领取状态0：可领取 1：不能领取
	private String type;//类型
	private String name;//名称
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getDiscount_money() {
		return discount_money;
	}
	public void setDiscount_money(String discount_money) {
		this.discount_money = discount_money;
	}
	public String getUsable_money() {
		return usable_money;
	}
	public void setUsable_money(String usable_money) {
		this.usable_money = usable_money;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getUse_range() {
		return use_range;
	}
	public void setUse_range(String use_range) {
		this.use_range = use_range;
	}
	public String getIsReceive() {
		return isReceive;
	}
	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
