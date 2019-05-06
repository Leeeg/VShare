package lee.vshare.audiotalkie.utils;

import android.util.Log;

import java.util.LinkedList;

/**
 * CreateDate：18-11-1 on 下午5:27
 * Describe:
 * Coder: lee
 */
public class OutLimitList extends LinkedList<Integer> {

    private static String TAG = "OutLimitList";

    private static final int SIZE = 15;

    private int firstSeq;

    public static OutLimitList getInstance() {
        return OutLimitList.LimitListHolder.instance;
    }

    private static class LimitListHolder {
        private static final OutLimitList instance = new OutLimitList();
    }

    public OutLimitList() {
        super();
    }


    public int add(int seq) {
        if (size() > SIZE) {
            removeFirst();
        }
        super.add(seq);
        if (size() >= SIZE) {
            firstSeq = getFirst();
            if (SIZE > (seq - firstSeq + 1)) {
                Log.d(TAG, "the avail pack count = " + (seq - firstSeq));
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    public void clean() {
        super.clear();
    }

}
