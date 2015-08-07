package com.example.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager viewPager;
	private TextView tv_desc;
	private LinearLayout ll_point;
	private int[] imagesIds = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d, R.drawable.e, };
	private List<ImageView> imageViews;
	// ͼƬ���⼯��
	private final String[] imageDescriptions = { "���������ף��ҾͲ��ܵ���",
			"�����ֻ��������ٳ������ϸ������˴�ϳ�", "���ر�����Ӱ�������", "������TV�������", "��Ѫ��˿�ķ�ɱ" };
	protected int lastPosition;
	private boolean isRunning = false;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
			if (isRunning) {
				handler.sendEmptyMessageDelayed(0, 2000);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		ll_point = (LinearLayout) findViewById(R.id.ll_point);

		tv_desc.setText(imageDescriptions[0]);
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imagesIds.length; i++) {
			ImageView ima = new ImageView(this);
			ima.setBackgroundResource(imagesIds[i]);
			imageViews.add(ima);
			
			// ���ָʾ��
			ImageView imageView = new ImageView(this);
			// ��LinearLayout�����
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			//�������View�Ĳ���
			params.rightMargin = 10;
			imageView.setLayoutParams(params);
			imageView.setBackgroundResource(R.drawable.point_bg);
			if (i == 0) {
				imageView.setEnabled(true);
			}else {
				imageView.setEnabled(false);
			}
			ll_point.addView(imageView);
		}
		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setCurrentItem(Integer.MAX_VALUE/2 - 3);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			// ҳ���л������
			@Override
			public void onPageSelected(int position) {
				position = position % imageViews.size();
				
				tv_desc.setText(imageDescriptions[position]);
				// �ı䵱ǰ�����ɫ
				// �ѵ�ǰ���enable����Ϊtrue
				ll_point.getChildAt(position).setEnabled(true);
				// ����һ��ҳ������Ϊfalse
				ll_point.getChildAt(lastPosition).setEnabled(false);
				lastPosition = position;
				System.out.println("position--" + position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}
	
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position = position % imageViews.size();
			container.addView(imageViews.get(position));

			return imageViews.get(position);
		}

		/**
		 * ����ʱ����
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
			object = null;
		}
	}
}
