package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/15.
 */

public class ClinicActivityData extends BaseData {


    /**
     * data : {"activities":[{"ActivityId":"16","Digest":"心脑血管","EndTime":"2018-03-17 14:32:00.0","ImagePath":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/9/6/7eca87b9-3cb7-42ec-82e3-a7163fcb5dc4_timgY24TP6NU.jpg","IsSignIn":"0","PresentDesc":"蛤蟆","StartTime":"2018-03-16 14:32:04.0","Title":"测试活动"},{"ActivityId":"2","Digest":"心脑血管预防","EndTime":"2018-03-30 15:00:00.0","ImagePath":"","IsSignIn":"0","PresentDesc":"鸡蛋2斤","StartTime":"2018-03-28 00:00:00.0","Title":"健康义诊"},{"ActivityId":"9","Digest":"测试","EndTime":"2018-04-12 16:39:37.0","ImagePath":"http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/7/3/982a65f1-bc76-4e62-9eb7-7156aad75817_33.jpg","IsSignIn":"0","PresentDesc":"测试礼品","StartTime":"2018-04-02 16:39:35.0","Title":"测试"}]}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<ActivitiesData> activities;

        public List<ActivitiesData> getActivities() {
            return activities;
        }

        public void setActivities(List<ActivitiesData> activities) {
            this.activities = activities;
        }

        public static class ActivitiesData {
            /**
             * ActivityId : 16
             * Digest : 心脑血管
             * EndTime : 2018-03-17 14:32:00.0
             * ImagePath : http://192.168.21.93:8080/unionWeb/image/webServer/compress/68/9/6/7eca87b9-3cb7-42ec-82e3-a7163fcb5dc4_timgY24TP6NU.jpg
             * IsSignIn : 0
             * PresentDesc : 蛤蟆
             * StartTime : 2018-03-16 14:32:04.0
             * Title : 测试活动
             */

            private String ActivityId;
            private String Digest;
            private String EndTime;
            private String ImagePath;
            private String IsSignIn;
            private String PresentDesc;
            private String StartTime;
            private String Title;
            private String ActionAddr;

            public String getActionAddr() {
                return ActionAddr;
            }

            public void setActionAddr(String actionAddr) {
                ActionAddr = actionAddr;
            }

            public String getActivityId() {
                return ActivityId;
            }

            public void setActivityId(String ActivityId) {
                this.ActivityId = ActivityId;
            }

            public String getDigest() {
                return Digest;
            }

            public void setDigest(String Digest) {
                this.Digest = Digest;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public String getImagePath() {
                return ImagePath;
            }

            public void setImagePath(String ImagePath) {
                this.ImagePath = ImagePath;
            }

            public String getIsSignIn() {
                return IsSignIn;
            }

            public void setIsSignIn(String IsSignIn) {
                this.IsSignIn = IsSignIn;
            }

            public String getPresentDesc() {
                return PresentDesc;
            }

            public void setPresentDesc(String PresentDesc) {
                this.PresentDesc = PresentDesc;
            }

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }
        }
    }
}
