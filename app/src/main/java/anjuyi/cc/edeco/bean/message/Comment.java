package anjuyi.cc.edeco.bean.message;

import java.util.List;

/** 
 *
 * @author  liyue: 
 * @date 创建时间：2016年10月27日 下午6:12:12  
 * @parameter  
 * @return  
 *
 */
public class Comment {
	
	  private String comment_id;//评论id
      private String gid; //商品ID
      private String uid; //用户Id
      private String username;  //用户名
      private String message; //评论信息
      private String review_time; //更新时间
      private String praise_num;  //几颗星
      private String headurl; //头像地址
      private List<String> imgUrls; //最多上传   张
      

}
