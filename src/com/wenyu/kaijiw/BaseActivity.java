package com.wenyu.kaijiw;


import android.app.Activity;
import android.media.AudioManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 */
public abstract class BaseActivity extends Activity  implements OnClickListener {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private onActivityFinishListener onActivityFinish;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 加载菜单
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	protected void initBase(int layoutResID) {
		// 使得音量键控制媒体声音
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		if (layoutResID != 0) {
			setContentView(layoutResID);
		}
		ImageButton leftBtn = ((ImageButton) findViewById(R.id.left));

		if (leftBtn != null) {
			leftBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
					if(onActivityFinish != null){
						onActivityFinish.finish();	
					}
				}
			});
		}

		TextView rightTextView = ((TextView) findViewById(R.id.right));

		if (rightTextView != null) {
			rightTextView.setOnClickListener(this);
		}
	


	}
	public interface onActivityFinishListener{
		public void finish();
	}
	public void setOnActivityFinishListener(onActivityFinishListener l){
		onActivityFinish = l;
	}

}
