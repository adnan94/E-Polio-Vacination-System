package com.example.ali.myapplication.Activities.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.util.Util;
import com.example.ali.myapplication.Activities.Adaptor.Navigations_ItemsAdapter;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.Activities.Uc_Ui.About_Fragment;
import com.example.ali.myapplication.Activities.Uc_Ui.SettingFragment;
import com.example.ali.myapplication.Activities.User_Ui.Add_form;
import com.example.ali.myapplication.Activities.User_Ui.UserFormListScreen;
import com.example.ali.myapplication.Activities.User_Ui.UserHomeFragment;
import com.example.ali.myapplication.Activities.User_Ui.UserTerms_Fragment;
import com.example.ali.myapplication.Activities.Utils.SharedPref;
import com.example.ali.myapplication.Activities.Utils.Utils;
import com.example.ali.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHome extends AppCompatActivity {

    Button form_btn_list, form_btn_details;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public LinearLayout customer_container;
    public String[] menuName = {"Home", "Terms & Conditions", "View Fill Forms", "Add Form", "Setting", "Logout"};
    public int a[]={R.drawable.home,R.drawable.terms,R.drawable.view_token,
            R.drawable.file,R.drawable.settingss,R.drawable.logout};
    public ImageView back_arrow;
    private Uri destination;
    StorageReference storegeRef, imgRef;

    public static TextView ActionBartitle;
    private CircleImageView iv;
    //  public LinearLayout e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_outside);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        //  back_arrow.setVisibility(View.INVISIBLE);
        back_arrow.setImageResource(R.mipmap.menu);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("User Dashboard");
        Utils.relwayMedium(UserHome.this,ActionBartitle);
        getSupportFragmentManager().beginTransaction().add(R.id.maincontainer, new UserHomeFragment()).commit();
        storegeRef = FirebaseStorage.getInstance().getReference();

        customer_container = (LinearLayout) findViewById(R.id.maincontainer);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //  e = (LinearLayout)findViewById(R.id.e);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View viewinflate = UserHome.this.getLayoutInflater().inflate(R.layout.nav_header, null);
        TextView name = (TextView) viewinflate.findViewById(R.id.nameNav);
        TextView email = (TextView) viewinflate.findViewById(R.id.emailNav);
        iv = (CircleImageView) viewinflate.findViewById(R.id.ivNav);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });
        Utils.relwayRegular(getApplicationContext(), name);
        Utils.relwayRegular(getApplicationContext(), email);
        name.setText(UserModel.myObj.getName());
        email.setText(UserModel.myObj.getEmail());

        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(UserHome.this, menuName, a);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(UserHome.this, drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                customer_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserHomeFragment()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 6) {
                //    getSupportFragmentManager().popBackStack();
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 3) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserFormListScreen()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 4) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new Add_form()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 5) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new SettingFragment()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 2) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, new UserTerms_Fragment()).addToBackStack(null).commit();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });

        getNavImage();
    }

    private void getNavImage() {
        Glide.with(UserHome.this)
                .load(UserModel.myObj.getPicUrl())
                .asBitmap()
                .placeholder(R.mipmap.user_image)
                .error(R.mipmap.user_image)

                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv.setImageBitmap(resource);
                    }
                });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 1) {
            try {
                Uri imageUri = data.getData();
                destination = imageUri;
                Crop.of(imageUri, destination).asSquare().start(UserHome.this);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            if (destination != null) {

                iv.setImageURI(destination);
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(destination);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                final ProgressDialog progress = new ProgressDialog(UserHome.this);
                progress.setMessage("Please wait while uploading picture");
                progress.show();
                String path = getRealPathFromURI(destination);
                getImage2(path, stream.toByteArray(), progress);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    public void getImage2(String path, final byte[] array, final ProgressDialog progress) {

        Uri file = Uri.fromFile(new File(path));
        Log.d("TAG", file.toString());
        File f = new File(path);
        Random r = new Random();
        int i1 = (r.nextInt(80) + 65);
        imgRef = storegeRef.child("UserImages").child(f.getName() + i1);

        final UploadTask uploadTask = imgRef.putBytes(array);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                final String url = taskSnapshot.getDownloadUrl().toString();
            //    UserModel userModel = SharedPref.getCurrentUser(getApplicationContext());
            //    userModel.setPicUrl(url);
            //    SharedPref.setCurrentUser(getApplicationContext(), userModel);

                FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("picUrl").setValue(url, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Utils.toast(getApplicationContext(), "Uploaded");
                        progress.dismiss();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
            }
        });

    }
}


