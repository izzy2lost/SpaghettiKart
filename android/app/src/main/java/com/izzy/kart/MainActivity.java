package com.izzy.kart;

import org.libsdl.app.SDLActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.Build;
import android.widget.Toast;
import android.util.Log;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.KeyEvent;

import java.util.concurrent.Executors;
import android.app.AlertDialog;

public class MainActivity extends SDLActivity {
static {
    System.loadLibrary("Spaghettify");
}
    SharedPreferences preferences;
    private static final CountDownLatch setupLatch = new CountDownLatch(1);
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 2296;
    private static final int FILE_PICKER_REQUEST_CODE = 0;
    private File targetRootFolder;
    private File romTargetFile; // Will hold the mk64.o2r destination
    private volatile boolean romFileReady = false;

    public static String getSaveDir() {
    File dir = new File(Environment.getExternalStorageDirectory(), "Spaghetti-Kart");
    if (!dir.exists()) dir.mkdirs();
    return dir.getAbsolutePath();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("com.izzy.kart.prefs", Context.MODE_PRIVATE);
        targetRootFolder = new File(Environment.getExternalStorageDirectory(), "Spaghetti-Kart");
        romTargetFile = new File(targetRootFolder, "mk64.o2r");

        // Check if mk64.o2r exists before starting SDL
        if (romTargetFile.exists()) {
            // File exists, safe to start SDL
            super.onCreate(savedInstanceState);
            setupControllerOverlay();
            attachController();
            setupLatch.countDown();
        } else {
            // File missing, start SDL but delay native initialization
            super.onCreate(savedInstanceState);
            setupControllerOverlay();
            
            // Check permissions and setup files
            if (hasStoragePermission()) {
                checkAndSetupFiles();
            } else {
                requestStoragePermission();
            }
        }
    }

    public static void waitForSetupFromNative() {
        try {
            setupLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Permissions helpers
    private boolean hasStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, STORAGE_PERMISSION_REQUEST_CODE);
            } else {
                checkAndSetupFiles();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            checkAndSetupFiles();
        }
    }

