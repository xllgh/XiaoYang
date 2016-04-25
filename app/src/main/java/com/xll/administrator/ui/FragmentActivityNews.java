package com.xll.administrator.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xll.administrator.utils.DataParser;
import com.xll.administrator.utils.GetDataListener;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.OnFragmentInteractionListener;
import com.xll.administrator.R;
import com.xll.administrator.utils.RVAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentActivityNews extends Fragment implements GetDataListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG="FragmentActivityNews";
    private View view;
    private RecyclerView rvClub,rvPublicGood,rvSmall;
    private RVAdapter clubAdapter,publicAdapter,smallAdapter;
    private ArrayList<HashMap<String,String>> activityList=new ArrayList<>();

    private ArrayList<HashMap<String,String>> clubList=new ArrayList<>();
    private ArrayList<HashMap<String,String>> publicList=new ArrayList<>();
    private ArrayList<HashMap<String,String>> smallList=new ArrayList<>();
    private ProgressDialog dialog;

    private TextView tvClub,tvPublic,tvSmall;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentActivityNews() {
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
    public static FragmentActivityNews newInstance(String param1, String param2) {
        FragmentActivityNews fragment = new FragmentActivityNews();
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
        view=inflater.inflate(R.layout.fragment_activity_news, container, false);
        init();
        getActivityNews();
        return view;
    }

    private void init(){

        tvClub= (TextView) view.findViewById(R.id.tvClub);
        tvPublic= (TextView) view.findViewById(R.id.tvPublic);
        tvSmall= (TextView) view.findViewById(R.id.tvSmall);

        rvClub= (RecyclerView) view.findViewById(R.id.rvClub);
        rvClub.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rvClub.setHasFixedSize(true);
        clubAdapter=new RVAdapter(getActivity(),clubList,RVAdapter.fraNews);
        rvClub.setAdapter(clubAdapter);


        rvPublicGood= (RecyclerView) view.findViewById(R.id.rvPublic);
        rvPublicGood.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rvPublicGood.setHasFixedSize(true);
        publicAdapter=new RVAdapter(getActivity(),publicList,RVAdapter.fraNews);
        rvPublicGood.setAdapter(publicAdapter);


        rvSmall= (RecyclerView) view.findViewById(R.id.rvSmall);
        rvSmall.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rvSmall.setHasFixedSize(true);
        smallAdapter=new RVAdapter(getActivity(),smallList,RVAdapter.fraNews);
        rvSmall.setAdapter(smallAdapter);
    }


   private void  getActivityNews(){

       tvSmall.setVisibility(View.GONE);
       tvPublic.setVisibility(View.GONE);
       tvClub.setVisibility(View.GONE);

       dialog= ProgressDialog.show(getActivity(), null, getString(R.string.waitData), true, true);
       HttpUtils.volleyGet(HttpUtils.URL_GET_ACTIVITY,null,this,TAG);
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


    @Override
    public void onSuccess(String str) {
        Log.e(TAG, str);
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
        activityList.clear();
        clubList.clear();
        publicList.clear();
        smallList.clear();
        activityList.addAll(new DataParser().parseActivityNewsData(str));
        if(activityList.size()<1){
            Toast.makeText(getActivity(),"no data",Toast.LENGTH_SHORT).show();
        }
        for(int i=0;i<activityList.size();i++){
            HashMap<String,String> map=activityList.get(i);
            Log.e("<<<<<<<<<<<<<<<<<<<<",map.get("category").trim());
            String category=map.get("category").trim();
            if(category.equals("1")){
                clubList.add(map);
            }else if(category.equals("2")){
                publicList.add(map);
            }else if(category.equals("3")){
                smallList.add(map);
            }
        }
        if(clubList.size()>0){
            clubAdapter.notifyDataSetChanged();
            tvClub.setVisibility(View.VISIBLE);
        }else{

        }

        if(smallList.size()>0){
            smallAdapter.notifyDataSetChanged();
            tvSmall.setVisibility(View.VISIBLE);
        }else{

        }

        if(publicList.size()>0){
            publicAdapter.notifyDataSetChanged();
            tvPublic.setVisibility(View.VISIBLE);
        }else{

        }



    }

    @Override
    public void onFailed(String str) {
        if(dialog!=null&&dialog.isShowing())
           dialog.dismiss();
        Toast.makeText(getActivity(),getString(R.string.netFailed),Toast.LENGTH_SHORT);
    }
}
