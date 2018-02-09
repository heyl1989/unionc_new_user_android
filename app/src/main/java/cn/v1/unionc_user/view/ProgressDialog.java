package cn.v1.unionc_user.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by lawrence on 17/10/12.
 */
public class ProgressDialog extends Dialog {

    private Context context;

    public ProgressDialog(Context context) {
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}