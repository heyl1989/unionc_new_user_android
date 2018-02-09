package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/9.
 */

public class LoginData extends BaseData {


    /**
     * data : {"token":"dR4w/RlFdcoTcsz2fSUBzgSalMHVJiyEnJT1f1HgsHA="}
     */

    private DataData data;

    public DataData getData() {
        return data;
    }

    public void setData(DataData data) {
        this.data = data;
    }

    public static class DataData {
        /**
         * token : dR4w/RlFdcoTcsz2fSUBzgSalMHVJiyEnJT1f1HgsHA=
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
