package com.ss.recyclewaste;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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


public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilMobile, tilPin;
    private TextInputEditText etMobile, etPin;
    private MaterialButton btnLogin;
    private View tvRegister;

    // Clean Firebase References
    private DatabaseReference ownersRef;
    private PrefManager prefManager;

    // Loading state
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupFirebase();
        checkAutoLogin();
        setupListeners();
        handleRegistrationIntent();
    }

    private void initViews() {
        // Bind all views
        tilMobile = findViewById(R.id.tilMobile);
        etMobile = findViewById(R.id.etMobile);
        tilPin = findViewById(R.id.tilPin);
        etPin = findViewById(R.id.etPin);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void setupFirebase() {
        // Clean Firebase structure
        ownersRef = FirebaseDatabase.getInstance().getReference("Owners");
        prefManager = new PrefManager(this);
    }

    private void checkAutoLogin() {
        // Auto-login if valid session exists
        if (prefManager.isLoggedIn()) {
            String savedMobile = prefManager.getOwnerId();
            if (!TextUtils.isEmpty(savedMobile)) {
                // Verify if owner still exists in Firebase
                verifyOwnerExists(savedMobile);
            }
        }
    }

    private void verifyOwnerExists(String mobile) {
        ownersRef.child(mobile).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Owner exists, proceed to main activity
                    navigateToMainActivity();
                } else {
                    // Owner doesn't exist, clear session
                    prefManager.logout();
                    showToast("Session expired. Please login again.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error checking, clear session to be safe
                prefManager.logout();
            }
        });
    }

    private void setupListeners() {
        // Login button click
        btnLogin.setOnClickListener(v -> loginOwner());

        // Register link click
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Real-time validation
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tilMobile.getError() != null) {
                    tilMobile.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tilPin.getError() != null) {
                    tilPin.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void handleRegistrationIntent() {
        // Handle mobile auto-fill from registration
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("registered_mobile")) {
            String registeredMobile = intent.getStringExtra("registered_mobile");
            if (!TextUtils.isEmpty(registeredMobile)) {
                etMobile.setText(registeredMobile);
                etPin.requestFocus(); // Focus on PIN field
            }
        }
    }

    private void loginOwner() {
        if (isLoading) return; // Prevent multiple clicks

        String mobile = getText(etMobile);
        String pin = getText(etPin);

        // Reset errors
        clearErrors();

        // Validation
        if (!validateInputs(mobile, pin)) {
            return;
        }

        // Show loading state
        setLoadingState(true);

        // Authenticate using clean Firebase structure
        authenticateOwner(mobile, pin);
    }

    private boolean validateInputs(String mobile, String pin) {
        boolean isValid = true;

        // Mobile validation
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

        // PIN validation
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

        return isValid;
    }

    private void authenticateOwner(String mobile, String pin) {
        // Access clean Firebase structure: Owners/{mobile}/profile
        ownersRef.child(mobile).child("profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setLoadingState(false);

                if (snapshot.exists()) {
                    String storedPin = snapshot.child("pin").getValue(String.class);
                    String ownerName = snapshot.child("name").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    // Check if account is active
                    if (status != null && !status.equals("active")) {
                        tilMobile.setError("Account is inactive. Contact support.");
                        return;
                    }

                    if (storedPin != null && storedPin.equals(pin)) {
                        // Login successful
                        handleSuccessfulLogin(mobile, ownerName);
                    } else {
                        // Wrong PIN
                        tilPin.setError("Invalid PIN");
                        tilPin.requestFocus();
                    }
                } else {
                    // Owner not found
                    tilMobile.setError("Mobile number not registered");
                    tilMobile.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                setLoadingState(false);
                showToast("Connection error: " + error.getMessage());
            }
        });
    }

    private void handleSuccessfulLogin(String mobile, String ownerName) {
        // Save login session
        prefManager.setOwnerLoggedIn(mobile);

        // Show success message
        String welcomeMessage = !TextUtils.isEmpty(ownerName) ?
                "Welcome back, " + ownerName + "!" : "Login successful!";
        showToast(welcomeMessage);

        // Update last login time (optional)
        updateLastLoginTime(mobile);

        // Navigate to main activity
        navigateToMainActivity();
    }

    private void updateLastLoginTime(String mobile) {
        // Optional: Update last login timestamp
        ownersRef.child(mobile).child("profile").child("lastLogin")
                .setValue(System.currentTimeMillis());
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void setLoadingState(boolean loading) {
        isLoading = loading;
        btnLogin.setEnabled(!loading);
        btnLogin.setText(loading ? "Logging in..." : "Login");

        // Disable input fields during loading
        etMobile.setEnabled(!loading);
        etPin.setEnabled(!loading);
        tvRegister.setEnabled(!loading);
    }

    private String getText(TextInputEditText et) {
        return et.getText() != null ? et.getText().toString().trim() : "";
    }

    private void clearErrors() {
        tilMobile.setError(null);
        tilPin.setError(null);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Reset loading state if activity is destroyed
        setLoadingState(false);
    }

    @Override
    public void onBackPressed() {
        // If loading, don't allow back press
        if (!isLoading) {
            super.onBackPressed();
        }
    }
}
