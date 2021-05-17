package com.crtf.weather.ui.main.location;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.crtf.weather.MainActivity;
import com.crtf.weather.data.pojo.colorfulclouds.response.ResponseColorfulClouds;
import com.crtf.weather.data.pojo.ui.adapter.ReverseGeocodingAndColorfulClouds;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 位置切换适配器
 * @author crtf
 * @date 2021年5月16日 星期日 12:02
 * @version 1.0
 */
public class SwitchLocationAdapter extends FragmentPagerAdapter {
    public static final String TAG = "com.crtf.weather.ui.main.location.SwitchLocationAdapter";

    private final Map<Integer, ReverseGeocodingAndColorfulClouds> reverseGeocodingAndColorfulCloudsMap;

    public SwitchLocationAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.reverseGeocodingAndColorfulCloudsMap = new HashMap<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SwitchLocationFragment.newInstance(this.reverseGeocodingAndColorfulCloudsMap.get(position));
    }

    @Override
    public int getCount() {
        final int size = this.reverseGeocodingAndColorfulCloudsMap.size();
        return size == 0 ? 1 : size;
    }

    public Map<Integer, ReverseGeocodingAndColorfulClouds> getReverseGeocodingAndColorfulCloudsMap() {
        return reverseGeocodingAndColorfulCloudsMap;
    }

    /**
     * .            {@link MainActivity#MainServiceConnection#NetworkObserver#update(Observable, Object)} 调用了
     * .            {@link MainActivity#switchLocationAdapter#notifyDataSetChanged()} 导致
     * .            {@link SwitchLocationAdapter#instantiateItem(ViewGroup, int)} 更新了,
     * .            {@link SwitchLocationFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} 被重新调用
     * .            所以这个方法 不能在此处调用 {@link SwitchLocationFragment#updateUiData()}
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.i(TAG, "instantiateItem: Thread.currentThread().getName(): " + Thread.currentThread().getName() + " position: " + position);
        SwitchLocationFragment switchLocationFragment = (SwitchLocationFragment) super.instantiateItem(container, position);
        switchLocationFragment.setReverseGeocodingAndColorfulClouds(this.reverseGeocodingAndColorfulCloudsMap.get(position));
//        switchLocationFragment.updateUiData();
        return switchLocationFragment;
    }

    /**
     * 在宿主视图试图确定项目位置是否已更改时调用。
     * 如果给定项目的位置未更改，
     * 则返回{@link FragmentPagerAdapter#POSITION_UNCHANGED}；
     * 如果适配器中不再存在该项目，
     * 则返回{@link FragmentPagerAdapter#POSITION_NONE}。
     * <p>
     * 默认实现假定项目永远不会改变位置，并且始终返回{@link FragmentPagerAdapter#POSITION_UNCHANGED}。
     *
     * @param object 表示项目的对象，以前是通过调用{@link FragmentPagerAdapter#instantiateItem(View, int)}返回的。
     * @return 对象从[0，{@link FragmentPagerAdapter#getCount（）}），
     * {@link FragmentPagerAdapter#POSITION_UNCHANGED}（如果对象的位置未更改）
     * 或{@link FragmentPagerAdapter#POSITION_NONE}（如果该项不再存在）中获取新的位置索引。
     */
    @Override
    public int getItemPosition(@NonNull Object object) {
//        return super.getItemPosition(object);
        return PagerAdapter.POSITION_NONE;
    }
}
