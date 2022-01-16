package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AdminUploadItems extends AppCompatActivity implements View.OnClickListener{

    Button choose, msubmit;
    ImageView img;
    public Uri imguri;
    StorageReference storageReference;
    private StorageTask storageTask;
    EditText txtname, txtprice;
    FoodModel foodModel;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_upload_items);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        dref = FirebaseDatabase.getInstance().getReference().child("Items");


        choose = findViewById(R.id.uploadbtn);
        choose.setOnClickListener(this);
        img = findViewById(R.id.images);
        msubmit = findViewById(R.id.submit);
        msubmit.setOnClickListener(this);
        txtname = findViewById(R.id.productname);
        txtprice = findViewById(R.id.price);
        foodModel = new FoodModel();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.uploadbtn:
                FileChooser();
                break;
            case R.id.submit:
                if (storageTask!=null && storageTask.isInProgress()){
                    Toast.makeText(this, "In progress", Toast.LENGTH_SHORT).show();
                }else {
                    FileUpload();
                }
                break;
        }
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void FileUpload() {
        String imageId = System.currentTimeMillis()+"."+getExtension(imguri);
        foodModel.setName(txtname.getText().toString().trim());
        foodModel.setImageId(imageId);
        int price = Integer.parseInt(txtprice.getText().toString().trim());
        foodModel.setPrice(price);
        dref.push().setValue(foodModel);
        StorageReference mStore = storageReference.child(imageId);


// Register observers to listen for when the download is done or if it fails
        storageTask = mStore.putFile(imguri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(AdminUploadItems.this, "Image uploaded Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null &&data.getData()!=null){
            imguri = data.getData();
            img.setImageURI(imguri);
        }
    }

    private void FileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
}