package com.tairui.smartcommunity.tenant.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SoftKeyBoardStatusView extends LinearLayout {

    private final int CHANGE_SIZE = 100;

    public SoftKeyBoardStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldw == 0 || oldh == 0)
            return;

        if (boardListener != null) {
            boardListener.keyBoardStatus(w, h, oldw, oldh);
            if (oldw != 0 && h - oldh < -CHANGE_SIZE) {
                boardListener.keyBoardVisible(Math.abs(h - oldh));
            }

            if (oldw != 0 && h - oldh > CHANGE_SIZE) {
                boardListener.keyBoardInvisible(Math.abs(h - oldh));
            }
        }
    }

    public interface SoftKeyboardListener {
        void keyBoardStatus(int w, int h, int oldw, int oldh);

        void keyBoardVisible(int move);

        void keyBoardInvisible(int move);
    }

    SoftKeyboardListener boardListener;

    public void setSoftKeyBoardListener(SoftKeyboardListener boardListener) {
        this.boardListener = boardListener;
    }
}
