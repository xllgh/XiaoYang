package com.xll.administrator.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.xll.administrator.utils.ParamContract;
import com.xll.administrator.utils.RVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentPlayTogether extends Fragment implements GetDataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private RecyclerView recyclerView;
    private RVAdapter rvAdapter;
    private static final String TAG="FragmentPlayTogether";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<HashMap<String,String>> playList=new ArrayList<HashMap<String, String>>();
    private ProgressDialog dialog;
    MainActivity activity;

    public FragmentPlayTogether() {
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
    public static FragmentPlayTogether newInstance(String param1, String param2) {
        FragmentPlayTogether fragment = new FragmentPlayTogether();
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
        Log.e("<<<<<<<<<<<<<","onCreateView");
        rootView=inflater.inflate(R.layout.fragment_fragment_play_together, container, false);
        initView();

        return rootView;
    }

    @Override
    public void onResume() {
        Log.e("<<<<<<<<<<<<<","onResume");
        super.onResume();
        getPlayData();
    }

    private void initView(){
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_play);
        rvAdapter=new RVAdapter(getActivity(),playList,RVAdapter.fraPlay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL ,false));
        recyclerView.setAdapter(rvAdapter);
    }

   public void getPlayData(){
       HttpUtils.volleyGet(HttpUtils.URL_GET_PLAY,null,this,TAG);
       dialog=ProgressDialog.show(getActivity(),null,getString(R.string.waitData),true,true);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);


        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(MainActivity)activity;
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onSuccess(String str) {
        Log.e(TAG, str);

        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        playList.clear();
        playList.addAll(new DataParser().parsePlaymateData(str));
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String str) {
        if(dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(getActivity(), getString(R.string.netFailed), Toast.LENGTH_SHORT);

    }
}
