package cn.v1.unionc_user.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjz on 9/3/16.
 */
public class Contact {
    private String index;
    private String name;

    public Contact(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Contact> getChineseContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("B", "白虎"));
        contacts.add(new Contact("C", "常羲"));
        contacts.add(new Contact("C", "嫦娥"));
        contacts.add(new Contact("E", "二郎神"));
        contacts.add(new Contact("F", "伏羲"));
        contacts.add(new Contact("G", "观世音"));
        contacts.add(new Contact("J", "精卫"));
        contacts.add(new Contact("K", "夸父"));
        contacts.add(new Contact("N", "女娲"));
        contacts.add(new Contact("N", "哪吒"));
        contacts.add(new Contact("P", "盘古"));
        contacts.add(new Contact("Q", "青龙"));
        contacts.add(new Contact("R", "如来"));
        contacts.add(new Contact("S", "孙悟空"));
        contacts.add(new Contact("S", "沙僧"));
        contacts.add(new Contact("S", "顺风耳"));
        contacts.add(new Contact("T", "太白金星"));
        contacts.add(new Contact("T", "太上老君"));
        contacts.add(new Contact("X", "羲和"));
        contacts.add(new Contact("X", "玄武"));
        contacts.add(new Contact("Z", "猪八戒"));
        contacts.add(new Contact("Z", "朱雀"));
        contacts.add(new Contact("Z", "祝融"));
        return contacts;
    }

}
