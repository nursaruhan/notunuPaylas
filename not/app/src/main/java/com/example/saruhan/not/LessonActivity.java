package com.example.saruhan.not;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

public class LessonActivity extends AppCompatActivity {

    private RecyclerView imageList;
    private DatabaseReference databaseLesson;
    String lessonKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        databaseLesson = FirebaseDatabase.getInstance().getReference("Notlar");

        imageList = (RecyclerView) findViewById(R.id.lessonList);
        imageList.setHasFixedSize(true);
        imageList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
    //    Toast.makeText(LessonActivity.this,lessonKey,Toast.LENGTH_SHORT).show();
        lessonKey = getIntent().getExtras().getString("lessonKey");
        FirebaseRecyclerAdapter<Note , NoteViewHolder> firebaseRecycleAdapter = new FirebaseRecyclerAdapter <Note, NoteViewHolder>(

                Note.class,
                R.layout.lesson,
                NoteViewHolder.class,
                databaseLesson.orderByChild("lessonKey").equalTo(lessonKey)
        ) {
            @Override
            protected void populateViewHolder(NoteViewHolder viewHolder, Note model, int position) {


                viewHolder.setNoteName(model.getNoteAd());
                viewHolder.setLessonImage(getApplicationContext(),model.getNoteImage());

            }
        };

        imageList.setAdapter(firebaseRecycleAdapter);




    }
        public static class NoteViewHolder extends RecyclerView.ViewHolder {

            View mView;

            //Constrcutor
            public NoteViewHolder(View itemView) {
                super(itemView);

                mView = itemView;
            }


            public void setNoteName(String name) {
                TextView notName = (TextView) mView.findViewById(R.id.notName);
                notName.setText(name);
            }

            public void setLessonImage(Context ctx, String image) {
                ImageView lessonImage = (ImageView) mView.findViewById(R.id.lessonImage);

                Picasso.with(ctx).load(image).into(lessonImage);

            }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_add){

            Intent i = new Intent(LessonActivity.this, ImageActivity.class);
            i.putExtra("lessonKey",lessonKey);
            startActivity(i);

        }

        if(item.getItemId()==R.id.action_settings){

            Intent i = new Intent(LessonActivity.this, FacultiesActivity.class);
            startActivity(i);

        }


        return super.onOptionsItemSelected(item);
    }

}
