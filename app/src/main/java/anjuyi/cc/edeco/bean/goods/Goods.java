package anjuyi.cc.edeco.bean.goods;
/** 
 *	 订单里面的商品详情
 * @author  liyue: 
 * @date 创建时间：2016年10月27日 下午7:33:41  
 * @parameter  
 * @return  
 *
 */
public class Goods {
	
	
	private String gid;//商品id
	private String goodsname;//商品名称
	private double price;//单价
	private String optional_id;//可选规格id
	private int goodscount; //商品数量
	private String picurl;  //商品地址
	private String allprice;  //勾选完可选项后总价格
	private String shop_id;  //店铺
	private String paycount;//付款人数
	private String present;//介绍
	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getPaycount() {
		return paycount;
	}

	public void setPaycount(String paycount) {
		this.paycount = paycount;
	}

	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOptional_id() {
		return optional_id;
	}
	public void setOptional_id(String optional_id) {
		this.optional_id = optional_id;
	}
	public int getGoodscount() {
		return goodscount;
	}
	public void setGoodscount(int goodscount) {
		this.goodscount = goodscount;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getAllprice() {
		return allprice;
	}
	public void setAllprice(String allprice) {
		this.allprice = allprice;
	}
	
	
	

}
