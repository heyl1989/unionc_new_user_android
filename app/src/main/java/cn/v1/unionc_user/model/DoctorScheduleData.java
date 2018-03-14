package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/8.
 */

public class DoctorScheduleData extends BaseData {


    /**
     * data : {"days":[{"ScheduleDate":"03-08","week":"星期四"},{"ScheduleDate":"03-09","week":"星期五"},{"ScheduleDate":"03-10","week":"星期六"},{"ScheduleDate":"03-11","week":"星期日"},{"ScheduleDate":"03-12","week":"星期一"},{"ScheduleDate":"03-13","week":"星期二"},{"ScheduleDate":"03-14","week":"星期三"}],"schedules":[]}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<DaysData> days;
        private List<Schedules> schedules;

        public List<DaysData> getDays() {
            return days;
        }

        public void setDays(List<DaysData> days) {
            this.days = days;
        }

        public List<Schedules> getSchedules() {
            return schedules;
        }

        public void setSchedules(List<Schedules> schedules) {
            this.schedules = schedules;
        }

        public static class DaysData {
            /**
             * ScheduleDate : 03-08
             * week : 星期四
             */

            private String ScheduleDate;
            private String week;

            public String getScheduleDate() {
                return ScheduleDate;
            }

            public void setScheduleDate(String ScheduleDate) {
                this.ScheduleDate = ScheduleDate;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }
        }

        public static class Schedules{

        }
    }
}
