jQuery.Huifold = function(obj,obj_c,speed,obj_type,Event){
	if(obj_type == 2){
		$(obj+":first").find("i").removeClass("weui_icon_down1").addClass("weui_icon_up1");
		$(obj_c+":first").show();
	}
	$(obj).bind(Event,function(){
		if($(this).next().is(":visible")){
			if(obj_type == 2){
				return false;
			}
			else{
				$(this).next().slideUp(speed).end().removeClass("selected");
				$(this).find("i").removeClass("weui_icon_up1").addClass("weui_icon_down1");
			}
		}
		else{
			if(obj_type == 3){
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("i").removeClass("weui_icon_up1").addClass("weui_icon_down1");
			}else{
				$(obj_c).slideUp(speed);
				$(obj).removeClass("selected");
				$(obj).find("i").removeClass("weui_icon_up1").addClass("weui_icon_down1");
				$(this).next().slideDown(speed).end().addClass("selected");
				$(this).find("i").removeClass("weui_icon_down1").addClass("weui_icon_up1");
			}
		}
	});
}

        $(function () {
            $.Huifold(".list .item .weui_cell", ".list .item .info", "fast", 1, "click"); /*5个参数顺序不可打乱，分别是：相应区,隐藏显示的内容,速度,类型,事件*/
        });


 var layer = {
        msg:function(text,time,fun){
            if(!text){
                return false;
            }
            if(!time){
                time = 1000
            }
            var str = $("<div class='popMessage'><div class='mobileMessage'>"+text+"</div></div>");
            str.appendTo("body");
            $('.popMessage').css({
                position:'fixed',
                width:'100%',
                height:'100%',
                left:'0',
                top:'0',
                'z-index':'88888'
            })
            $('.mobileMessage').css({
                //width:'1.4rem',
                'min-width': '2rem',
                'max-width': '90%',
                height:'auto',
                position:'absolute',
                top:'50%',
                left:'50%',
                padding:'5px',
                'border-radius':'5px',
                background:'none no-repeat center center rgba(0,0,0,.5)',
                //'background-size':'38% 48%',
                overflow:'hidden',
                'text-align':'center',
                color:'#fff',
                'font-size':'14px',
                'z-index':'99999',
                '-webkit-transform': 'translate(-50%,-50%)',
                '-moz-transform': 'translate(-50%,-50%)',
                'transform': 'translate(-50%,-50%)'
            });
            var offMessage = function remove() {
                $('.popMessage').remove();
                //fun;
                if (typeof(fun)=="function") {
                    fun();
                }else {
                    return false;
                }
            }
            setTimeout(offMessage,time);
        }
    };
function baseBianMa(str){
	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var base64DecodeChars = new Array(
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
               -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
               52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
                  -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
                 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
                  -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
	
			var out, i, len;
              var c1, c2, c3;
              len = str.length;
              i = 0;
              out = "";
              while(i < len) {
                  c1 = str.charCodeAt(i++) & 0xff;
                  if(i == len)
                    {
                          out += base64EncodeChars.charAt(c1 >> 2);
                         out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                         out += "==";
                         break;
                     }
                  c2 = str.charCodeAt(i++);
                  if(i == len)
                      {
                          out += base64EncodeChars.charAt(c1 >> 2);
                         out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
                          out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                          out += "=";
                         break;
                      }
                c3 = str.charCodeAt(i++);
                 out += base64EncodeChars.charAt(c1 >> 2);
                 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
                 out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
                 out += base64EncodeChars.charAt(c3 & 0x3F);
                  }
              return out;
		
};