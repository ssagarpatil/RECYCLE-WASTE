package com.ss.recyclewaste;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize SharedPreferences for user login data
        sharedPreferences = getActivity().getSharedPreferences("loginPrefs", getActivity().MODE_PRIVATE);

        // Language Card
        CardView cardLanguage = view.findViewById(R.id.card_language);
        TextView tvLanguage = view.findViewById(R.id.tv_language);

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

        // Notifications Card
        CardView cardNotifications = view.findViewById(R.id.card_notifications);
        cardNotifications.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Notifications settings", Toast.LENGTH_SHORT).show();
        });

        // Logout Card
        CardView cardLogout = view.findViewById(R.id.card_logout);
        cardLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
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

        // Privacy Policy Card
        CardView cardPrivacy = view.findViewById(R.id.card_privacy);
        cardPrivacy.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Privacy Policy")
                    .setMessage("Your privacy is important to us. We collect minimal data and never share your personal information with third parties without your consent.")
                    .setPositiveButton("OK", null)
                    .show();
        });

        return view;
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout from your account?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes, Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        performLogout();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void performLogout() {
        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Show logout success message
        Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();

        // Navigate to login activity (replace MainActivity with your login activity)
        Intent intent = new Intent(getActivity(), MainActivity.class); // Change to your login activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish current activity to prevent back navigation
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
