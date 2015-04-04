package io.madd.taskmanager.ui;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.madd.taskmanager.R;
import io.madd.taskmanager.utils.BitmapUtils;

/**
 * A custom {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handles interaction events.
 * Use the {@link SplashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment extends Fragment {

    // The fragment initialization parameters
    private static final String SPLASH_PAGE = "splash_page";

    // The Page image that will be displayed.
    @InjectView(R.id.splash_title_text)
    TextView mPageTitle;

    @InjectView(R.id.splash_description_text)
    TextView mPageDescription;

    @InjectView(R.id.splash_center_image)
    ImageView mPageImage;

    // Page number in which the fragment will display content
    private int mPageNumber;

    // variable that will hold references values needed for the splash sequence.
    private String[] mPageTitleArray;

    private String[] mPageDescriptionArray;

    private TypedArray mPageImageArray;

    private OnFragmentInteractionListener mListener;
    private BitmapUtils mBitmapProcessor;

    public SplashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page page number
     * @return A new instance of fragment SplashFragment.
     */
    public static SplashFragment newInstance(int page) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putInt(SPLASH_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieving resource arrays to determine what content should be displayed.
        mPageTitleArray = getResources().getStringArray(R.array.splash_page_titles);
        mPageDescriptionArray = getResources().getStringArray(R.array.splash_page_descriptions);
        mPageImageArray = getResources().obtainTypedArray(R.array.splash_page_images);

        if (getArguments() != null) {
            mPageNumber = getArguments().getInt(SPLASH_PAGE);
        } else {
            throw new NullPointerException(getActivity().toString()
                    + " Arguments cannot be null.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.inject(this, rootView);

        mPageTitle.setText(mPageTitleArray[mPageNumber]);
        mPageDescription.setText(mPageDescriptionArray[mPageNumber]);

        mBitmapProcessor = new BitmapUtils(getActivity(), mPageImage,
                mPageImageArray.getResourceId(mPageNumber, -1), 950, 950);

        if (!mBitmapProcessor.checkIfExecuted()) {
            mBitmapProcessor.execute();
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mBitmapProcessor.setExecutionFalse();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(boolean setSeen);
    }

}