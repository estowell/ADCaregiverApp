package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentAcceptChallengeBinding;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.CurrentChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.SelectedFlower;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.CURRENT_CHALLENGE;

public class AcceptChallengeFragment extends DaggerFragment {


    private static String UNIT_CHALLENGE = "UNIT_CHALLENGE";
    private static String Image_Name = "Image_name";
    private ChallengesViewModel viewModel;
    private UnitChallenge unitChallenge;
    private String imageName;

    @Inject
    UserManager userManager;

    @Inject
    ViewModelFactory viewModelFactory;

    public static AcceptChallengeFragment newInstance(UnitChallenge unitChallenge, String imageName) {

        Bundle args = new Bundle();
        args.putString(Image_Name, imageName);
        args.putParcelable(UNIT_CHALLENGE, unitChallenge);
        AcceptChallengeFragment fragment = new AcceptChallengeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(ChallengesViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        FragmentAcceptChallengeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accept_challenge, container, false);

        Bundle bundle = getArguments();

        if (bundle != null) {
            unitChallenge = bundle.getParcelable(UNIT_CHALLENGE);
            imageName = bundle.getString(Image_Name);
        }
        init(binding);
        return binding.getRoot();
    }

    private void init(FragmentAcceptChallengeBinding binding) {

        Observer<Boolean> acceptChallenge = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    updateCurrentChallengeOnFireBase();
                } else {
                    Toast.makeText(getContext(), "Error while making the request, please try again!", Toast.LENGTH_LONG).show();
                }
            }
        };

        binding.imageView7.setImageResource(DrawableUntils.getDrawableIdByName(getContext(), imageName));
        binding.header.setText(unitChallenge.total_duration + " Days Challenge");
        binding.subheader.setText(unitChallenge.text);
        binding.acceptChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.acceptChallenge(unitChallenge);
            }
        });

        binding.changeChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getFragmentManager()).popBackStack();
            }
        });

        viewModel.getAcceptChallengeResponse().observe(this, acceptChallenge);
    }

    private void updateCurrentChallengeOnFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        SelectedFlower selectedFlower = new SelectedFlower(imageName, 1);
        final CurrentChallenge currentChallenge = new CurrentChallenge(0, 0, true, false, selectedFlower);
        DatabaseReference reference = database.getReference().child(CURRENT_CHALLENGE).child(String.valueOf(userManager.getUser().getUserId()));

        reference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                mutableData.setValue(currentChallenge);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}
