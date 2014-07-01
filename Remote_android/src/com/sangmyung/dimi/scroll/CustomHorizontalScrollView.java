package com.sangmyung.dimi.scroll;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;

/**
 * A HorizontalScrollView (HSV) implementation that disallows touch events (so
 * no scrolling can be done by the user).
 * 
 * This HSV MUST contain a single ViewGroup as its only child, and this
 * ViewGroup will be used to display the children Views passed in to the
 * initViews() method.
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {

	public CustomHorizontalScrollView(Context _Context, AttributeSet _AttributeSet, int _DefaultStyle) {
		super(_Context, _AttributeSet, _DefaultStyle);
		_Initialize(_Context);
	}

	public CustomHorizontalScrollView(Context _Context, AttributeSet _AttributeSet) {
		super(_Context, _AttributeSet);
		_Initialize(_Context);
	}

	public CustomHorizontalScrollView(Context _Context) {
		super(_Context);
		_Initialize(_Context);
	}

	private void _Initialize(Context __Context) {
		// remove the fading as the HSV looks better without it
		setHorizontalFadingEdgeEnabled(false);
		setVerticalFadingEdgeEnabled(false);
	}

	/**
	 * @param ViewChild
	 *          The child Views to add to parent.
	 * @param ScrollToViewIdx
	 *          The index of the View to scroll to after initialisation.
	 * @param sizeCallback
	 *          A SizeCallback to interact with the HSV.
	 */
	public void initViews(View[] ViewChild, int ScrollToViewIdx,
			SizeCallback sizeCallback) {
		// A ViewGroup MUST be the only child of the HSV
		ViewGroup _TViewGroup = (ViewGroup) getChildAt(0);

		// Add all the children, but add them invisible so that the layouts are
		// calculated, but you can't see the Views
		for (int i = 0; i < ViewChild.length; i++) {
			ViewChild[i].setVisibility(View.INVISIBLE);
			_TViewGroup.addView(ViewChild[i]);
		}

		// Add a layout listener to this HSV
		// This listener is responsible for arranging the child views.
		OnGlobalLayoutListener _TOnGlobalLayoutListener= new CustomOnGlobalLayoutListener(_TViewGroup,
				ViewChild, ScrollToViewIdx, sizeCallback);
		getViewTreeObserver().addOnGlobalLayoutListener(_TOnGlobalLayoutListener);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Do not allow touch events.
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// Do not allow touch events.
		return false;
	}

	/**
	 * An OnGlobalLayoutListener impl that passes on the call to onGlobalLayout to
	 * a SizeCallback, before removing all the Views in the HSV and adding them
	 * again with calculated widths and heights.
	 */
	class CustomOnGlobalLayoutListener implements OnGlobalLayoutListener {
		private ViewGroup _ViewGroup;
		private View[] _ViewChild;
		private SizeCallback _SizeCallback;

		private int _ScrollToViewIdx;
		private int _ScrollToViewPos = 0;
		
		/**
		 * @param _ViewGroup
		 *          The parent to which the child Views should be added.
		 * @param _ViewChild
		 *          The child Views to add to parent.
		 * @param _ScrollToViewIdx
		 *          The index of the View to scroll to after initialisation.
		 * @param _SizeCallback
		 *          A SizeCallback to interact with the HSV.
		 */
		public CustomOnGlobalLayoutListener(ViewGroup _ViewGroup, View[] _ViewChild, 
				int _ScrollToViewIdx, SizeCallback _SizeCallback) {
			this._ViewGroup = _ViewGroup;
			this._ViewChild = _ViewChild;
			this._ScrollToViewIdx = _ScrollToViewIdx;
			this._SizeCallback = _SizeCallback;
		}

		@Override
		public void onGlobalLayout() {
			// System.out.println("onGlobalLayout");

			final CustomHorizontalScrollView _TCustomHorizontalScrollView = CustomHorizontalScrollView.this;

			// The listener will remove itself as a layout listener to the HSV
			// Android 15부터 deprecated
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				_TCustomHorizontalScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
			else {
				_TCustomHorizontalScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
			// Allow the SizeCallback to 'see' the Views before we remove them and
			// re-add them.
			// This lets the SizeCallback prepare View sizes, ahead of calls to
			// SizeCallback.getViewSize().
			_SizeCallback.onGlobalLayout();

			_ViewGroup.removeViewsInLayout(0, _ViewChild.length);

			final int _ScrollViewWidth = _TCustomHorizontalScrollView.getMeasuredWidth();
			final int _ScrollViewHeight = _TCustomHorizontalScrollView.getMeasuredHeight();

			// Add each view in turn, and apply the width and height returned by the
			// SizeCallback.
			int[] _TDims = new int[2];
			_ScrollToViewPos = 0;
			for (int i = 0; i < _ViewChild.length; i++) {
				_SizeCallback.Get_ViewSize(i, _ScrollViewWidth, _ScrollViewHeight, _TDims);
				_ViewChild[i].setVisibility(View.VISIBLE);
				_ViewGroup.addView(_ViewChild[i], _TDims[0], _TDims[1]);
				if (i < _ScrollToViewIdx) {
					_ScrollToViewPos += _TDims[0];
				}
			}

		}
	}

	/**
	 * Callback interface to interact with the HSV.
	 */
	public interface SizeCallback {
		/**
		 * Used to allow clients to measure Views before re-adding them.
		 */
		public void onGlobalLayout();

		/**
		 * Used by clients to specify the View dimensions.
		 * 
		 * @param idx
		 *          Index of the View.
		 * @param w
		 *          Width of the parent View.
		 * @param h
		 *          Height of the parent View.
		 * @param dims
		 *          dims[0] should be set to View width. dims[1] should be set to
		 *          View height.
		 */
		public void Get_ViewSize(int idx, int w, int h, int[] dims);
	}
}
