package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodDonationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_donation, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerRecipients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Recipient> recipients = new ArrayList<>();
        recipients.add(new Recipient(
                "Meals for All NGO",
                "123 Green Avenue, Pune 411001",
                "Distributes meals to homeless and low-income families."
        ));
        recipients.add(new Recipient(
                "Children's Shelter",
                "456 Sunshine Street, Mumbai 400001",
                "Provides food and shelter for street children."
        ));
        recipients.add(new Recipient(
                "Elder Care Foundation",
                "789 Harmony Lane, Nagpur 440001",
                "Serves nutritious meals to senior citizens."
        ));

        RecipientsAdapter adapter = new RecipientsAdapter(recipients, getContext());
        recyclerView.setAdapter(adapter);

        // When a recipient is selected, open the PickupFragment
        adapter.setOnItemClickListener(position -> {
            Recipient selected = recipients.get(position);

            PickupFragment pickupFragment = new PickupFragment();
            Bundle args = new Bundle();
            args.putString("recipient_name", selected.name);
            args.putString("recipient_address", selected.address);
            pickupFragment.setArguments(args);

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_container, pickupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
