package com.example.saruhan.not;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.firebase.client.Firebase;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final int SELECT_PHOTO = 1000;
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageRef,imageRef;
    ProgressDialog progressDialog;
    UploadTask uploadTask;


    EditText icerik;
    ImageView imageView;
    String lessonKey;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView) findViewById(R.id.imageView2);
        icerik = (EditText) findViewById(R.id.editTextİcerik);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();
    //    deparmentKey = getIntent().getExtras().getString("key");
        mDatabase = FirebaseDatabase.getInstance().getReference("Notlar");
    }

    public void selectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(ImageActivity.this,"Image selected, click on upload button",Toast.LENGTH_SHORT).show();

                    selectedImage = imageReturnedIntent.getData();

                }
        }
    }
    public void uploadImage(View view) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        final String textNoteAd = icerik.getText().toString().trim();
        final String lessonKey = getIntent().getExtras().getString("lessonKey");

        auth = FirebaseAuth.getInstance();
        final String userID = auth.getCurrentUser().getUid();

        imageRef = storageRef.child(userID).child(selectedImage.getLastPathSegment());

        uploadTask = imageRef.putFile(selectedImage);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                progressDialog.incrementProgressBy((int) progress);
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Toast.makeText(ImageActivity.this,"Error in uploading!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                startActivity(new Intent(ImageActivity.this, LessonActivity.class));
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();


                Toast.makeText(ImageActivity.this,"Upload successful",Toast.LENGTH_SHORT).show();

                DatabaseReference newPost = mDatabase.push();
                String noteKey = newPost.getKey();
                String imageUri = downloadUri.toString();


                newPost.setValue(new Note(userID, lessonKey, noteKey, textNoteAd, imageUri));


                progressDialog.dismiss();
                Picasso.with(ImageActivity.this).load(downloadUri).into(imageView);
            }
        });
    }
}


