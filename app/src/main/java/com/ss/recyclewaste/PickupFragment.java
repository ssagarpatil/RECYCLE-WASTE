package com.ss.recyclewaste;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

public class PickupFragment extends Fragment {

    private EditText etAddress, etContactName, etContactNumber;
    private TextView tvDate, tvTime;
    private Button btnSchedule;
    private PrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // 1. Inflate the layout
        View view = inflater.inflate(R.layout.fragment_pickup, container, false);

        // 2. Initialize views
        etAddress = view.findViewById(R.id.etAddress);
        etContactName = view.findViewById(R.id.etContactName);
        etContactNumber = view.findViewById(R.id.etContactNumber);
        tvDate = view.findViewById(R.id.tvDate);
        tvTime = view.findViewById(R.id.tvTime);
        btnSchedule = view.findViewById(R.id.btnSchedule);

        // 3. Get saved mobile from PrefManager (if logged in)
        Context context = getContext();
        prefManager = new PrefManager(context);
        String savedMobile = prefManager.getOwnerId();
        if (!TextUtils.isEmpty(savedMobile)) {
            etContactNumber.setText(savedMobile);
        }

        // 4. Set default texts for date/time
        tvDate.setText("Select Date");
        tvTime.setText("Select Time");

        // 5. Date picker click
        tvDate.setOnClickListener(v -> showDatePicker());

        // 6. Time picker click
        tvTime.setOnClickListener(v -> showTimePicker());

        // 7. Schedule button click
        btnSchedule.setOnClickListener(v -> schedulePickup());

        return view;
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String dateStr = dayOfMonth + "/" + (month + 1) + "/" + year;
                    tvDate.setText(dateStr);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );
        // Prevent selecting past dates
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, hourOfDay, minute) -> {
                    String timeStr = String.format("%02d:%02d", hourOfDay, minute);
                    tvTime.setText(timeStr);
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                false // 24-hour format
        );
        timePickerDialog.show();
    }

    private void schedulePickup() {
        // 8. Get all input values
        String address = etAddress.getText().toString().trim();
        String name = etContactName.getText().toString().trim();
        String number = etContactNumber.getText().toString().trim();
        String date = tvDate.getText().toString();
        String time = tvTime.getText().toString();

        // 9. Validate all fields
        if (TextUtils.isEmpty(address)) {
            etAddress.setError("Address required");
            etAddress.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            etContactName.setError("Name required");
            etContactName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(number) || number.length() < 10) {
            etContactNumber.setError("Valid 10-digit number required");
            etContactNumber.requestFocus();
            return;
        }
        if (date.equals("Select Date")) {
            Toast.makeText(getContext(), "Please select a date.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (time.equals("Select Time")) {
            Toast.makeText(getContext(), "Please select a time.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 10. Show success message
        Toast.makeText(
                getContext(),
                "Pickup scheduled successfully!",
                Toast.LENGTH_LONG
        ).show();

        // 11. Optionally clear fields
        etAddress.setText("");
        etContactName.setText("");
        etContactNumber.setText("");
        tvDate.setText("Select Date");
        tvTime.setText("Select Time");

        // 12. Go back to previous screen
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
