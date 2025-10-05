package com.ss.recyclewaste;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilMobile, tilPin, tilShopName, tilAddress;
    private TextInputEditText etName, etMobile, etPin, etShopName, etAddress;
    private MaterialButton btnRegister;

    // Clean Firebase References
    private DatabaseReference ownersRef, ownerProfileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        setupFirebase();
        setupListeners();
    }

    private void initViews() {
        // Bind all views
        tilName = findViewById(R.id.tilName);
        etName = findViewById(R.id.etName);
        tilMobile = findViewById(R.id.tilMobile);
        etMobile = findViewById(R.id.etMobile);
        tilPin = findViewById(R.id.tilPin);
        etPin = findViewById(R.id.etPin);
        tilShopName = findViewById(R.id.tilShopName);
        etShopName = findViewById(R.id.etShopName);
        tilAddress = findViewById(R.id.tilAddress);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void setupFirebase() {
        // Clean Firebase structure references
        ownersRef = FirebaseDatabase.getInstance().getReference("Owners");
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> registerOwner());

        // Login link
        findViewById(R.id.tvLogin).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish(); // Close registration activity
        });
    }

    private void registerOwner() {
        String name = getText(etName);
        String mobile = getText(etMobile);
        String pin = getText(etPin);
        String shopName = getText(etShopName);
        String address = getText(etAddress);

        // Reset errors
        clearErrors();

        // Validation
        if (!validateInputs(name, mobile, pin, shopName, address)) {
            return;
        }

        // Disable button during registration
        btnRegister.setEnabled(false);
        btnRegister.setText("Registering...");

        // Check if mobile already exists in clean structure
        checkMobileExists(mobile, name, pin, shopName, address);
    }

    private boolean validateInputs(String name, String mobile, String pin, String shopName, String address) {
        boolean isValid = true;

        if (TextUtils.isEmpty(name)) {
            tilName.setError("Full name is required");
            isValid = false;
        } else if (name.length() < 2) {
            tilName.setError("Name must be at least 2 characters");
            isValid = false;
        }

        if (TextUtils.isEmpty(mobile)) {
            tilMobile.setError("Mobile number is required");
            isValid = false;
        } else if (mobile.length() != 10) {
            tilMobile.setError("Enter valid 10-digit mobile number");
            isValid = false;
        } else if (!mobile.matches("^[6-9][0-9]{9}$")) {
            tilMobile.setError("Enter valid Indian mobile number");
            isValid = false;
        }

        if (TextUtils.isEmpty(pin)) {
            tilPin.setError("PIN is required");
            isValid = false;
        } else if (pin.length() != 4) {
            tilPin.setError("PIN must be exactly 4 digits");
            isValid = false;
        } else if (!pin.matches("^[0-9]{4}$")) {
            tilPin.setError("PIN must contain only numbers");
            isValid = false;
        }

        if (TextUtils.isEmpty(shopName)) {
            tilShopName.setError("Shop name is required");
            isValid = false;
        } else if (shopName.length() < 2) {
            tilShopName.setError("Shop name must be at least 2 characters");
            isValid = false;
        }

        if (TextUtils.isEmpty(address)) {
            tilAddress.setError("Address is required");
            isValid = false;
        } else if (address.length() < 5) {
            tilAddress.setError("Address must be at least 5 characters");
            isValid = false;
        }

        return isValid;
    }

    private void checkMobileExists(String mobile, String name, String pin, String shopName, String address) {
        // Check if owner already exists in clean structure
        ownersRef.child(mobile).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Mobile already registered
                    tilMobile.setError("Mobile number already registered");
                    btnRegister.setEnabled(true);
                    btnRegister.setText("Register");
                } else {
                    // Mobile not registered, proceed with registration
                    saveOwnerProfile(mobile, name, pin, shopName, address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("Database error: " + error.getMessage());
                btnRegister.setEnabled(true);
                btnRegister.setText("Register");
            }
        });
    }

    private void saveOwnerProfile(String mobile, String name, String pin, String shopName, String address) {
        // Clean Firebase structure - Save under profile node
        Map<String, Object> ownerProfileData = new HashMap<>();
        ownerProfileData.put("name", name);
        ownerProfileData.put("mobile", mobile);
        ownerProfileData.put("pin", pin);
        ownerProfileData.put("shopName", shopName);
        ownerProfileData.put("address", address);
        ownerProfileData.put("registrationDate", System.currentTimeMillis());
        ownerProfileData.put("status", "active");

        // Save under Owners/{mobile}/profile/
        ownersRef.child(mobile).child("profile").setValue(ownerProfileData)
                .addOnSuccessListener(aVoid -> {
                    // Initialize empty nodes for clean structure
                    initializeOwnerNodes(mobile);

                    showToast("Registration successful! Please login now.");

                    // Navigate to login screen
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    intent.putExtra("registered_mobile", mobile); // Pass mobile for auto-fill
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                    finish();
                })
                .addOnFailureListener(e -> {
                    showToast("Registration failed: " + e.getMessage());
                    btnRegister.setEnabled(true);
                    btnRegister.setText("Register");
                });
    }

    private void initializeOwnerNodes(String mobile) {
        // Initialize empty nodes for clean Firebase structure
        DatabaseReference ownerRef = ownersRef.child(mobile);

        // Initialize customers node (empty initially)
        ownerRef.child("customers").setValue(new HashMap<String, Object>());

        // Initialize recharges node (empty initially)
        ownerRef.child("recharges").setValue(new HashMap<String, Object>());

        // Add any other nodes you want to initialize
    }

    private String getText(TextInputEditText et) {
        return et.getText() != null ? et.getText().toString().trim() : "";
    }

    private void clearErrors() {
        tilName.setError(null);
        tilMobile.setError(null);
        tilPin.setError(null);
        tilShopName.setError(null);
        tilAddress.setError(null);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Reset button state if activity is destroyed
        if (btnRegister != null) {
            btnRegister.setEnabled(true);
            btnRegister.setText("Register");
        }
    }
}
