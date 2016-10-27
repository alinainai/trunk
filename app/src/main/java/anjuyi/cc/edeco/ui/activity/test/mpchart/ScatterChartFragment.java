package anjuyi.cc.edeco.ui.activity.test.mpchart;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseFragment;
import butterknife.BindView;

/**
 * Created by Think on 2015/11/29.
 */
public class ScatterChartFragment extends BaseFragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    @BindView(R.id.scatter_chart)
    ScatterChart mChart;
    @BindView(R.id.seek_bar_2)
    SeekBar mSeekBarX;
    @BindView(R.id.seek_bar_1)
    SeekBar mSeekBarY;
    @BindView(R.id.tvXMax)
    TextView tvX;
    @BindView(R.id.tvYMax)
    TextView tvY;

    public static ScatterChartFragment newInstance(String tag) {

        ScatterChartFragment fragment = new ScatterChartFragment();
        Bundle arguments = new Bundle();
        arguments.putString("TAG", tag);
        fragment.setArguments(arguments);
        return fragment;

    }

    @Override
    protected int initLayoutId() {
        return R.layout.scatter_chart;
    }


    @Override
    public void initView() {

        mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart.getDescription().setEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);
        mChart.setMaxHighlightDistance(50f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setMaxVisibleValueCount(200);
        mChart.setPinchZoom(true);

        mSeekBarX.setProgress(45);
        mSeekBarY.setProgress(100);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXOffset(5f);

        YAxis yl = mChart.getAxisLeft();
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setDrawGridLines(false);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i1 = 0; i1 < mSeekBarX.getProgress(); i1++) {
            float val = (float) (Math.random() * mSeekBarY.getProgress()) + 3;
            yVals1.add(new Entry(i1, val));
        }

        for (int i2 = 0; i2 < mSeekBarX.getProgress(); i2++) {
            float val = (float) (Math.random() * mSeekBarY.getProgress()) + 3;
            yVals2.add(new Entry(i2+0.33f, val));
        }

        // create a dataset and give it a type
        ScatterDataSet set1 = new ScatterDataSet(yVals1, "DS 1");
        set1.setScatterShape(ScatterChart.ScatterShape.SQUARE);
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        ScatterDataSet set2 = new ScatterDataSet(yVals2, "DS 2");
        set2.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set2.setScatterShapeHoleColor(ColorTemplate.COLORFUL_COLORS[3]);
        set2.setScatterShapeHoleRadius(3f);
        set2.setColor(ColorTemplate.COLORFUL_COLORS[1]);


        set1.setScatterShapeSize(8f);
        set2.setScatterShapeSize(8f);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);

        // create a data object with the datasets
        ScatterData data = new ScatterData(dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
