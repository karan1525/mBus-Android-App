package io.github.karan.mbus.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import io.github.karan.mbus.R;
import io.github.karan.mbus.controllers.CameraUtility;
import io.github.karan.mbus.database.BusDBOperations;
import io.github.karan.mbus.models.User;


public class mBus_ProfileActivity extends AppCompatActivity {

    private final int REQUEST_CAMERA = 0;
    private final int SELECT_FILE = 1;
    private ImageView ivImage;
    private String userChosenTask;

    private BusDBOperations mBusOps;

    private EditText nameEditText;
    private Spinner genderSpinner;
    private TextView busBooked;
    private TextView tripsTaken;

    private static boolean mIsFirstRun = true;
    private static User mSavedUser;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_bus__profile);
        Button btnSelect = findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = findViewById(R.id.ivImage);

        newUser = new User();
        nameEditText = findViewById(R.id.user_name);
        genderSpinner = findViewById(R.id.gender_spinner);
        busBooked = findViewById(R.id.bus_booked_content_view);
        tripsTaken = findViewById(R.id.trips_taken_content_view);

        mBusOps = new BusDBOperations(this);

    }

    private void checkIfUserExists() {
        User myCurrentUser = mBusOps.getUser(1);
        nameEditText.setText(myCurrentUser.getName());
        nameEditText.setEnabled(false);

        busBooked.setText(R.string.user_bus_number);
        tripsTaken.setText(R.string.user_trips_taken);

        String gender = myCurrentUser.getGender();
        if (gender.equalsIgnoreCase("Male")) {
            genderSpinner.setSelection(0);
        } else {
            genderSpinner.setSelection(1);
        }

        mIsFirstRun = false;
    }

    @SuppressWarnings({"UnusedAssignment", "unused"})
    private void getAllData() {

        String busNumberBooked = "";
        String numberTripsTaken = "";

        List<User> allUsers = mBusOps.getAllUsers();

        for (User user: allUsers) {
            busNumberBooked = user.getBusBooked();
            numberTripsTaken = user.getTripsTaken();
        }

        busBooked.setText(R.string.user_bus_number);
        tripsTaken.setText(R.string.user_trips_taken);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CameraUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    if(userChosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    TastyToast.makeText(getApplicationContext(),"Permission Denied",
                            TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(mBus_ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int item) {
                boolean result = CameraUtility.checkPermission(mBus_ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChosenTask = "Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChosenTask = "Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(@NonNull Intent data) {
        @SuppressWarnings("ConstantConditions")
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            //noinspection ResultOfMethodCallIgnored
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(@Nullable Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                        data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mIsFirstRun) {
            mIsFirstRun = false;
            saveUserInformation();
        }

        Intent intent = new Intent(this, mBus_HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private void saveUserInformation() {
        if (!nameEditText.getText().toString().isEmpty()) {
            newUser.setName(nameEditText.getText().toString());
        }

        newUser.setGender(genderSpinner.getSelectedItem().toString());
        newUser.setTripsTaken(String.valueOf(R.string.user_trips_taken));
        newUser.setBusBooked(String.valueOf(R.string.user_bus_number));

        if((!nameEditText.getText().toString().isEmpty() &&
                !mSavedUser.getName().equalsIgnoreCase(newUser.getName()))) {
            mSavedUser = mBusOps.addUser(newUser);
            TastyToast.makeText(this, "User " + newUser.getName()
                            + " has been added successfully",
                    TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        } else if (nameEditText.getText().toString().isEmpty()) {
            TastyToast.makeText(this, "User adding failed! Try again",
                    TastyToast.LENGTH_LONG, TastyToast.ERROR);
        } else {
            TastyToast.makeText(this, "User " + newUser.getName()
                            + " was already in the DB. Try again",
                    TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBusOps.open();

        mSavedUser = mBusOps.getUser(1);

        if (!mIsFirstRun) {
            checkIfUserExists();
            getAllData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBusOps.close();
    }

}