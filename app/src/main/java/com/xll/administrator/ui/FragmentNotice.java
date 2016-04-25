package com.xll.administrator.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xll.administrator.utils.DataParser;
import com.xll.administrator.utils.GetDataListener;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.OnFragmentInteractionListener;
import com.xll.administrator.R;
import com.xll.administrator.utils.RVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class FragmentNotice extends Fragment implements GetDataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG="FragmentNotice";
    private ArrayList<HashMap<String,String>> noticeList=new ArrayList<>();
    private View view;
    private RecyclerView recylerView;
    private RVAdapter adapter;
    private ProgressDialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentNotice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPlayTogether.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotice newInstance(String param1, String param2) {
        FragmentNotice fragment = new FragmentNotice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_notice, container, false);
        init();
        getNoticeData();

        return view;
    }

    private void init(){
        recylerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        DisplayMetrics dm=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels/2;
        int height=dm.heightPixels/4;
        adapter=new RVAdapter(getActivity(),noticeList,RVAdapter.fraBoard,width,height);
        recylerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recylerView.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void getNoticeData(){
        dialog= ProgressDialog.show(getActivity(), null, getString(R.string.waitData), true, true);
        HttpUtils.volleyGet(HttpUtils.URL_GET_NOTICE,null,this,TAG);
    }

    @Override
    public void onSuccess(String str) {
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        Log.e(TAG,str);
        noticeList.clear();
        noticeList.addAll(new DataParser().parseNoticeData(str));
        if(noticeList.size()<1){

        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(String str) {
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(getActivity(), getString(R.string.netFailed), Toast.LENGTH_SHORT);
    }
}
