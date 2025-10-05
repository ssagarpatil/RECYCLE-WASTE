package com.ss.recyclewaste;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Language Card
        CardView cardLanguage = view.findViewById(R.id.card_language);
        TextView tvLanguage = view.findViewById(R.id.tv_language);

        // You can save the selected language in SharedPreferences later
        cardLanguage.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Select Language")
                    .setItems(new String[]{"English", "मराठी (Marathi)"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                tvLanguage.setText("English");
                                Toast.makeText(getContext(), "Language set to English", Toast.LENGTH_SHORT).show();
                            } else {
                                tvLanguage.setText("मराठी");
                                Toast.makeText(getContext(), "भाषा मराठीत सेट केली", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .show();
        });

        // About Card
        CardView cardAbout = view.findViewById(R.id.card_about);
        cardAbout.setOnClickListener(v ->
                new AlertDialog.Builder(getContext())
                        .setTitle("About RecycleWaste")
                        .setMessage("Version: 1.0\n\nA sustainable app to recycle waste and donate food. Made with care for the planet and community. © 2025")
                        .setPositiveButton("OK", null)
                        .show()
        );

        // Contact Card
        CardView cardContact = view.findViewById(R.id.card_contact);
        cardContact.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Contact: support@recyclewaste.com", Toast.LENGTH_LONG).show();
        });

        return view;
    }
}
