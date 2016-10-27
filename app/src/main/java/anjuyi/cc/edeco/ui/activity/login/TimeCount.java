package anjuyi.cc.edeco.ui.activity.login;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.TextView;

import anjuyi.cc.edeco.util.Colors;

/**
 * 作者：Mr.Lee on 2016-8-2 11:40
 * 邮箱：569932357@qq.com
 */
public class TimeCount  extends AsyncTask<Void, Void, Void> {

        //初始化倒计时时间
        private static final int COUNTTIEM=60;
        private int time=COUNTTIEM;
        private TextView mButton;
        private static TimeCount task;
        private static boolean isNew;
        private boolean isRun;
        private TimeCount(){}

        public static TimeCount getInstence(){
            if(task==null){
                synchronized (TimeCount.class) {
                    if(task==null){
                        task=new TimeCount();
                        isNew=true;
                    }
                }
            }
            return task;
        }

        public void startTimer(TextView button){
            this.mButton=button;
            if(isNew){
                execute();
            }
        }

        @Override
        protected void onPreExecute() {
            time=COUNTTIEM;
            isNew=false;
            isRun=true;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                for (; time>=0; time--) {
                    publishProgress();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {

            if(mButton !=null){
                mButton.setText(String.format("%ds", time));
                mButton.setTextColor(Color.rgb(0xff,0xb6,0xc1));
                if(mButton.isEnabled()){
                    mButton.setEnabled(false);
                }
            }
        }
        private void cancel(){
            if(task!=null){
                isRun=false;
                super.cancel(true);
                task=null;
                isNew=true;
            }
        }
        @Override
        protected void onPostExecute(Void result) {
            end();
        }
        public void end(){
            if(mButton!=null){
                mButton.setEnabled(true);
                mButton.setTextColor(Colors.WHITE);
                mButton.setText("获取验证码");
            }
            cancel();
        }
        public  boolean isRun() {
            return isRun;
        }

}
