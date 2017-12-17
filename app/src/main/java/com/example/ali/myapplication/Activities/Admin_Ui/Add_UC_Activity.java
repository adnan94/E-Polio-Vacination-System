package com.example.ali.myapplication.Activities.Admin_Ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ali.myapplication.Activities.Activity.AdminHome;
import com.example.ali.myapplication.Activities.ModelClasses.UC_Object;
import com.example.ali.myapplication.Activities.Uc_Ui.Add_Team_View;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_UC_Activity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    private static final int SELECTED_PICTURE = 2;
    public Spinner uc_area;
    public EditText uc_team_name;
    public EditText uc_team_nic_no;
    public EditText uc_member_email;
    public EditText uc_member_phone_no;
    public Button add_member;
    public String[] uc_areas = {"Malir","Central","South","East","West","Korangi"};
    public ArrayAdapter<String> arrayAdapter;
    public CircleImageView member_image;
    private String downloadURL = "";
    ProgressDialog progressDialog;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private ProgressDialog mProgressDialog;
    public Uri mCapturedImageURI;
    private String imgPath;
    public ImageView back_image;
    public String key="";
    public UC_Object uc_object;
    public Toolbar toolbar_outside;
    public TextView ActionBartitle;
    public ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__uc_);

        toolbar_outside = (Toolbar)findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar_outside);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar_outside.findViewById(R.id.back_image);
        //  back_arrow.setVisibility(View.INVISIBLE);
