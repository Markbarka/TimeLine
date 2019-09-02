package com.example.timeline.Time;

/**

 * 存储数据

 */



public class TimeData {
    private String posttime;
    private String title;
    private int nowTime;

    public TimeData(String posttime,String title) {
        this.title = title;
        this.posttime = posttime;
    }

    public TimeData(int nowTime,String title){
        this.title = title;
        this.nowTime= nowTime;
    }
    public int getNowTime(){
        return nowTime;
    }

    public void setNowTime(int nowTime){
        this.nowTime = nowTime;
    }
    public String getPosttime() {

        return posttime;

    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
