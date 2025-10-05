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

public class TipsFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tips, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        List<Tip> tips = new ArrayList<>();
        tips.add(new Tip(
                "Separate Dry and Wet Waste",
                "Always separate dry waste (plastic, paper, metal) from wet waste (food, leaves). This makes recycling easier and more effective."
        ));
        tips.add(new Tip(
                "Clean Before Donating",
                "Rinse food containers and packaging before donating. Clean items are more likely to be recycled or reused."
        ));
        tips.add(new Tip(
                "Avoid Plastic Mixing",
                "Don’t mix different types of plastic (PET, HDPE, etc.). Separate them by type for better recycling outcomes."
        ));
        tips.add(new Tip(
                "Donate Before Expiry",
                "Donate surplus food before it expires. NGOs can distribute it safely to those in need."
        ));
        tips.add(new Tip(
                "Use Eco-Friendly Bags",
                "Carry reusable cloth or jute bags instead of plastic. They are stronger and better for the environment."
        ));
        tips.add(new Tip(
                "Compost Food Waste",
                "Turn kitchen waste into compost for plants. It reduces landfill use and creates natural fertilizer."
        ));
        tips.add(new Tip(
                "Repair, Don’t Discard",
                "Fix broken electronics or furniture instead of throwing them away. Extending life reduces waste."
        ));

        // Optional Marathi tips (for localization demo)
        tips.add(new Tip(
                "प्लास्टिक जाळू नका",
                "प्लास्टिक जाळणे वातावरणास घातक आहे. ते पुन्हा वापरा किंवा योग्य पद्धतीने निपटवा."
        ));
        tips.add(new Tip(
                "अन्न वाया घालू नका",
                "अन्न वाया घालणे पर्यावरणासाठी खराब आहे. शक्य असेल तर ते दान करा."
        ));

        TipsAdapter adapter = new TipsAdapter(tips);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Model class
    static class Tip {
        String title, description;

        Tip(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    // RecyclerView Adapter
    static class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipViewHolder> {
        private final List<Tip> tips;

        TipsAdapter(List<Tip> tips) {
            this.tips = tips;
        }

        @NonNull
        @Override
        public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip, parent, false);
            return new TipViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
            Tip tip = tips.get(position);
            holder.tvTitle.setText(tip.title);
            holder.tvDescription.setText(tip.description);
        }

        @Override
        public int getItemCount() {
            return tips.size();
        }

        static class TipViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvDescription;

            TipViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTipTitle);
                tvDescription = itemView.findViewById(R.id.tvTipDescription);
            }
        }
    }
}
