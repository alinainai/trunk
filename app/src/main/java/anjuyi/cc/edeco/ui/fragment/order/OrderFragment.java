package anjuyi.cc.edeco.ui.fragment.order;


import android.os.Bundle;
import android.view.View;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;

/**
 * 全部订单
 * 
 * @author W orderstatus 0待付款 1待服务 6已确认 7已取消 9失效 10完成评价
 * 
 */
public class OrderFragment extends BaseFragment {


	public static OrderFragment newInstance(String tag) {
		OrderFragment fragment = new OrderFragment();
		Bundle arguments = new Bundle();
		arguments.putString("TAG", tag);
		fragment.setArguments(arguments);
		return fragment;
	}


	@Override
	protected int initLayoutId() {
		return R.layout.fragment_test;
	}

	@Override
	public void initView() {

	}

	@Override
	public void setListener(View view, Bundle savedInstanceState) {

	}
	@Override
	public void initData(Bundle savedInstanceState) {

	}

}
