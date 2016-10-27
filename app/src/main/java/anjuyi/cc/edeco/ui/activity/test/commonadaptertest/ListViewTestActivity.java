package anjuyi.cc.edeco.ui.activity.test.commonadaptertest;

import android.os.Bundle;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;

public class ListViewTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_list_view_test;
    }


    /**
     * CommonAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     *
     * 多种类型的type
     */
//    private CommonAdapter<DemoModel> test01(List<DemoModel> data) {
//        return new CommonAdapter<DemoModel>(data, 3) {
//
//            @Override
//            public Object getItemType(DemoModel demoModel) {
//                return demoModel.type;
//            }
//
//            @NonNull
//            @Override
//            public AdapterItem createItem(Object type) {
//                switch (((String) type)) {
//                    case "text":
//                        return new TextItem();
//                    case "button":
//                        return new ButtonItem();
//                    case "image":
//                        return new ImageItem();
//                    default:
//                        throw new IllegalArgumentException("不合法的type");
//                }
//            }
//        };
//    }

    /**
     * CommonPagerAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     *
     * 一种类型的type
     */
//    private CommonAdapter<DemoModel> test02(List<DemoModel> data) {
//        return new CommonAdapter<DemoModel>(data, 1) {
//
//            @NonNull
//            @Override
//            public AdapterItem createItem(Object type) {
//                // 如果就一种，那么直接return一种类型的item即可。
//                return new TextItem();
//            }
//        };
//    }

    /**
     * CommonAdapter的类型和item的类型是不一致的
     * 这里的adapter的类型是{ DemoModel}，但item的类型是Integer.
     * 所以需要调用{ IAdapter#getConvertedData(Object, Object)}方法，来进行数据的转换
     *
     * 多种类型的type
     */
//    private CommonAdapter<DemoModel> test03(List<DemoModel> data) {
//        return new CommonAdapter<DemoModel>(data, 3) {
//
//            @Override
//            public Object getItemType(DemoModel demoModel) {
//                return demoModel.type;
//            }
//
//            @NonNull
//            @Override
//            public AdapterItem createItem(Object type) {
//                switch (((String) type)) {
//                    case "text":
//                        return new TextItem();
//                    case "button":
//                        return new ButtonItem();
//                    case "image":
//                        return new ImageItem2(ListViewTestActivity.this, new ImageItem2.ImageItemCallback() {
//                            @Override
//                            public void onImageClick(View view) {
//                                Toast.makeText(ListViewTestActivity.this, "click", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    default:
//                        throw new IllegalArgumentException("不合法的type");
//                }
//            }
//
//            /**
//             * 做数据的转换，这里算是数据的精细拆分
//             */
//            @NonNull
//            @Override
//            public Object getConvertedData(DemoModel data, Object type) {
//                if (type.equals("image")) {
//                    return Integer.valueOf(data.content); // String -> Integer
//                } else {
//                    return data;
//                }
//            }
//        };
//    }


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }
}
