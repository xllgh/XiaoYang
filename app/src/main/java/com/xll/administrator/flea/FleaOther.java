package com.xll.administrator.flea;

/**
 * Created by Administrator on 2016/4/20.
 */
public class FleaOther extends FleaParent {
   static FleaOther fleaOther;


    public static FleaParent getInstance() {
        if (fleaOther==null){
            fleaOther=new FleaOther();
        }
        return fleaOther;
    }
}
