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

import com.example.whatsappandroid.R;
import com.example.whatsappandroid.utilities.Info;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private ImageView viewImage;
    private Button b;
    private Button register;
    private TextView tv;
    private FloatingActionButton login;
    private FloatingActionButton settings;
    private TextInputLayout username;
    private TextInputLayout nickname;
    private TextInputLayout password;
    private TextInputLayout repeatPassword;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@#$%^&*():/_-]" +
                                                    "[a-zA-Z0-9!@#$%^&*():/_-]+$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        b = findViewById(R.id.btnSelectPhoto);
        viewImage = findViewById(R.id.viewImageProfilePhoto);
        tv = findViewById(R.id.textViewErrorImgRegister);
        register = findViewById(R.id.RegisterButtonRegister);
        register.setOnClickListener(v -> {
            confirmInput();
        });
        settings = findViewById(R.id.btnToSettingsRegister);
        settings.setOnClickListener(v ->{
            Intent i = new Intent(RegisterActivity.this, SettingsActivity.class);
            startActivity(i);
        });
        username = findViewById(R.id.usernameRegister);
        username.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateUsername();
            return false;
        });
        nickname = findViewById(R.id.nicknameRegister);
        nickname.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateNickname();
            return false;
        });
        password = findViewById(R.id.passwordRegister);
        password.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validatePassword();
            return false;
        });
        repeatPassword = findViewById(R.id.repeatPasswordRegister);
        repeatPassword.getEditText().setOnKeyListener((v, keyCode, event) -> {
            validateRepeatPassword();
            return false;
        });

        b.setOnClickListener(v -> selectImage());

        login = findViewById(R.id.btnToLogin);

        login.setOnClickListener(v -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("Please allow this app permissions to files (gallery) and camera");
                        } else {
                            tv.setVisibility(View.INVISIBLE);
                            startActivityForResult(takePictureIntent, 1);
                        }
                    } else {
                        tv.setVisibility(View.INVISIBLE);
                        startActivityForResult(takePictureIntent, 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (options[item].equals("Choose from Gallery")) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            tv.setVisibility(View.VISIBLE);
                            tv.setText("Please allow this app permissions to files (gallery) and camera");
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            tv.setVisibility(View.INVISIBLE);
                            startActivityForResult(intent, 2);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        tv.setVisibility(View.INVISIBLE);
                        startActivityForResult(intent, 2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
            if (requestCode == 1) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    viewImage.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                try {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                viewImage.setImageBitmap(thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validateUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();
        if (usernameInput == null || usernameInput.length() == 0) {
            username.setError("Username can not be empty");
            return false;
        }

        else if (!NAME_PATTERN.matcher(usernameInput).matches()) {
            username.setError("Username must have at least 2 English/numbers chars");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validateNickname() {
        String nicknameInput = nickname.getEditText().getText().toString().trim();
        if (nicknameInput == null || nicknameInput.length() == 0) {
            nickname.setError("Nickname can not be empty");
            return false;
        }

        else if (!NAME_PATTERN.matcher(nicknameInput).matches()) {
            nickname.setError("Nickname must have at least 2 English/numbers chars");
            return false;
        } else {
            nickname.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput == null || passwordInput.length() == 0) {
            password.setError("Password can not be empty");
            return false;
        }

        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password must have at least 2" +
                    " English, numbers or special chars");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean validateRepeatPassword() {
        String repeatPasswordInput = repeatPassword.getEditText().getText().toString().trim();
        String passwordInput = password.getEditText().getText().toString().trim();
        if (repeatPasswordInput == null || repeatPasswordInput.length() == 0) {
            repeatPassword.setError("Repeat password can not be empty");
            return false;
        }

        else if (!passwordInput.equals(repeatPasswordInput)) {
            repeatPassword.setError("Repeat password must be equal to Password");
            return false;
        } else {
            repeatPassword.setError(null);
            return true;
        }
    }

    public void confirmInput() {
        boolean valid = validateUsername() || validateNickname() ||
                validatePassword() || validateRepeatPassword();
        if (!valid) {
            return;
        }
        String input = "Welcome " + username.getEditText().getText().toString().trim() + "!";
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        Intent contactsListIntent = new Intent(this, ContactsListActivity.class);

        // pass username to the contacts list screen
        Info.loggedUser = username.getEditText().getText().toString().trim();
        startActivity(contactsListIntent);
    }
}