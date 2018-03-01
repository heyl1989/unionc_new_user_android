package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/2/28.
 */

public class TIMSigData extends BaseData {


    /**
     * data : {"tls":"eJxNTt1OgzAUfpfeYrRQGGCyC8QuEbdpxtjwirTQ4ZkIlZY5Y3z3AWGJ5*58-79ou4xvVfGRMSmhQPfItDHGM*ISF92MpDhLaEXGDlq0A*84jtVLJna0ZUxnpB3cV1jDpxjFpu9h37LtCYdC1BoOMEapTrPmmpPnTVfrTP9I8S9HQdl-K5qET9R*y*PkZBwNo4Gk7DZrozKWL-57SBe8*vZwzF7Dfbi7i3YB0ICAVXGX*p5MzcfnRRBtqpTjr-VKqgd*5KmcsciL9tuyyOdT2Um0Cpq6L7Rwv9wieDj0dwFZ0FZu"}
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
         * tls : eJxNTt1OgzAUfpfeYrRQGGCyC8QuEbdpxtjwirTQ4ZkIlZY5Y3z3AWGJ5*58-79ou4xvVfGRMSmhQPfItDHGM*ISF92MpDhLaEXGDlq0A*84jtVLJna0ZUxnpB3cV1jDpxjFpu9h37LtCYdC1BoOMEapTrPmmpPnTVfrTP9I8S9HQdl-K5qET9R*y*PkZBwNo4Gk7DZrozKWL-57SBe8*vZwzF7Dfbi7i3YB0ICAVXGX*p5MzcfnRRBtqpTjr-VKqgd*5KmcsciL9tuyyOdT2Um0Cpq6L7Rwv9wieDj0dwFZ0FZu
         */

        private String tls;

        public String getTls() {
            return tls;
        }

        public void setTls(String tls) {
            this.tls = tls;
        }
    }
}
