package com.example.ali.myapplication.Activities.Uc_Ui;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.myapplication.Activities.ModelClasses.Polio_Team;
import com.example.ali.myapplication.Activities.ModelClasses.Team_MemberObject;
import com.example.ali.myapplication.Activities.Utils.FirebaseHandler;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Sami Khan on 11/12/2017.
 */

public class Add_Team_View extends android.support.v4.app.Fragment {

    private static final int CAMERA_REQUEST = 1;
    private static final int SELECTED_PICTURE = 2;


    public static TextView ActionBartitle;
    public Spinner team_member_type;
    public EditText team_mname;
    public EditText team_mnic_no;
    public EditText team_maddress;
    public EditText team_mphone_no;
    public String [] membership_type= {"Head Member","Member"};
    public ArrayAdapter memberShipAdapter;
    public ImageView back_arrow;
    public Button add_member;
    public Polio_Team polio_team;
    public EditText team_email;
    public ImageView member_image;
    public Uri mCapturedImageURI;
    public Intent intent;
    private String imgPath;
    ProgressDialog progressDialog;
    private StorageReference rootStorageRef, imageRef, folderRef, fileStorageRef;
    private ProgressDialog mProgressDialog;
    private String downloadURL = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_team_view,null);
        initializeView(view);

        if(getArguments()!=null){
            if(getArguments().getParcelable("obj")!=null){
                polio_team = getArguments().getParcelable("obj");
            }
        }

        member_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Upload Image");
                alert.setMessage("Want to upload image..?");
                alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
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
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
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


        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (team_mname.getText().toString().length() == 0) {
                    team_mname.setError("Enter Member Name");
                    //   flag = false;
                }
                else  if (team_email.getText().toString().length() == 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(team_email.getText().toString()).matches()) {
                    team_email.setError("Enter Member Email");
                    // flag = false;
                }
                else  if (team_mnic_no.getText().toString().length() == 0) {
                    team_mnic_no.setError("Enter NIC No");
                    //  flag = false;
                }
                else  if (team_mphone_no.getText().toString().length() == 0) {
                    team_mphone_no.setError("Enter Phone Number");
                    //   flag = false;
                } else if(downloadURL.equals("")) {
                    Snackbar.make(view,"Upload Picture",Snackbar.LENGTH_LONG).show();

                }else{


                    DatabaseReference reference = FirebaseHandler.getInstance().getPolio_teams().child(polio_team.getTeam_uid()).push();

                    final Team_MemberObject team_memberObject = new Team_MemberObject(team_mname.getText().toString(), team_email.getText().toString()
                            , team_mnic_no.getText().toString(), team_member_type.getSelectedItem().toString(), team_mphone_no.getText().toString(),reference.getKey(),downloadURL);

                    FirebaseHandler.getInstance().getPolio_teams()
                            .child(polio_team.getTeam_uid()).child(reference.getKey()).setValue(team_memberObject, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            getActivity().getSupportFragmentManager().popBackStack();
                            Bundle bundle= new Bundle();
                            bundle.putParcelable("obj",polio_team);
                            bundle.putParcelable("member",team_memberObject);
                            Add_PolioTeam_Fragment add_team_view = new Add_PolioTeam_Fragment();
                            add_team_view.setArguments(bundle);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.add_member_container, add_team_view)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });
                }

            }
        });





        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 1) {
                //for camera
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(data.getData(), projection, null, null, null);
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
                        Cursor myCursor = getActivity().managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projectionn, selection, null, sort);

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
                        myCursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, largeFileProjection, null, null, largeFileSort);
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
                Cursor cursor = getActivity().getContentResolver().query(mCapturedImageURI, imgHolder, null, null, null);
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
            Toast.makeText(getActivity(), "Nothing Selected !", Toast.LENGTH_LONG).show();
        }


    }

    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Team Member");
        team_member_type = (Spinner)view.findViewById(R.id.team_member_type);
        team_mname = (EditText)view.findViewById(R.id.team_mname);
        member_image = (ImageView)view.findViewById(R.id.member_image);
        team_mnic_no = (EditText)view.findViewById(R.id.team_mnic_no);
        team_email = (EditText)view.findViewById(R.id.team_email);
        team_maddress = (EditText)view.findViewById(R.id.team_maddress);
        team_mphone_no = (EditText)view.findViewById(R.id.team_mphone_no);
        add_member = (Button)view.findViewById(R.id.add_member);

        memberShipAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, membership_type);
        team_member_type.setAdapter(memberShipAdapter);

        team_member_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



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
            mProgressDialog = ProgressDialog.show(getActivity(), "Uploading Image", "loading...", true, false);
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
                    Toast.makeText(getActivity(), "UPLOAD FAILD", Toast.LENGTH_LONG).show();
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

    private Uri beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(), Add_Team_View.this);
//        Crop.of(source,destination).withMaxSize(100,100).start(getActivity(), TabFragment1.this);

        return destination;
    }

    private void handleCrop(int resultCode, final Intent result, Uri mCapturedImageURI) {

        // imgPath = null;
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap bitmap = null;
            try {
                if (Build.VERSION.SDK_INT <= 19) {
                    //     bitmap =(Bitmap) mCapturedImageURI.
                    //   InputStream image_stream =getActivity().getContentResolver().openInputStream(mCapturedImageURI);

                    //   bitmap= BitmapFactory.decodeStream(image_stream);
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mCapturedImageURI);

                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Crop.getOutput(result));
                }

                member_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
