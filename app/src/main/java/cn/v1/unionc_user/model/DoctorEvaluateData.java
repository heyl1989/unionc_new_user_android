package cn.v1.unionc_user.model;

import java.util.List;

/**
 * Created by qy on 2018/3/9.
 */

public class DoctorEvaluateData extends BaseData {


    /**
     * data : {"evaluates":[{"Content":"阿奎咯","CreatedTime":"2018-03-09 07:36:40","HeadImage":"http://192.168.21.93:8080/unionWeb/image/unionWeb/original/158/3/9/997a4619-2157-4600-be1f-00c1eeedd0eb_temphead.jpg","Telphone":"13810205545","UserName":"测试一下"}]}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        private List<EvaluatesData> evaluates;

        public List<EvaluatesData> getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(List<EvaluatesData> evaluates) {
            this.evaluates = evaluates;
        }

        public static class EvaluatesData {
            /**
             * Content : 阿奎咯
             * CreatedTime : 2018-03-09 07:36:40
             * HeadImage : http://192.168.21.93:8080/unionWeb/image/unionWeb/original/158/3/9/997a4619-2157-4600-be1f-00c1eeedd0eb_temphead.jpg
             * Telphone : 13810205545
             * UserName : 测试一下
             */

            private String Content;
            private String CreatedTime;
            private String HeadImage;
            private String Telphone;
            private String UserName;

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getCreatedTime() {
                return CreatedTime;
            }

            public void setCreatedTime(String CreatedTime) {
                this.CreatedTime = CreatedTime;
            }

            public String getHeadImage() {
                return HeadImage;
            }

            public void setHeadImage(String HeadImage) {
                this.HeadImage = HeadImage;
            }

            public String getTelphone() {
                return Telphone;
            }

            public void setTelphone(String Telphone) {
                this.Telphone = Telphone;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }
        }
    }
}
