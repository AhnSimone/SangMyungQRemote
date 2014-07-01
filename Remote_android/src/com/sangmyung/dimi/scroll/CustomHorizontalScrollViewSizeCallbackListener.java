package com.sangmyung.dimi.scroll;

import android.view.View;

import com.sangmyung.dimi.scroll.CustomHorizontalScrollView.SizeCallback;

/**
 * Helper that remembers the width of the 'slide' button, so that the 'slide'
 * button remains in view, even when the menu is showing.
 */
class SizeCallbackForMenu implements SizeCallback {
	private View _ButtonView;
	private int _ButtonWidth;

	public SizeCallbackForMenu(View btnSlide) {
		super();
		this._ButtonView = btnSlide;
	}

	@Override
	public void onGlobalLayout() {
		_ButtonWidth = _ButtonView.getMeasuredWidth();
	}

	@Override
	public void Get_ViewSize(int idx, int w, int h, int[] dims) {
		dims[0] = w;
		dims[1] = h;
		final int menuIdx = 0;
		if (idx == menuIdx) {
			dims[0] = w - (_ButtonWidth); // 주석을 안했을 시 scroll에 쌓으면서 초기화를 시킨다.
		}
	}
}
