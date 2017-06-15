package com.example.hoang.freemusic.activities;

import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.adapters.PagerAdapter;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.example.hoang.freemusic.events.LoadUIPlayer;
import com.example.hoang.freemusic.events.OnClickMiniPlayer;
import com.example.hoang.freemusic.events.OnClickTopSong;
import com.example.hoang.freemusic.fragments.MainPlayerFragment;
import com.example.hoang.freemusic.managers.MusicManager;
import com.example.hoang.freemusic.managers.ScreenManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tb_main)
    Toolbar tbMain;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.rl_miniplayer)
    RelativeLayout rlMiniplayer;
    @BindView(R.id.iv_mini)
    ImageView ivMini;
    @BindView(R.id.sb_mini)
    SeekBar sbMini;
    @BindView(R.id.tv_mini_song)
    TextView tvMiniSong;
    @BindView(R.id.tv_mini_artist)
    TextView tvMiniArtist;
    @BindView(R.id.fab_mini)
    FloatingActionButton fabMini;

    private TopSongModel topSongModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        EventBus.getDefault().register(this);

    }

    private void setupUI() {
        ButterKnife.bind(this);

        tbMain.setTitle("Free Music");
        tbMain.setTitleTextColor(getResources().getColor(R.color.primary_text));

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_file_download_black_24dp));

        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.icon_selected), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.icon_unselected), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.icon_unselected), PorterDuff.Mode.SRC_IN);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(getResources().getColor(R.color.icon_selected), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.icon_unselected), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        sbMini.setPadding(0, 0, 0, 0);
    }

    @Subscribe
    public void onReceiveTopSong(OnClickTopSong onClickTopSong) {
        topSongModel = onClickTopSong.getTopSongModel();
        MusicManager.loadSearchSong(this, topSongModel);
        rlMiniplayer.setVisibility(View.VISIBLE);
        fabMini.setOnClickListener(this);
        rlMiniplayer.setOnClickListener(this);
    }

    @Subscribe
    public void onLoadMiniPlayerUI(LoadUIPlayer loadUIPlayer) {
        TopSongModel topSongModel = loadUIPlayer.getTopSongModel();
        tvMiniArtist.setText(topSongModel.getArtistName());
        tvMiniSong.setText(topSongModel.getSongName());
        Picasso.with(getApplicationContext()).load(topSongModel.getSmallImage()).transform(new CropCircleTransformation()).into(ivMini);
        MusicManager.updateSongRealtime(fabMini, sbMini, null, null, null);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_mini) {
            MusicManager.playOrPause();
        }
        if (v.getId() == R.id.rl_miniplayer) {
            EventBus.getDefault().postSticky(new OnClickMiniPlayer(topSongModel));
            ScreenManager.openFragment(getSupportFragmentManager(), new MainPlayerFragment(), R.id.rl_container, true, true);

        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0){
            super.onBackPressed();
        }
        else{
            moveTaskToBack(true);
        }

    }
}
