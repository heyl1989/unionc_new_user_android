package cn.v1.unionc_user.utils;

import java.util.Comparator;

import cn.v1.unionc_user.model.PersonBean;

public class PinyinComparator implements Comparator<PersonBean> {

	@Override
	public int compare(PersonBean lhs, PersonBean rhs) {
		// TODO Auto-generated method stub
		return sort(lhs, rhs);
	}

	private int sort(PersonBean lhs, PersonBean rhs) {
		// ��ȡasciiֵ
		int lhs_ascii = lhs.getFirstPinYin().toUpperCase().charAt(0);
		int rhs_ascii = rhs.getFirstPinYin().toUpperCase().charAt(0);
		// �ж���������ĸ����������ĸ֮��
		if (lhs_ascii < 65 || lhs_ascii > 90)
			return 1;
		else if (rhs_ascii < 65 || rhs_ascii > 90)
			return -1;
		else
			return lhs.getPinYin().compareTo(rhs.getPinYin());
	}

}
