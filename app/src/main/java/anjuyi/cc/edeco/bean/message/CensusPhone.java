package anjuyi.cc.edeco.bean.message;

/**
 * 作者：Mr.Lee on 2016-10-13 11:18
 * 邮箱：569932357@qq.com
 */

public class CensusPhone {

    private String fileName;//存储文件名
    private String plateName="android";//平台
    private String userToken;//用户唯一标识符
    private String activityName;//界面名称
    private String methodName;//方法名
    private long timeStamp;//时间戳
    private String duration;//界面时长
    private String censusDate;//统计日期
    private String extra_1;//额外参数1
    private String extra_2;//额外参数2
    private String extra_3;//额外参数3


    public CensusPhone() {
    }

    public CensusPhone(String fileName, String plateName, String userToken, String activityName, String methodName, long timeStamp, String duration, String censusDate, String extra_1, String extra_2, String extra_3) {
        this.fileName = fileName;
        this.plateName = plateName;
        this.userToken = userToken;
        this.activityName = activityName;
        this.methodName = methodName;
        this.timeStamp = timeStamp;
        this.duration = duration;
        this.censusDate = censusDate;
        this.extra_1 = extra_1;
        this.extra_2 = extra_2;
        this.extra_3 = extra_3;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtra_1() {
        return extra_1;
    }

    public void setExtra_1(String extra_1) {
        this.extra_1 = extra_1;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCensusDate() {
        return censusDate;
    }

    public void setCensusDate(String censusDate) {
        this.censusDate = censusDate;
    }

    public String getExtra_2() {
        return extra_2;
    }

    public void setExtra_2(String extra_2) {
        this.extra_2 = extra_2;
    }

    public String getExtra_3() {
        return extra_3;
    }

    public void setExtra_3(String extra_3) {
        this.extra_3 = extra_3;
    }

    @Override
    public String toString() {
        return timeStamp + "\r\n"+
                "fileName=" + fileName + "---" +
                "平台名=" + plateName +"---" +
                "用户标识符=" + userToken + "---" +
                "界面名=" + activityName + "---" +
                "方法名=" + methodName + "---" +
                "界面时长=" + duration +"---" +
                "进行统计日期='" + censusDate + "---" +
                "额外参数1='" + extra_1 + "---" +
                "额外参数2='" + extra_2 + "---" +
                "额外参数3='" + extra_3 + "---" +
                "\r\n";
    }
}
