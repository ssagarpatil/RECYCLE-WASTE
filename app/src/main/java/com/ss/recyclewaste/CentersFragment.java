package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CentersFragment extends Fragment {

    private String selectedWasteType = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_centers, container, false);

        Bundle args = getArguments();
        if (args != null) {
            selectedWasteType = args.getString("selected_waste", "");
        }

        TextView tvSelectedWaste = view.findViewById(R.id.text_selected_waste);
        tvSelectedWaste.setText("Centers accepting: " + selectedWasteType);

        RecyclerView recyclerView = view.findViewById(R.id.centers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Center> allCenters = getAllCenters();
        List<Center> showCenters = new ArrayList<>();

        for (Center center : allCenters) {
            if (center.getAcceptedWasteTypes().toLowerCase().contains(selectedWasteType.toLowerCase())) {
                showCenters.add(center);
            }
        }

        CentersAdapter adapter = new CentersAdapter(showCenters, getContext(), selectedWasteType);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Center> getAllCenters() {
        List<Center> centers = new ArrayList<>();
        centers.add(new Center("Green Recycle Center", "123 Main St", "9 AM - 6 PM", "Plastic, Metal, Glass"));
        centers.add(new Center("Eco Waste Facility", "456 Elm St", "8 AM - 5 PM", "E-Waste, Plastic"));
        centers.add(new Center("Clean Earth NGO", "789 Oak St", "10 AM - 4 PM", "Food Waste, Medical Waste"));
        centers.add(new Center("Urban Metal Recycling", "321 Cedar St", "9 AM - 5 PM", "Metal"));
        return centers;
    }
}
