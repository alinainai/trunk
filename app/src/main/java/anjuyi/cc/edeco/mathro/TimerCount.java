package anjuyi.cc.edeco.mathro;



public class TimerCount {


        //时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
        public static String showTimeCount(long time) {
            System.out.println("time="+time);
            if(time >= 360000000){
                return "00:00:00";
            }
            String timeCount = "";
            long hourc = time/3600000;
            String hour = "0" + hourc;
            System.out.println("hour="+hour);
            hour = hour.substring(hour.length()-2, hour.length());
            System.out.println("hour2="+hour);

            long minuec = (time-hourc*3600000)/(60000);
            String minue = "0" + minuec;
            System.out.println("minue="+minue);
            minue = minue.substring(minue.length()-2, minue.length());
            System.out.println("minue2="+minue);

            long secc = (time-hourc*3600000-minuec*60000)/1000;
            String sec = "0" + secc;
            System.out.println("sec="+sec);
            sec = sec.substring(sec.length()-2, sec.length());
            System.out.println("sec2="+sec);
            timeCount = hour + ":" + minue + ":" + sec;
            System.out.println("timeCount="+timeCount);
            return timeCount;
        }

}