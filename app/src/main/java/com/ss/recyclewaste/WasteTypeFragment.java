package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WasteTypeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste_type, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.waste_type_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        List<WasteTypeItem> wasteTypeList = new ArrayList<>();
        // Replace with your actual icons
        wasteTypeList.add(new WasteTypeItem("Plastic", R.drawable.img_1));
        wasteTypeList.add(new WasteTypeItem("E-Waste", R.drawable.img_2));
        wasteTypeList.add(new WasteTypeItem("Metal", R.drawable.img_3));
        wasteTypeList.add(new WasteTypeItem("Glass", R.drawable.img_4));
        wasteTypeList.add(new WasteTypeItem("Food Waste", R.drawable.ic_food));
        wasteTypeList.add(new WasteTypeItem("Medical Waste", R.drawable.img_5));

        WasteTypeAdapter adapter = new WasteTypeAdapter(wasteTypeList, getContext(), item -> {
            // Create bundle with selected waste type
            Bundle bundle = new Bundle();
            bundle.putString("selected_waste", item.getName());

            // Create CentersFragment and set arguments
            CentersFragment centersFragment = new CentersFragment();
            centersFragment.setArguments(bundle);

            // Replace this fragment with CentersFragment
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.main_container, centersFragment)
                        .addToBackStack(null) // Optional: Allows back navigation
                        .commit();

                // Highlight Centers in bottom nav (if MainActivity is parent)
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_centers);
                }
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}
