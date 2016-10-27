package anjuyi.cc.edeco.ui.activity.test.jiemiandonghua;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import anjuyi.cc.edeco.R;
import anjuyi.cc.edeco.base.BaseActivity;

/**
 * Created by 晓勇 on 2015/8/28 0028.
 */
public class RecyclerViewActivity extends BaseActivity implements RecyclerViewAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_recycler;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_recycler);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(getDatas());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    private List<String> getDatas() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i == 2) {
                lists.add("Slide侧滑动画");
                continue;
            }
            if (i == 3) {
                lists.add("Explode展开动画");
                continue;
            }
            if (i == 4) {
                lists.add("Fade渐显渐隐动画");
                continue;
            }
            lists.add(i + "");
        }
        return lists;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Slide mSlide = new Slide();
                    mSlide.setDuration(500);

                    getWindow().setExitTransition(mSlide);
                    getWindow().setEnterTransition(mSlide);
                }


                Intent intent = new Intent(RecyclerViewActivity.this, SlideActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(RecyclerViewActivity.this);
                    startActivity(intent, optionsCompat.toBundle());
                }else{
                    startActivity(intent);
                }
                break;
            case 3:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Explode mExplode = new Explode();
                    mExplode.setDuration(500);

                    getWindow().setExitTransition(mExplode);
                    getWindow().setEnterTransition(mExplode);
                }
                Intent intent2 = new Intent(RecyclerViewActivity.this, ExplodeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat optionsCompat2 = ActivityOptionsCompat.makeSceneTransitionAnimation(RecyclerViewActivity.this);
                    startActivity(intent2, optionsCompat2.toBundle());
                }else{
                    startActivity(intent2);
                }
                break;
            case 4:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Fade mFade = new Fade();
                    mFade.setDuration(500);

                    getWindow().setExitTransition(mFade);
                    getWindow().setEnterTransition(mFade);
                }
                Intent intent3 = new Intent(RecyclerViewActivity.this, FadeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat optionsCompat3 = ActivityOptionsCompat.makeSceneTransitionAnimation(RecyclerViewActivity.this);

                    startActivity(intent3, optionsCompat3.toBundle());
                }else{
                    startActivity(intent3);
                }
                break;
        }
    }
}
