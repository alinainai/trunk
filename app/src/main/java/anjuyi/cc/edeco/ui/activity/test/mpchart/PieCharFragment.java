package anjuyi.cc.edeco.ui.activity.test.mpchart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import butterknife.BindView;

/**
 * Created by Think on 2015/11/29.
 */
public class PieCharFragment extends BaseFragment {


    @BindView(R.id.spread_pie_chart)
    PieChart spreadPieChart;
    protected boolean mIsDataInitiated;

    public static PieCharFragment newInstance(String tag) {
        PieCharFragment fragment = new PieCharFragment();
        Bundle arguments = new Bundle();
        arguments.putString("TAG", tag);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected int initLayoutId() {
        return R.layout.pie_chart;
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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser  && !mIsDataInitiated){
            showChart(spreadPieChart,null);
            getPieData(4, 100);
            mIsDataInitiated=true;
        }
    }
    private void showChart(PieChart mChart, PieData pieData) {

        mChart.setUsePercentValues(true);//显示成百分比
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);//饼状图中间可以添加文字
        mChart.setCenterText("饼状图");
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(false);


        // add a selection listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){


            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        //  mChart.setEntryLabelTypeface(mTfRegular); 字体
        mChart.setEntryLabelTextSize(12f);
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private void getPieData(int count, float range) {


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry((float) (quarterly1), "java"));
        entries.add(new PieEntry((float) (quarterly2), "c/c++"));
        entries.add(new PieEntry((float) (quarterly3), "hadoop"));
        entries.add(new PieEntry((float) (quarterly4), "PHP"));

        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        spreadPieChart.setData(data);
        // undo all highlights
        spreadPieChart.highlightValues(null);
        spreadPieChart.invalidate();

    }


}
