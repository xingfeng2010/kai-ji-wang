package com.wenyu.kaijiw.fragment;

import java.util.List;

import com.baoyz.swipemenulistview.BaoyzApplication;
import com.wenyu.Data.Customer;
import com.wenyu.db.DBManager;
import com.wenyu.kaijiw.LoginActivity;
import com.wenyu.kaijiw.MainActivity;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class MyBaseFragment extends Fragment {

	protected void exitButtonClick() {
		DBManager mgr = new DBManager(getActivity());
		List<Customer> query = mgr.query();
		for (int i = 0; i < query.size(); i++) {
			Customer customer = query.get(i);
			customer.setActive(0);
			mgr.updateActive(customer);
		}
		BaoyzApplication.getInstance().isLogined = false;
		getActivity().finish();
		Intent intent5 = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent5);
	}
}
