package sinia.com.linkfarmnew.view.slideview;//package sinia.com.linkfarmnew.view.slideview;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ListView;
//
//public class CustomSwipeListView extends ListView {
//
//	private static final String TAG = "ListViewCompat";
//
//	public static SwipeItemView mFocusedItemView;
//	private int mPosition;
//
//	public CustomSwipeListView(Context context) {
//		super(context);
//	}
//
//	public CustomSwipeListView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}
//
//	public CustomSwipeListView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}
//
//	public void shrinkListItem(int position) {
//		View item = getChildAt(position);
//
//		if (item != null) {
//			try {
//				((SwipeItemView) item).shrink();
//			} catch (ClassCastException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN: {
//			int x = (int) event.getX();
//			int y = (int) event.getY();
//			// 我们想知道当前点击了哪一行
//			int position = pointToPosition(x, y);
//			Log.e(TAG, "postion=" + position);
//			if (position != INVALID_POSITION) {
//
//			 // 由于pointToPosition返回的是ListView所有item中被点击的item的position，
//                // 而listview只会缓存可见的item，因此getChildAt()的时候，需要通过减去getFirstVisiblePosition()
//                // 来计算被点击的item在可见items中的位置。
//				int firstPos = getFirstVisiblePosition();
//				mFocusedItemView = (SwipeItemView) getChildAt(position
//						- firstPos);
//				Log.d("gaolei", "position------------------" + position);
//				Log.d("gaolei", "firstPos------------------" + firstPos);
//				Log.d("gaolei", "mFocusedItemView-----isNull---------"
//						+ (mFocusedItemView != null));
//			}
//		}
//		default:
//			break;
//		}
//
//		if (mFocusedItemView != null) {
//			mFocusedItemView.onRequireTouchEvent(event);
//
//		}
//
//		return super.onTouchEvent(event);
//	}
//
//}
