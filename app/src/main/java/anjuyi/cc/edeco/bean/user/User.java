package anjuyi.cc.edeco.bean.user;

import java.io.Serializable;

/**
 * Created by ly on 2016/5/30 12:15.
 */
public class User implements Serializable{

    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String userId; //用户ID
    private String token;
    private String isCertInfo; //是否填写身份信息 0未填写
    private String isAcceptInfo; //是否接收消息  0不接收
    private String referralCode; //推荐吗
    private boolean loanStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIsCertInfo() {
        return isCertInfo;
    }

    public void setIsCertInfo(String isCertInfo) {
        this.isCertInfo = isCertInfo;
    }

    public String getIsAcceptInfo() {
        return isAcceptInfo;
    }

    public void setIsAcceptInfo(String isAcceptInfo) {
        this.isAcceptInfo = isAcceptInfo;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(boolean loanStatus) {
        this.loanStatus = loanStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", isCertInfo='" + isCertInfo + '\'' +
                ", isAcceptInfo='" + isAcceptInfo + '\'' +
                ", referralCode='" + referralCode + '\'' +
                ", loanStatus=" + loanStatus +
                '}';
    }
}
