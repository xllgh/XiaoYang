package com.xll.administrator.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xll.administrator.R;
import com.xll.administrator.utils.DataParser;
import com.xll.administrator.utils.GetDataListener;
import com.xll.administrator.utils.HttpUtils;
import com.xll.administrator.utils.MyPagerAdapter;
import com.xll.administrator.utils.OnFragmentInteractionListener;
import com.xll.administrator.utils.RVAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFlea#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFlea extends Fragment implements View.OnClickListener, GetDataListener, ViewPager.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RVAdapter adapter;
    private ViewPager viewPager;
    private TextView tvRecycle, tvFindBook, tvFindExam, tvFindOther;
    private RecyclerView recyclerView;
    private ArrayList<HashMap<String, String>> fleaList = new ArrayList<>();
    private ArrayList<View> pageList = new ArrayList<>();
    private String TAG = "FragmentFlea";
    private ImageView ivLeft, ivRight;
    private OnFragmentInteractionListener mListener;
    private ImageView[] ivs;
    private MyPagerAdapter pAdapter;
    private ProgressDialog dialog;


    public FragmentFlea() {
        // Required empty public constructor
    }

    public static FragmentFlea newInstance(String param1, String param2) {
        FragmentFlea fragment = new FragmentFlea();
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
        view = inflater.inflate(R.layout.fragment_flea, container, false);
        init();
        getFleaData();
        return view;
    }


    private void init() {
        ivLeft = (ImageView) view.findViewById(R.id.ivLeft);
        ivRight = (ImageView) view.findViewById(R.id.ivRight);
        ivs = new ImageView[]{ivLeft, ivRight};
        ivLeft.setSelected(true);

        tvFindBook = (TextView) view.findViewById(R.id.findBook);
        tvFindExam = (TextView) view.findViewById(R.id.findExam);
        tvFindOther = (TextView) view.findViewById(R.id.findOther);
        tvRecycle = (TextView) view.findViewById(R.id.recycle);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels / 3;
        int height = dm.heightPixels / 5;

        adapter = new RVAdapter(getActivity(), fleaList, RVAdapter.fraFlea, width, height);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_pager, null);
        View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.item_pager, null);

        pageList.add(view1);
        pageList.add(view2);
        viewPager.addOnPageChangeListener(this);
        pAdapter = new MyPagerAdapter(getActivity(), pageList);
        viewPager.setAdapter(pAdapter);


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

    private void getFleaData() {
        dialog = ProgressDialog.show(getActivity(), null, getString(R.string.waitData), true, true);
        HttpUtils.volleyGet(HttpUtils.URL_GET_ALL_TRADE, null, this, TAG);
    }

    private void getFleaPager() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(String str) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Log.e(TAG, str);
        fleaList.clear();
        fleaList.addAll(new DataParser().parseFleaData(str));
        if (fleaList.size() < 1) {

        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onFailed(String str) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        Toast.makeText(getActivity(), getString(R.string.netFailed), Toast.LENGTH_SHORT);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ivs.length; i++) {
            if (i == position) {
                ivs[i].setSelected(true);
            } else {
                ivs[i].setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
