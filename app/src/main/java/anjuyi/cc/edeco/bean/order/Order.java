package anjuyi.cc.edeco.bean.order;

import java.util.List;

import anjuyi.cc.edeco.bean.goods.Coupon;
import anjuyi.cc.edeco.bean.goods.Goods;
import anjuyi.cc.edeco.bean.address.Address;

/** 
 * 订单实体类
 * @author  liyue: 
 * @date 创建时间：2016年10月27日 下午6:26:10  
 * @parameter  
 * @return  
 *
 */
public class Order {
	
	private String oid;
	private String ordernum;
	private Goods goods ;
	private Address address;
	private List<Coupon> coupon;  //优惠券
	
	

}
