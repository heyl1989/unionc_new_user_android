package cn.v1.unionc_user.network_frame;

import java.util.HashMap;
import cn.v1.unionc_user.data.Common;
import cn.v1.unionc_user.model.RongTokenData;
import cn.v1.unionc_user.utils.SHA1Util;
import io.reactivex.Observable;

/**
 * Created by qy on 2018/2/3.
 */

public class RongAPIPackage {

    private static String appkey = Common.RONG_AK;
    private static String nonce = String.valueOf((int) (Math.random() * 1000000));
    private static String timestamp = String.valueOf(System.currentTimeMillis());
    private static String signature = SHA1Util.encryptToSHA(new StringBuilder(Common.RONG_SK)
            .append(nonce)
            .append(timestamp).toString());

    /**
     * 融云请求头
     *
     * @return
     */
    private static HashMap<String, String> rongHeaders() {
        HashMap<String, String> rongHeaders = new HashMap<>();
        rongHeaders.put("RC-App-Key", appkey);
        rongHeaders.put("RC-Nonce", nonce);
        rongHeaders.put("RC-Timestamp", timestamp);
        rongHeaders.put("RC-Signature", signature);
        return rongHeaders;
    }

    /**
     * 融云获取Token
     *
     * @param userId
     * @param name
     * @param portraitUri
     * @return
     */
    public static Observable<RongTokenData> getRongToken(String userId, String name, String portraitUri) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("name",name);
        params.put("portraitUri",portraitUri);
        return ConnectHttp.getRongAPI().getRongToken(rongHeaders(), params);
    }

}
