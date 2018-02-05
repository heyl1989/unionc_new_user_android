package cn.v1.unionc_user.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.TimePickerView;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qy on 2018/2/5.
 */

public abstract class TimePicker {

    /**
     * 时间选择器类型
     */
    public static final int YEAR_MONTH_DATE = 1;
    public static final int MONTH_DATE = 2;
    public static final int HOUR_MINUTE_SECOND = 3;
    public static final int HOUR_MINUTE = 4;

    /**
     * 屏蔽当前日期之前或者之后日期
     */
    public static final int SHIELD_PREVIOUS = 5;
    public static final int SHIELD_AFTER = 6;


    private Context context;
    private boolean[] type = new boolean[6];
    private Calendar selectedDate = Calendar.getInstance();
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();


    public TimePicker(Context context, int type) {
        this.context = context;
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        startDate.set(selectedDate.getTime().getYear() - 10 + 1900, 0, 23);
        endDate.set(selectedDate.getTime().getYear() + 10 + 1900, 11, 28);
        initTimePicker(type);
    }

    public TimePicker(Context context, int type, int shield) {
        this.context = context;
        if(shield == SHIELD_PREVIOUS){
            startDate = selectedDate;
            endDate.set(selectedDate.getTime().getYear() + 30 + 1900, 11, 28);
        }
        if(shield == SHIELD_AFTER){
            endDate = selectedDate;
            startDate.set(selectedDate.getTime().getYear() - 120 + 1900, 0, 23);
        }
        initTimePicker(type);

    }

    private void initTimePicker(int type) {
        if (type == YEAR_MONTH_DATE) {
            this.type = new boolean[]{true, true, true, false, false, false};
        }
        if (type == MONTH_DATE) {
            this.type = new boolean[]{false, true, true, false, false, false};
        }
        if (type == HOUR_MINUTE_SECOND) {
            this.type = new boolean[]{false, false, false, true, true, true};
        }
        if (type == HOUR_MINUTE) {
            this.type = new boolean[]{false, false, false, true, true, false};
        }
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                onTimeSelected(date);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(this.type)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();

        pvTime.show();
    }


    public abstract void onTimeSelected(Date date);

}
