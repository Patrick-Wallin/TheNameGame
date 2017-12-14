package com.pwmobiledeveloper.projects.thenamegame.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.pwmobiledeveloper.projects.thenamegame.R;
import com.pwmobiledeveloper.projects.thenamegame.adapter.FaceRecyclerViewAdapter;
import com.pwmobiledeveloper.projects.thenamegame.core.ListRandomizer;
import com.pwmobiledeveloper.projects.thenamegame.core.TheNameGameApplication;
import com.pwmobiledeveloper.projects.thenamegame.listener.OnNameGameFragmentInteractionListener;
import com.pwmobiledeveloper.projects.thenamegame.model.Headshot;
import com.pwmobiledeveloper.projects.thenamegame.model.Person;
import com.pwmobiledeveloper.projects.thenamegame.rest.ProfilesRespository;
import com.pwmobiledeveloper.projects.thenamegame.rest.TheNameGameApi;
import com.pwmobiledeveloper.projects.thenamegame.utilities.NetworkUtils;

import org.parceler.Parcels;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NameGameFragment extends Fragment implements ProfilesRespository.Listener {
    private static final Interpolator OVERSHOOT = new OvershootInterpolator();

    private OnNameGameFragmentInteractionListener mListener;
    private Context mContext;

    @BindView(R.id.face_recycler_view)
    public RecyclerView mFacePhotoRecyclerView;
    @BindView(R.id.name_text_view)
    public TextView mNameTextView;
    @BindView(R.id.time_text_view)
    public TextView mTimeTextView;
    @BindView(R.id.attempts_text_view)
    public TextView mAttemptsTextView;
    @BindView(R.id.average_text_view)
    public TextView mAverageTextView;

    @Inject
    TheNameGameApi mTheNameGameApi;

    ListRandomizer listRandomizer;

    private int numberOfColumn = 2;
    private int numberOfFacesOnView = 6;
    private FaceRecyclerViewAdapter mFaceRecyclerViewAdapter;
    private List<Person> mPeople;
    private Person mPersonAnswer;
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private int mTotalAttempts = 0;
    private int mCorrectAttempts = 0;
    private float mAverageTime = 0.0f;
    private long mTotalTimeInMilliseconds = 0L;

    private Handler customHandler = new Handler();

    public NameGameFragment() {
        listRandomizer = new ListRandomizer(new Random());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mTotalAttempts = savedInstanceState.getInt(getString(R.string.total_attempts));
            mCorrectAttempts = savedInstanceState.getInt(getString(R.string.correct_attempts));
            updatedTime = savedInstanceState.getLong(getString(R.string.updated_time));
            Parcelable parcelPersonAnswer = savedInstanceState.getParcelable(getString(R.string.person_answer));
            Parcelable parcelPeople = savedInstanceState.getParcelable(getString(R.string.people));
            mPersonAnswer = Parcels.unwrap(parcelPersonAnswer);
            mPeople = Parcels.unwrap(parcelPeople);
            timeSwapBuff = savedInstanceState.getLong(getString(R.string.time_swap_buff));
            startTime = savedInstanceState.getLong(getString(R.string.start_time));
            mAverageTime = savedInstanceState.getFloat(getString(R.string.average_time));
            mTotalTimeInMilliseconds = savedInstanceState.getLong(getString(R.string.total_time));
        }

        TheNameGameApplication.get(getActivity()).getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_game, container, false);
        ButterKnife.bind(this,view);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE){
            numberOfColumn = 6;
        }
        else {
            numberOfColumn = 3;
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumn);
        mFacePhotoRecyclerView.setLayoutManager(mLayoutManager);

        mFaceRecyclerViewAdapter = new FaceRecyclerViewAdapter(mPeople, mContext, mListener);
        mFacePhotoRecyclerView.setAdapter(mFaceRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPersonAnswer != null && mPeople != null && mPeople.size() > 0) {
            loadData(mPeople);
        }else {
            NetworkUtils networkUtils = new NetworkUtils(mContext);
            if (networkUtils.isNetworkConnected()) {
                ProfilesRespository profilesRespository = new ProfilesRespository(mTheNameGameApi, this);
            }else {
                stopTimer();
                networkUtils.showAlertMessageAboutNoInternetConnection(false);
            }
        }
    }

    public void shuffleAndReload() {
        if(mPeople != null) {
            mPeople = listRandomizer.pickN(mPeople, mPeople.size());
            mPersonAnswer = listRandomizer.pickOne(mPeople.subList(0, 6));
            mNameTextView.setAlpha(0f);
            mNameTextView.setText(mPersonAnswer.getFullName());
            mFaceRecyclerViewAdapter.loadData(mPeople.subList(0, 6), mPersonAnswer);
            animateFacesIn();
            startTime = SystemClock.uptimeMillis();
            refreshAttemptsUI();
            refreshAverageTime();
            customHandler.postDelayed(updateTimerThread, 0);
        }
    }

    private void loadData(List<Person> people) {
        boolean refresh = false;
        if(mPeople == null) {
            mPeople = people;
            cleanUpProfile();
            mPeople = listRandomizer.pickN(people, people.size());
            mPersonAnswer = listRandomizer.pickOne(mPeople.subList(0, 6));
            mNameTextView.setAlpha(0);
            refresh = true;
        }
        mNameTextView.setText(mPersonAnswer.getFullName());
        mFaceRecyclerViewAdapter.loadData(mPeople.subList(0, 6), mPersonAnswer);
        animateFacesIn();
        if(refresh)
            startTime = SystemClock.uptimeMillis();
        refreshAttemptsUI();
        refreshAverageTime();
        customHandler.postDelayed(updateTimerThread, 0);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.total_attempts),mTotalAttempts);
        outState.putInt(getString(R.string.correct_attempts),mCorrectAttempts);
        outState.putLong(getString(R.string.updated_time),updatedTime);
        outState.putParcelable(getString(R.string.person_answer), Parcels.wrap(mPersonAnswer));
        outState.putParcelable(getString(R.string.people),Parcels.wrap(mPeople));
        outState.putLong(getString(R.string.time_swap_buff),timeSwapBuff);
        outState.putLong(getString(R.string.start_time),startTime);
        outState.putFloat(getString(R.string.average_time),mAverageTime);
        outState.putLong(getString(R.string.total_time),mTotalTimeInMilliseconds);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnNameGameFragmentInteractionListener) {
            mListener = (OnNameGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNameGameFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadFinished(@NonNull List<Person> people) {
        mPeople = null;
        mPersonAnswer = null;
        loadData(people);
    }

    @Override
    public void onError(@NonNull Throwable error) {
        Log.i("error", error.getLocalizedMessage());
    }

    private void animateFacesIn() {
        mNameTextView.animate().setDuration(10000).alpha(1).start();
        //for (int i = 0; i < faces.size(); i++) {
        //    ImageView face = faces.get(i);
        //    face.animate().scaleX(1).scaleY(1).setStartDelay(800 + 120 * i).setInterpolator(OVERSHOOT).start();
        //}
    }

    private void cleanUpProfile() {
        if(mPeople != null && mPeople.size() > 0) {
            boolean remove = false;
            for(int i = 0; i < mPeople.size(); i++) {
                remove = false;
                Headshot headshot = mPeople.get(i).getHeadshot();
                if(headshot == null) {
                    remove = true;
                }else {
                    if(headshot.getUrl() != null) {
                        String url = headshot.getUrl().trim().toLowerCase();
                        if (url.isEmpty() || url.contains(getString(R.string.image_test).toLowerCase())) {
                            remove = true;
                        }
                    }else {
                        remove = true;
                    }
                }
                if(remove) {
                    mPeople.remove(i);
                    i--;
                }
            }
        }
    }

    public void stopTimer() {
        customHandler.removeCallbacks(updateTimerThread);
    }

    public void resumeTimer() {
        if(mPeople != null && mPeople.size() > 0)
            customHandler.postDelayed(updateTimerThread, 0);
    }

    public void updateAttempts(boolean correct) {
        if(correct) {
            mCorrectAttempts++;
            refreshAverageTime();
        }
        mTotalAttempts++;
        refreshAttemptsUI();
    }

    public void refreshAttemptsUI() {
        mAttemptsTextView.setText(String.valueOf(mCorrectAttempts)+"/"+String.valueOf(mTotalAttempts));
    }

    public void refreshAverageTime() {
        if(mCorrectAttempts > 0) {
            mTotalTimeInMilliseconds += timeInMilliseconds;
            long averageBasedOnAttempts = mTotalTimeInMilliseconds / mCorrectAttempts;
            int secs = (int) (averageBasedOnAttempts / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (averageBasedOnAttempts % 1000);
            mAverageTextView.setText("Avg: " + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs/ 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            mTimeTextView.setText("" + mins + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

}
