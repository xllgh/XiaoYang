package com.xll.administrator.flea;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FleaBook extends FleaParent {
    private static FleaBook fleaBook;

    public static FleaParent getInstance() {
        if(fleaBook==null){
            fleaBook=new FleaBook();
        }
        return fleaBook;
    }
}
