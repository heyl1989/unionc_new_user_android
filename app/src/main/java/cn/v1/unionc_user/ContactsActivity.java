package cn.v1.unionc_user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.gjiazhe.wavesidebar.WaveSideBar;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.v1.unionc_user.model.Contact;
import cn.v1.unionc_user.ui.adapter.ContactsAdapter;
import cn.v1.unionc_user.ui.base.BaseActivity;

public class ContactsActivity extends BaseActivity {

    @Bind(R.id.rv_contacts)
    RecyclerView rvContacts;
    @Bind(R.id.side_bar)
    WaveSideBar sideBar;

    private ArrayList<Contact> contacts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        contacts.addAll(Contact.getChineseContacts());
    }
//

//    private List<Contact> getData(String[] data) {
//        List<Contact> listarray = new ArrayList<Contact>();
//        for (int i = 0; i < data.length; i++) {
//            String pinyin = PingYinUtil.getPingYin(data[i]);
//            String Fpinyin = pinyin.substring(0, 1).toUpperCase();
//
//            Contact person = new Contact();
//            person.setName(data[i]);
//            person.setIndex(pinyin);
//            // 正则表达式，判断首字母是否是英文字母
//            if (Fpinyin.matches("[A-Z]")) {
//                person.setIndex(Fpinyin);
//            } else {
//                person.setIndex("#");
//            }
//
//            listarray.add(person);
//        }
//          Collections.sort(listarray, new PinyinComparator());
//        return listarray;
//
//    }

    private void initView() {

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(new ContactsAdapter(contacts));

        sideBar.setPosition(WaveSideBar.POSITION_RIGHT);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<contacts.size(); i++) {
                    if (contacts.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }
}