// Check & Setup Files 
public void checkAndSetupFiles() {
    File assetsFolder = new File(targetRootFolder, "assets");
    File skO2rFile = new File(targetRootFolder, "spaghetti.o2r");
    File configYml = new File(targetRootFolder, "config.yml");
    File yamlsFolder = new File(targetRootFolder, "yamls");
    File gameControllerDb = new File(targetRootFolder, "gamecontrollerdb.txt");
    boolean missingGameControllerDb = !gameControllerDb.exists();
    boolean missingAssets = !assetsFolder.exists() || assetsFolder.listFiles() == null || assetsFolder.listFiles().length == 0;
    boolean missingSkO2r = !skO2rFile.exists();
    boolean missingConfig = !configYml.exists();
    boolean missingYamls = !yamlsFolder.exists() || yamlsFolder.listFiles() == null || yamlsFolder.listFiles().length == 0;

    if (!targetRootFolder.exists()) targetRootFolder.mkdirs();

    // Always ensure mods folder exists
        File targetModsDir = new File(targetRootFolder, "mods");
        if (!targetModsDir.exists()) {
            targetModsDir.mkdirs();
        }
    
    // Move config files to external storage
    
    if (!assetsFolder.exists() || missingAssets) copyAssetFolder("assets", assetsFolder.getAbsolutePath());
    if (!yamlsFolder.exists() || missingYamls) copyAssetFolder("yamls", yamlsFolder.getAbsolutePath());
    if (missingConfig) copyAssetFile("config.yml", configYml);
    if (missingSkO2r) copyAssetFile("spaghetti.o2r", skO2rFile);
    if (missingGameControllerDb) copyAssetFile("gamecontrollerdb.txt", gameControllerDb);
    
    // This method is only called when file is missing (from onCreate)
    runOnUiThread(() -> new AlertDialog.Builder(this)
        .setTitle("Missing mk64.o2r")
        .setMessage("Please select your Mario Kart 64 ROM file (.z64) or pre-extracted mk64.o2r file to continue.")
        .setCancelable(false)
        .setPositiveButton("Select File", (dialog, which) -> openFilePicker())
        .show());
}

    // Helper methods for asset copying
    private void copyAssetFolder(String assetFolderName, String destPath) {
        try {
            File dest = new File(destPath);
            if (!dest.exists()) dest.mkdirs();
            AssetCopyUtil.copyAssetsToExternal(this, assetFolderName, destPath);
            showToast(assetFolderName + " copied");
        } catch (IOException e) {
            showToast("Error copying " + assetFolderName);
        }
    }

    private void copyAssetFile(String assetFileName, File destFile) {
        try (InputStream in = getAssets().open(assetFileName);
             OutputStream out = new FileOutputStream(destFile)) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) out.write(buffer, 0, read);
            showToast(assetFileName + " copied");
        } catch (IOException e) {
            showToast("Error copying " + assetFileName);
        }
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            handleRomFileSelection(data.getData());
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            // Handle MANAGE_EXTERNAL_STORAGE result
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    checkAndSetupFiles();
                } else {
                    Toast.makeText(this, "Storage permission is required to access files.", Toast.LENGTH_LONG).show();
                    // finish();
                }
            }
        }
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Accept both ROM files (.z64) and O2R files
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"*/*"});
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    private void handleRomFileSelection(Uri selectedFileUri) {
        if (selectedFileUri == null) {
            showToast("No file selected.");
            return;
        }

        // Get the file name to determine if it's a ROM or O2R file
        String fileName = getFileName(selectedFileUri);
        if (fileName == null) {
            showToast("Could not determine file type.");
            return;
        }

        Log.i("MainActivity", "Selected file: " + fileName);

        // Check if it's a ROM file (.z64) or O2R file
        if (fileName.toLowerCase().endsWith(".z64")) {
            // Handle ROM extraction
            handleRomExtraction(selectedFileUri, fileName);
        } else if (fileName.toLowerCase().endsWith(".o2r")) {
            // Handle O2R file copy
            handleO2RFileCopy(selectedFileUri);
        } else {
            // Show dialog to let user choose how to handle the file
            runOnUiThread(() -> new AlertDialog.Builder(this)
                .setTitle("File Type Detection")
                .setMessage("Could not determine file type from extension. How would you like to process this file?")
                .setPositiveButton("As ROM (.z64)", (dialog, which) -> handleRomExtraction(selectedFileUri, fileName))
                .setNegativeButton("As O2R", (dialog, which) -> handleO2RFileCopy(selectedFileUri))
                .setNeutralButton("Cancel", null)
                .show());
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                    if (nameIndex >= 0) {
                        fileName = cursor.getString(nameIndex);
                    }
                }
            } catch (Exception e) {
                Log.w("MainActivity", "Could not get file name from content URI", e);
            }
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    private void handleRomExtraction(Uri romFileUri, String fileName) {
        showToast("Starting ROM extraction...");
        Log.i("MainActivity", "Starting ROM extraction for: " + fileName);

        // Create a progress dialog
        AlertDialog progressDialog = new AlertDialog.Builder(this)
            .setTitle("Extracting ROM")
            .setMessage("Extracting Mario Kart 64 ROM to O2R format...\nProgress: 0%")
            .setCancelable(false)
            .create();
        
        runOnUiThread(() -> progressDialog.show());

        // Perform extraction in background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // First, copy ROM file to temporary location
                File tempRomFile = new File(targetRootFolder, "temp_rom.z64");
                
                try (InputStream in = getContentResolver().openInputStream(romFileUri);
                     FileOutputStream out = new FileOutputStream(tempRomFile)) {
                    
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.flush();
                    out.getFD().sync();
                }

                Log.i("MainActivity", "ROM file copied to temp location: " + tempRomFile.getAbsolutePath());

                // Create progress callback
                RomExtractionProgressCallback progressCallback = new RomExtractionProgressCallback() {
                    @Override
                    public void onProgress(float progress) {
                        int progressPercent = Math.round(progress * 100);
                        runOnUiThread(() -> {
                            if (progressDialog.isShowing()) {
                                progressDialog.setMessage("Extracting Mario Kart 64 ROM to O2R format...\nProgress: " + progressPercent + "%");
                            }
                        });
                    }
                };

                // Call native extraction method
                boolean success = nativeExtractRom(
                    tempRomFile.getAbsolutePath(),
                    romTargetFile.getAbsolutePath(),
                    progressCallback
                );

                // Clean up temp file
                if (tempRomFile.exists()) {
                    tempRomFile.delete();
                }

                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    
                    if (success && romTargetFile.exists() && romTargetFile.length() > 0) {
                        Log.i("MainActivity", "ROM extraction completed successfully");
                        showToast("ROM extraction completed!");
                        
                        // Continue with normal flow
                        tryNativeFileCall(romTargetFile.getAbsolutePath(), 0);
                    } else {
                        Log.e("MainActivity", "ROM extraction failed");
                        showToast("ROM extraction failed. Please try a different ROM file.");
                    }
                });

            } catch (Exception e) {
                Log.e("MainActivity", "Error during ROM extraction", e);
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    showToast("ROM extraction failed: " + e.getMessage());
                });
            }
        });
    }

    private void handleO2RFileCopy(Uri o2rFileUri) {
        showToast("Copying O2R file...");

        try (InputStream in = getContentResolver().openInputStream(o2rFileUri);
             FileOutputStream out = new FileOutputStream(romTargetFile)) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            
            out.flush();
            out.getFD().sync();
            
            Log.i("MainActivity", "O2R file copied successfully, size: " + romTargetFile.length() + " bytes");
            
            // Use a delay to ensure file operations are complete
            new android.os.Handler(getMainLooper()).postDelayed(() -> {
                try {
                    if (romTargetFile.exists() && romTargetFile.length() > 0) {
                        Log.i("MainActivity", "Calling nativeHandleSelectedFile with: " + romTargetFile.getAbsolutePath());
                        tryNativeFileCall(romTargetFile.getAbsolutePath(), 0);
                    } else {
                        Log.e("MainActivity", "O2R file verification failed");
                        showToast("O2R file verification failed");
                    }
                } catch (Exception e) {
                    Log.e("MainActivity", "Error in native file handling", e);
                    showToast("Error loading O2R file");
                }
            }, 2000);
            
        } catch (IOException e) {
            Log.e("MainActivity", "Error copying O2R file", e);
            showToast("Failed to copy O2R file: " + e.getMessage());
        }
    }

    private void tryNativeFileCall(String filePath, int attempt) {
        final int maxAttempts = 5;
        final int baseDelay = 500; // Start with 500ms
        
        if (attempt >= maxAttempts) {
            Log.e("MainActivity", "Failed to call native method after " + maxAttempts + " attempts");
            showToast("Failed to load mk64.o2r after multiple attempts. Please restart the app.");
            return;
        }
        
        runOnUiThread(() -> {
            try {
                Log.i("MainActivity", "Attempting native call, attempt " + (attempt + 1));
                nativeHandleSelectedFile(filePath);
                attachController();
                setupLatch.countDown();
                romFileReady = true;
                showToast("mk64.o2r loaded successfully!");
                Log.i("MainActivity", "Native call successful on attempt " + (attempt + 1));
            } catch (UnsatisfiedLinkError e) {
                Log.w("MainActivity", "Native library not ready on attempt " + (attempt + 1) + ", retrying...", e);
                // Retry with exponential backoff
                new android.os.Handler(getMainLooper()).postDelayed(() -> {
                    tryNativeFileCall(filePath, attempt + 1);
                }, baseDelay * (1 << attempt)); // Exponential backoff: 500ms, 1s, 2s, 4s, 8s
            } catch (Exception e) {
                Log.e("MainActivity", "Native call failed on attempt " + (attempt + 1), e);
                showToast("Failed to load mk64.o2r: " + e.getMessage());
            }
        });
    }

    // Native methods
    public native void attachController();
    public native void detachController();
    public native void setButton(int button, boolean value);
    public native void setCameraState(int axis, float value);
    public native void setAxis(int axis, short value);
    public native void nativeHandleSelectedFile(String filePath);
    public native boolean nativeExtractRom(String romPath, String outputPath, RomExtractionProgressCallback progressCallback);

    // Controller overlay and touch handling
    private Button buttonA, buttonB, buttonX, buttonY;
    private Button buttonDpadUp, buttonDpadDown, buttonDpadLeft, buttonDpadRight;
    private Button buttonLB, buttonRB, buttonZ, buttonStart, buttonBack, buttonToggle, buttonMenu;
    private FrameLayout leftJoystick;
    private ImageView leftJoystickKnob;
    private View overlayView;

    private void setupControllerOverlay() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        overlayView = inflater.inflate(R.layout.touchcontrol_overlay, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        overlayView.setLayoutParams(layoutParams);
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.addView(overlayView);

        final ViewGroup buttonGroup = overlayView.findViewById(R.id.button_group);

        buttonA = overlayView.findViewById(R.id.buttonA);
        buttonB = overlayView.findViewById(R.id.buttonB);
        buttonX = overlayView.findViewById(R.id.buttonX);
        buttonY = overlayView.findViewById(R.id.buttonY);

        buttonDpadUp = overlayView.findViewById(R.id.buttonDpadUp);
        buttonDpadDown = overlayView.findViewById(R.id.buttonDpadDown);
        buttonDpadLeft = overlayView.findViewById(R.id.buttonDpadLeft);
        buttonDpadRight = overlayView.findViewById(R.id.buttonDpadRight);

        buttonLB = overlayView.findViewById(R.id.buttonLB);
        buttonRB = overlayView.findViewById(R.id.buttonRB);
        buttonZ = overlayView.findViewById(R.id.buttonZ);

        buttonStart = overlayView.findViewById(R.id.buttonStart);
        buttonBack = overlayView.findViewById(R.id.buttonBack);
        buttonMenu = overlayView.findViewById(R.id.buttonMenu);

        buttonToggle = overlayView.findViewById(R.id.buttonToggle);

        leftJoystick = overlayView.findViewById(R.id.left_joystick);
        leftJoystickKnob = overlayView.findViewById(R.id.left_joystick_knob);

        FrameLayout rightScreenArea = overlayView.findViewById(R.id.right_screen_area);

        addTouchListener(buttonA, ControllerButtons.BUTTON_A);
        addTouchListener(buttonB, ControllerButtons.BUTTON_B);
        addTouchListener(buttonX, ControllerButtons.BUTTON_X);
        addTouchListener(buttonY, ControllerButtons.BUTTON_Y);

        setupCButtons(buttonDpadUp, ControllerButtons.AXIS_RY, 1);
        setupCButtons(buttonDpadDown, ControllerButtons.AXIS_RY, -1);
        setupCButtons(buttonDpadLeft, ControllerButtons.AXIS_RX, 1);
        setupCButtons(buttonDpadRight, ControllerButtons.AXIS_RX, -1);

        addTouchListener(buttonLB, ControllerButtons.BUTTON_LB);
        addTouchListener(buttonRB, ControllerButtons.BUTTON_RB);
        addTouchListener(buttonZ, ControllerButtons.AXIS_RT);

        addTouchListener(buttonStart, ControllerButtons.BUTTON_START);
        addTouchListener(buttonBack, ControllerButtons.BUTTON_BACK);
        setupMenuButton(buttonMenu);

        setupJoystick(leftJoystick, leftJoystickKnob, true);
        setupLookAround(rightScreenArea);
        setupToggleButton(buttonToggle, buttonGroup);
    }

    private void setupMenuButton(Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onNativeKeyDown(KeyEvent.KEYCODE_ESCAPE);
                        button.setPressed(true);
                        // Toggle menu state and controls
                        MenuOpen = !MenuOpen;
                        if (MenuOpen) {
                            DisableAllControls();
                        } else {
                            EnableAllControls();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        onNativeKeyUp(KeyEvent.KEYCODE_ESCAPE);
                        button.setPressed(false);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        onNativeKeyUp(KeyEvent.KEYCODE_ESCAPE);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupToggleButton(Button button, ViewGroup uiGroup) {
        boolean isHidden = preferences.getBoolean("controlsVisible", false);
        uiGroup.setVisibility(isHidden ? View.INVISIBLE : View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            boolean isHidden = false;
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    uiGroup.setVisibility(View.VISIBLE);
                } else {
                    uiGroup.setVisibility(View.INVISIBLE);
                }
                preferences.edit().putBoolean("controlsVisible", !isHidden).apply();
                isHidden = !isHidden;
            }
        });
    }

    private void addTouchListener(Button button, int buttonNum) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!AllControlsEnabled) return false;
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setButton(buttonNum, true);
                        button.setPressed(true);
                        return true;
                    case MotionEvent.ACTION_UP:
                        setButton(buttonNum, false);
                        button.setPressed(false);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        setButton(buttonNum, false);
                        return true;
                }
                return false;
            }
        });
    }

    private void setupCButtons(Button button, int buttonNum, int direction) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!AllControlsEnabled) return false;
                
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setAxis(buttonNum, direction < 0 ? Short.MAX_VALUE : Short.MIN_VALUE);
                        button.setPressed(true);
                        return true;
                    case MotionEvent.ACTION_UP:
                        setAxis(buttonNum, (short) 0);
                        button.setPressed(false);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        setAxis(buttonNum, (short) 0);
                        return true;
                }
                return false;
            }
        });
    }

    // Control state management
    private boolean TouchAreaEnabled = true;
    private boolean MenuOpen = false;
    private boolean AllControlsEnabled = true;

    private void DisableTouchArea() {
        TouchAreaEnabled = false;
    }

    private void EnableTouchArea() {
        TouchAreaEnabled = true;
    }

    private void DisableAllControls() {
        AllControlsEnabled = false;
        TouchAreaEnabled = false;
    }

    private void EnableAllControls() {
        AllControlsEnabled = true;
        TouchAreaEnabled = true;
    }

    private void setupLookAround(FrameLayout rightScreenArea) {
        rightScreenArea.setOnTouchListener(new View.OnTouchListener() {
            private float lastX = 0;
            private float lastY = 0;
            private boolean isTouching = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getX();
                        lastY = event.getY();
                        isTouching = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isTouching) {
                            float deltaX = event.getX() - lastX;
                            float deltaY = event.getY() - lastY;
                            lastX = event.getX();
                            lastY = event.getY();
                            float sensitivityMultiplier = 15;
                            float rx = (deltaX * sensitivityMultiplier);
                            float ry = (deltaY * sensitivityMultiplier);
                            setCameraState(0, rx);
                            setCameraState(1, ry);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isTouching = false;
                        setCameraState(0, 0.0f);
                        setCameraState(1, 0.0f);
                        break;
                }
                return TouchAreaEnabled && AllControlsEnabled;
            }
        });
    }

    private void setupJoystick(FrameLayout joystickLayout, ImageView joystickKnob, boolean isLeft) {
        joystickLayout.post(() -> {
            final float joystickCenterX = joystickLayout.getWidth() / 2f;
            final float joystickCenterY = joystickLayout.getHeight() / 2f;

            joystickLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (!AllControlsEnabled) return false;
                    
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            float deltaX = event.getX() - joystickCenterX;
                            float deltaY = event.getY() - joystickCenterY;
                            float maxRadius = joystickLayout.getWidth() / 2f - joystickKnob.getWidth() / 2f;
                            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                            if (distance > maxRadius) {
                                float scale = maxRadius / distance;
                                deltaX *= scale;
                                deltaY *= scale;
                            }
                            joystickKnob.setX(joystickCenterX + deltaX - joystickKnob.getWidth() / 2f);
                            joystickKnob.setY(joystickCenterY + deltaY - joystickKnob.getHeight() / 2f);

                            short x = (short) (deltaX / maxRadius * Short.MAX_VALUE);
                            short y = (short) (deltaY / maxRadius * Short.MAX_VALUE);
                            setAxis(isLeft ? ControllerButtons.AXIS_LX : ControllerButtons.AXIS_RX, x);
                            setAxis(isLeft ? ControllerButtons.AXIS_LY : ControllerButtons.AXIS_RY, y);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            joystickKnob.setX(joystickCenterX - joystickKnob.getWidth() / 2f);
                            joystickKnob.setY(joystickCenterY - joystickKnob.getHeight() / 2f);
                            setAxis(isLeft ? ControllerButtons.AXIS_LX : ControllerButtons.AXIS_RX, (short) 0);
                            setAxis(isLeft ? ControllerButtons.AXIS_LY : ControllerButtons.AXIS_RY, (short) 0);
                            break;
                    }
                    return true;
                }
            });
        });
    }
}