//        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle = (TextView) toolbar_outside.findViewById(R.id.main_appbar_textView);
        Utils.relwayMedium(Add_UC_Activity.this,ActionBartitle);
        ActionBartitle.setText("Create UC Member");


        back_image = (ImageView)findViewById(R.id.back_image);
        member_image = (CircleImageView)findViewById(R.id.member_image);
        uc_area = (Spinner)findViewById(R.id.uc_area);
        uc_member_email  = (EditText)findViewById(R.id.uc_member_email);
        Utils.relwayRegular(Add_UC_Activity.this,uc_member_email);
        uc_team_name  = (EditText)findViewById(R.id.uc_team_name);
        Utils.relwayRegular(Add_UC_Activity.this,uc_team_name);
        uc_team_nic_no = (EditText)findViewById(R.id.uc_team_nic_no);
        Utils.relwayRegular(Add_UC_Activity.this,uc_team_nic_no);
        uc_member_phone_no = (EditText)findViewById(R.id.uc_member_phone_no);
        Utils.relwayRegular(Add_UC_Activity.this,uc_member_phone_no);
        add_member  = (Button)findViewById(R.id.add_member);
        Utils.relwayRegular(Add_UC_Activity.this,add_member);
        rootStorageRef = FirebaseStorage.getInstance().getReference();
        folderRef = rootStorageRef.child("user_profile_images");
        arrayAdapter = new ArrayAdapter<String>(Add_UC_Activity.this,android.R.layout.simple_list_item_1,uc_areas);
        uc_area.setAdapter(arrayAdapter);


        if(getIntent().hasExtra("obj")){
            uc_object = getIntent().getParcelableExtra("obj");
            uc_team_name.setText(uc_object.getUc_membern());
            uc_member_email.setText(uc_object.getUc_member_email());
            uc_team_nic_no.setText(uc_object.getUc_member_cnic());
            uc_member_phone_no.setText(uc_object.getUc_member_phone());
            int spinnerPosition = arrayAdapter.getPosition(uc_object.getUc_area());
            uc_area.setSelection(spinnerPosition);
           key=uc_object.getUc_member_uid();
            downloadURL=uc_object.getUc_member_piture();
            Glide.with(Add_UC_Activity.this)
                    .load(uc_object.getUc_member_piture())
                    .asBitmap()
                    .placeholder(R.drawable.user)
                    .into(member_image);


        }



        uc_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(Add_UC_Activity.this, "Add UC", "Adding...", true, false);


                if(uc_team_name.getText().toString().length()==0 || uc_team_name.getText().toString().matches("\"^[a-zA-Z]+( [a-zA-z]+)*$\"")){
                   // uc_team_name.setError("Enter Valid name");
                    Snackbar.make(view,"Enter Valid Name",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(uc_member_email.getText().toString().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(uc_member_email.getText().toString()).matches()){
                    Snackbar.make(view,"Enter Valid Email",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(uc_team_nic_no.getText().toString().length()==0 || uc_team_nic_no.getText().toString().length() > 13 || uc_team_nic_no.getText().toString().length() < 13){
                    Snackbar.make(view,"Enter Valid CNIC",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(uc_member_phone_no.getText().toString().length()==0 || uc_member_phone_no.getText().toString().length() > 11 || uc_member_phone_no.getText().toString().length() < 11 ){
                    Snackbar.make(view,"Enter Valid Phone Number",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(downloadURL.equals("")){
                    Snackbar.make(view,"Upload Image",Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{

                    if(key.equals("")) {
                        key = FirebaseHandler.getInstance().getUc_members().push().getKey();
                    }

                    UC_Object uc_object = new UC_Object(uc_area.getSelectedItem().toString(),uc_team_name.getText().toString(),uc_team_nic_no.getText().toString()
                                    ,uc_member_email.getText().toString(),uc_member_phone_no.getText().toString(),downloadURL,key);



                    FirebaseHandler.getInstance().getUc_members().child(key).setValue(uc_object, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            progressDialog.dismiss();
                            finish();
                        }
                    });


                }
            }
        });

            back_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Add_UC_Activity.this);
                alert.setTitle("Upload Image");
                alert.setMessage("Want to upload image..?");
                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (ContextCompat.checkSelfPermission(Add_UC_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Add_UC_Activity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Add_UC_Activity.this,
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {
                            //       if(Build.VERSION.SDK_INT >16) {
                            //          File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Images");
                            //         imagesFolder.mkdirs();
                            //         File image = new File(imagesFolder.getPath(), "MyImage_.jpg");
                            //        String fileName = "temp.jpg";
                            //           ContentValues values = new ContentValues();
                            //          values.put(MediaStore.Images.Media.TITLE, image.getAbsolutePath());
                            //           mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            //     }


                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            //     cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        }

                    }
                });

                alert.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  mayRequestContacts();
                        if (ContextCompat.checkSelfPermission(Add_UC_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Add_UC_Activity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Add_UC_Activity.this,
                                    new String[]{
                                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    2);
                        } else {

                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, SELECTED_PICTURE);
                        }
                    }
                });


                alert.create().show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_OK) {
            if (requestCode == 1) {
                //for camera
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(data.getData(), projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imgPath = cursor.getString(column_index_data);
                cursor.close();

                if (Build.VERSION.SDK_INT <= 19) {
                    handleCrop(resultCode, data, mCapturedImageURI);
                } else {
                    if (data == null) {
                        String[] projectionn = {
                                MediaStore.Images.Thumbnails._ID,  // The columns we want
                                MediaStore.Images.Thumbnails.IMAGE_ID,
                                MediaStore.Images.Thumbnails.KIND,
                                MediaStore.Images.Thumbnails.DATA};
                        String selection = MediaStore.Images.Thumbnails.KIND + "=" + // Select only mini's
                                MediaStore.Images.Thumbnails.MINI_KIND;

                        String sort = MediaStore.Images.Thumbnails._ID + " DESC";

//At the moment, this is a bit of a hack, as I'm returning ALL images, and just taking the latest one. There is a better way to narrow this down I think with a WHERE clause which is currently the selection variable
                        Cursor myCursor = this.managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projectionn, selection, null, sort);

                        long imageId = 0l;
                        long thumbnailImageId = 0l;
                        String thumbnailPath = "";

                        try {
                            myCursor.moveToFirst();
                            imageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
                            thumbnailImageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
                            thumbnailPath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
                        } finally {
                            myCursor.close();
                        }

                        //Create new Cursor to obtain the file Path for the large image

                        String[] largeFileProjection = {
                                MediaStore.Images.ImageColumns._ID,
                                MediaStore.Images.ImageColumns.DATA
                        };

                        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
                        myCursor = this.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, largeFileProjection, null, null, largeFileSort);
                        String largeImagePath = "";

                        try {
                            myCursor.moveToFirst();

//This will actually give yo uthe file path location of the image.
                            largeImagePath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
                        } finally {
                            myCursor.close();
                        }
                        // These are the two URI's you'll be interested in. They give you a handle to the actual images
                        Uri uriLargeImage = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));
                        Uri uriThumbnailImage = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, String.valueOf(thumbnailImageId));
                        beginCrop(uriLargeImage);
                    } else {
                        beginCrop(data.getData());
                    }
                }

            } else if (requestCode == 2) {
                //for gallery
                //   beginCrop(data.getData());

                mCapturedImageURI = data.getData();
                String[] imgHolder = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(imgHolder[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();


                if (Build.VERSION.SDK_INT > 19) {
                    beginCrop(data.getData());
                } else {
                    handleCrop(resultCode, data, mCapturedImageURI);
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data, mCapturedImageURI);
            }

        } else {
            Toast.makeText(Add_UC_Activity.this, "Nothing Selected !", Toast.LENGTH_LONG).show();
        }


    }

    private Uri beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(Add_UC_Activity.this.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(Add_UC_Activity.this);
//        Crop.of(source,destination).withMaxSize(100,100).start(getActivity(), TabFragment1.this);

        return destination;
    }

    private void handleCrop(int resultCode, final Intent result, Uri mCapturedImageURI) {

        // imgPath = null;
        if (resultCode == this.RESULT_OK) {
            Bitmap bitmap = null;
            try {
                if (Build.VERSION.SDK_INT <= 19) {
                    //     bitmap =(Bitmap) mCapturedImageURI.
                    //   InputStream image_stream =getActivity().getContentResolver().openInputStream(mCapturedImageURI);

                    //   bitmap= BitmapFactory.decodeStream(image_stream);
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mCapturedImageURI);

                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Crop.getOutput(result));
                }

                member_image.setImageBitmap(bitmap);
                uploadImage(member_image);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(Add_UC_Activity.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(ImageView imgPath) {
        try {
            //   File fileRef = new File(imgPath);


            final Date date = new Date(System.currentTimeMillis());
            //   final String filenew = fileRef.getName();
            //    AppLogs.d("fileNewName", filenew);
            //    int dot = filenew.lastIndexOf('.');
            //     String base = (dot == -1) ? filenew : filenew.substring(0, dot);
            //     final String extension = (dot == -1) ? "" : filenew.substring(dot + 1);
            //     AppLogs.d("extensionsss", extension);
            mProgressDialog = ProgressDialog.show(Add_UC_Activity.this, "Uploading Image", "loading...", true, false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            UploadTask uploadTask;
            // Uri file = Uri.fromFile(new File(imgPath));
            imageRef = folderRef.child(String.valueOf(date) + ".png");

            imgPath.setDrawingCacheEnabled(true);
            imgPath.buildDrawingCache();
            Bitmap b = imgPath.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            //  UploadTask uploadTask = ref.child(id + ".png").putBytes(bytes);


            uploadTask = imageRef.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    mProgressDialog.dismiss();
                    Toast.makeText(Add_UC_Activity.this, "UPLOAD FAILD", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    Log.e("Image ka URL", "" + downloadUrl);
                    downloadURL = downloadUrl;
                    mProgressDialog.dismiss();
                    //   FirebaseHandler.getInstance().getUsersRef().child(polio_team.getTeam_uid()).child("imageUrl").setValue(downloadUrl);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
