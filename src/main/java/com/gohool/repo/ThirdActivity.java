package com.gohool.repo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.Compressor;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.function.Consumer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public
class ThirdActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final Object CameraActivity = " ";
    private TextView CameraTextview;
    private ImageView CameraImageView;
    private EditText NameEditText;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;




    private ImageView actualImageView;
    private ImageView compressedImageView;
    private TextView actualSizeTextView;
    private TextView compressedSizeTextView;
    private File actualImage;
    private File compressedImage;


    @SuppressLint("WrongThread")
    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_third );

         //compress image
        actualImageView = (ImageView) findViewById(R.id.actual_image);
        compressedImageView = (ImageView) findViewById(R.id.compressed_image);
        actualSizeTextView = (TextView) findViewById(R.id.actual_size);
        compressedSizeTextView = (TextView) findViewById(R.id.compressed_size);

        actualImageView.setBackgroundColor(getRandomColor());
        clearImage();

        // [START storage_field_initialization]
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // [END storage_field_initialization]

        try {
            includesForUploadFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }


        //firebase database Instance
        databaseReference= FirebaseDatabase.getInstance ().getReference ().child ("Main");

        //Assign Instance to Firebase Auth;
        mAuth = FirebaseAuth.getInstance ();
        mAuthListener= new FirebaseAuth.AuthStateListener () {
            @Override
            public
            void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //logic check main
                FirebaseUser user = firebaseAuth.getCurrentUser ();
                if(user!=null){

                }

            }
        };


        CameraTextview = ( TextView ) findViewById ( R.id.CameraTextView );
        CameraImageView = ( ImageView ) findViewById ( R.id.imgPreview );
        NameEditText = ( EditText ) findViewById ( R.id.NameeditText );


        CameraImageView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public
            void onClick(View v) {
                //Logic for image
                ImageSelection();
            }
        } );




    }

    private
    void clearImage() {
        actualImageView.setBackgroundColor(getRandomColor());
        compressedImageView.setImageDrawable(null);
        compressedImageView.setBackgroundColor(getRandomColor());
        compressedSizeTextView.setText("Size : -");

    }

    private
    int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }


    private
    void ImageSelection() {

        //Display Dialog to choose camera

        final CharSequence[] items= {"Take Photo","Cancel"};
        AlertDialog.Builder builder= new AlertDialog.Builder ( ThirdActivity.this );
        builder.setTitle ( "Add photo!" );

        //SET ITEMS AND THERE LISTENERS
        builder.setItems ( items, new DialogInterface.OnClickListener () {
            @Override
            public
            void onClick(DialogInterface dialog, int item) {

                if (items[item].equals ( "Take Photo" )){
                    cameraIntent();
                } else if(items[item].equals ( "Cancel" )){
                    dialog.dismiss ();
                }

            }
        } );
        builder.show ();
    }

    private
    void cameraIntent() {
        Log.d ("gola","entered here");
        Intent intent= new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivity ( intent, ( Bundle ) CameraActivity );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode  == (Integer ) CameraActivity && resultCode == RESULT_OK)
            {
           Uri imageUri = data.getData ();


            }
        else  if (requestCode == (Integer)PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            try {
                actualImage = FileUtil.from(this, data.getData());
                actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                actualSizeTextView.setText(String.format("Size : %s", getReadableFileSize(actualImage.length())));
                clearImage();
            } catch (IOException e) {
                e.printStackTrace ();
                showError("Failed to open picture!");
            }

        }
    }




   public void includesForUploadFiles() throws FileNotFoundException {
       // Create a storage reference from our app
       FirebaseStorage storage = FirebaseStorage.getInstance();


       StorageReference storageRef =  storage.getReference();

// Create a reference to "mountains.jpg"
       StorageReference mountainsRef = storageRef.child("mountains.jpg");

// Create a reference to 'images/mountains.jpg'
       StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");

// While the file names are the same, the references point to different files
       mountainsRef.getName().equals(mountainImagesRef.getName());    // true
       mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

       // [START upload_memory]

       //// Get the data from an ImageView as bytes

       CameraImageView.setDrawingCacheEnabled(true);
       CameraImageView.buildDrawingCache();
       Bitmap bitmap = (( BitmapDrawable ) CameraImageView.getDrawable()).getBitmap();
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
       byte[] data = baos.toByteArray();

       UploadTask uploadTask = mountainsRef.putBytes(data);
       uploadTask.addOnFailureListener(new OnFailureListener () {
           @Override
           public void onFailure(@NonNull Exception exception) {
               // Handle unsuccessful uploads
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot> () {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
               // ...
           }
       });

       // [END upload_memory]




      // // [START upload_file]
       Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
       StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
       uploadTask = riversRef.putFile(file);

       // Register observers to listen for when the download is done or if it fails
       uploadTask.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception exception) {
               // Handle unsuccessful uploads
           }
       }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
               // ...
           }
       });
       // [END upload_file]



       // [START manage_uploads]
       uploadTask = storageRef.child("images/mountains.jpg").putFile(file);

       // Pause the upload
       uploadTask.pause();

       // Resume the upload
       uploadTask.resume();

       // Cancel the upload
       uploadTask.cancel();
       // [END manage_uploads]



       // [START monitor_upload_progress]
       // Observe state change events such as progress, pause, and resume
       uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot> () {
           @Override
           public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
               double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
               System.out.println("Upload is " + progress + "% done");
           }
       }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot> () {
           @Override
           public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
               System.out.println("Upload is paused");
           }
       });
       // [END monitor_upload_progress]



       // [START upload_get_download_url]
       final StorageReference ref = storageRef.child("images/mountains.jpg");
       uploadTask = ref.putFile(file);

       Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>> () {
           @Override
           public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
               if (!task.isSuccessful()) {
                   throw task.getException();
               }

               // Continue with the task to get the download URL
               return ref.getDownloadUrl();
           }
       }).addOnCompleteListener(new OnCompleteListener<Uri> () {
           @Override
           public void onComplete(@NonNull Task<Uri> task) {
               if (task.isSuccessful()) {
                   Uri downloadUri = task.getResult();
               } else {
                   // Handle failures
                   // ...
               }
           }
       });
       // [END upload_get_download_url]


   }


    public
    void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    private
    void showError(String s) {
        int errorMessage = 0;
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private
    void setCompressedImage() {
        compressedImageView.setImageBitmap( BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
        compressedSizeTextView.setText(String.format("Size : %s", getReadableFileSize(compressedImage.length())));

        Toast.makeText(this, "Compressed image save in " + compressedImage.getPath(), Toast.LENGTH_LONG).show();
        Log.d("Compressor", "Compressed image save in " + compressedImage.getPath());

    }

    private
    Object getReadableFileSize(long length) {
        double size = 0;
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat ("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }



}
