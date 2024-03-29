package com.example.whatsappandroid.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.activities.contactsActivity.MainContactsActivity;
import com.example.whatsappandroid.api.ConnectToFirebaseApi;
import com.example.whatsappandroid.successables.Successable;
import com.example.whatsappandroid.utilities.Info;
import com.example.whatsappandroid.viewModels.RegisterViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements Successable {
    private RegisterViewModel registerViewModel;
    private Bitmap picture;
    private ImageView viewImage;
    private Button b;
    private Button registerBtn;
    private TextView tv;
    private FloatingActionButton loginBtn;
    private FloatingActionButton settingsBtn;
    private TextInputLayout usernameTIL;
    private String username;
    private TextInputLayout nicknameTIL;
    private String nickname;
    private TextInputLayout passwordTIL;
    private String password;
    private TextInputLayout repeatPasswordTIL;
    private String repeatPassword;
    private String fbToken;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@#$%^&*():/_-]" +
            "[a-zA-Z0-9!@#$%^&*():/_-]+$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        picture = null;
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.setSuccessable(this);
        findViews();
        setListeners();
    }

    @Override
    public void onSuccess() {
        String input = "Welcome " + username + "!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

        // if registered successfully then we send to the server his token.
        ConnectToFirebaseApi connectToFirebaseApi = new ConnectToFirebaseApi();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(RegisterActivity.this,
                        instanceIdResult -> {
                            fbToken = instanceIdResult.getToken();
                        });
        fbToken = FirebaseInstanceId.getInstance().getToken();
        connectToFirebaseApi.connectToFB(username, fbToken);
        Intent contactsListIntent = new Intent(this,
                MainContactsActivity.class);

        // pass username to the contacts list screen
        Info.loggedUser = username;
        clearTextBoxes();
        startActivity(contactsListIntent);
    }

    @Override
    public void onFail() {
        clearTextBoxes();
        String input = "Something went wrong! please try again!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    // clear user's input
    protected void clearTextBoxes() {
        usernameTIL.getEditText().setText("");
        passwordTIL.getEditText().setText("");
        repeatPasswordTIL.getEditText().setText("");
        nicknameTIL.getEditText().setText("");
        viewImage.setImageResource(R.drawable.ic_default_profile_pid);
    }

    private void setListeners() {
        registerBtn.setOnClickListener(v -> {
            if (confirmInput() == 1) {

                // pass to the viewmodel the username password nickname and picture if has
                byte[] byteArrayPic = null;
                if (picture != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    picture.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byteArrayPic = baos.toByteArray();
                }
                registerViewModel.registerUser(username, password, nickname, byteArrayPic);
            }
        });

        settingsBtn.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, SettingsActivity.class);
            startActivity(i);
        });

        usernameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            username = usernameTIL.getEditText().getText().toString().trim();
            validateUsername();
            return false;
        });

        nicknameTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            nickname = nicknameTIL.getEditText().getText().toString().trim();
            validateNickname();
            return false;
        });

        passwordTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            password = passwordTIL.getEditText().getText().toString().trim();
            validatePassword();
            return false;
        });

        repeatPasswordTIL.getEditText().setOnKeyListener((v, keyCode, event) -> {
            repeatPassword = repeatPasswordTIL.getEditText().getText().toString().trim();
            validateRepeatPassword();
            return false;
        });

        b.setOnClickListener(v -> selectImage());

        loginBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void findViews() {
        b = findViewById(R.id.btnSelectPhoto);
        tv = findViewById(R.id.textViewErrorImgRegister);
        viewImage = findViewById(R.id.viewImageProfilePhoto);
        registerBtn = findViewById(R.id.RegisterButtonRegister);
        settingsBtn = findViewById(R.id.btnToSettingsRegister);
        usernameTIL = findViewById(R.id.usernameRegister);
        nicknameTIL = findViewById(R.id.nicknameRegister);
        passwordTIL = findViewById(R.id.passwordRegister);
        repeatPasswordTIL = findViewById(R.id.repeatPasswordRegister);
        loginBtn = findViewById(R.id.btnToLogin);
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, (dialog, item) -> {

            // if choose 'Take Photo' then ask for permissions to the camera and take a picture
            if (options[item].equals("Take Photo")) {
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CAMERA}, 100);
                        if (ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("Please allow this app permissions" +
                                    "to files (gallery) and camera");
                        } else {
                            tv.setVisibility(View.INVISIBLE);
                            startActivityForResult(takePictureIntent, 1);
                        }
                    } else {
                        tv.setVisibility(View.INVISIBLE);
                        startActivityForResult(takePictureIntent, 1);
                    }
                } catch (Exception e) {
                }
            } else if (options[item].equals("Choose from Gallery")) {

                /*
                * if choose 'Choose from Gallery;
                * then ask for permissions to the Files and choose a picture
                */
                try {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        if (ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("Please allow this app permissions " +
                                    "to files (gallery) and camera");
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            tv.setVisibility(View.INVISIBLE);
                            startActivityForResult(intent, 2);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        tv.setVisibility(View.INVISIBLE);
                        startActivityForResult(intent, 2);
                    }
                } catch (Exception e) {
                }
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // if took a picture then upload this picture to the page
            if (requestCode == 1) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    viewImage.setImageBitmap(imageBitmap);
                    picture = imageBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                // if choose a picture then upload this picture from the phone to the page
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath,
                            null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    viewImage.setImageBitmap(thumbnail);
                    picture = thumbnail;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validateUsername() {
        if (username == null || username.length() == 0) {
            usernameTIL.setError("Username can not be empty");
            return false;
        } else if (!NAME_PATTERN.matcher(username).matches()) {
            usernameTIL.setError("Username must have at least 2 English/numbers chars");
            return false;
        } else {
            usernameTIL.setError(null);
            return true;
        }
    }

    private boolean validateNickname() {
        if (nickname == null || nickname.length() == 0) {
            nicknameTIL.setError("Nickname can not be empty");
            return false;
        } else if (!NAME_PATTERN.matcher(nickname).matches()) {
            nicknameTIL.setError("Nickname must have at least 2 English/numbers chars");
            return false;
        } else {
            nicknameTIL.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        if (password == null || password.length() == 0) {
            passwordTIL.setError("Password can not be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordTIL.setError("Password must have at least 2" +
                    " English, numbers or special chars");
            return false;
        } else {
            passwordTIL.setError(null);
            return true;
        }
    }

    private boolean validateRepeatPassword() {
        if (repeatPassword == null || repeatPassword.length() == 0) {
            repeatPasswordTIL.setError("Repeat password can not be empty");
            return false;
        } else if (!password.equals(repeatPassword)) {
            repeatPasswordTIL.setError("Repeat password must be equal to Password");
            return false;
        } else {
            repeatPasswordTIL.setError(null);
            return true;
        }
    }

    public int confirmInput() {
        boolean valid = validateUsername() || validateNickname() ||
                validatePassword() || validateRepeatPassword();
        if (!valid) {
            return 0;
        }
        return 1;
    }
}