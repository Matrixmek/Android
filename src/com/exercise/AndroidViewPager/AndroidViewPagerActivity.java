package com.exercise.AndroidViewPager;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

@SuppressLint("NewApi")
public class AndroidViewPagerActivity extends FragmentActivity {
	
    public static MyFragmentA fragmentA = new MyFragmentA();
    public static MyFragmentB fragmentB = new MyFragmentB();
    public static MyFragmentC fragmentC = new MyFragmentC();
    public static MyFragmentD fragmentD = new MyFragmentD();
	
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	
	String TagAccControl;
	
	String TagAccY;
	String TagAccZ;
	
	String TabFragmentC;
	
	public void setTagAccControl(String t){
		TagAccControl = t;
	}
	
	public String getTagAccControl(){
		return TagAccControl;
	}
	
	public void setTagAccY(String t){
		TagAccY = t;
	}
	
	public String getTagAccY(){
		return TagAccY;
	}
	
	public void setTagAccZ(String t){
		TagAccZ = t;
	}
	
	public String getTagAccZ(){
		return TagAccZ;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.pager);
        setContentView(mViewPager);
        
        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        
        mTabsAdapter = new TabsAdapter(this, mViewPager);       
        		
        mTabsAdapter.addTab(bar.newTab().setText("Buttons Control"),
                MyFragmentA.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Speech Control"),
        		MyFragmentB.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Accelerometer Control"),
        		MyFragmentC.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("Connect HRI"),
        		MyFragmentD.class, null);
        
        
        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }

    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
	
	public static class TabsAdapter extends FragmentPagerAdapter
		implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
		
		private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        
        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

		public TabsAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
            mContext = activity;
            mActionBar = activity.getActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
            //return Fragment.instantiate(mContext, info.clss.getName(), info.args);
			
			Fragment fragment;
			
			if(position == 0) fragment = fragmentA;
			else if(position == 1) fragment = fragmentB;
			else if(position == 2) fragment = fragmentC;
			else fragment = fragmentD;
			
			fragment.setArguments(info.args);
			
			return fragment;
			
			
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

	}

}