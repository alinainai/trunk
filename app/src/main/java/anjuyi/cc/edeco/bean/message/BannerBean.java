package anjuyi.cc.edeco.bean.message;

/**
 * 作者：Mr.Lee on 2016-8-5 13:59
 * 邮箱：569932357@qq.com
 */
public class BannerBean {

    public BannerBean(String title, String info, String imgaddr, String extra) {
        this.title = title;
        this.info = info;
        this.imgaddr = imgaddr;
        this.extra = extra;
    }

    public BannerBean() {
    }

    private String title;
    private String info;
    private String imgaddr;
    private String extra;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgaddr() {
        return imgaddr;
    }

    public void setImgaddr(String imgaddr) {
        this.imgaddr = imgaddr;
    }
}
