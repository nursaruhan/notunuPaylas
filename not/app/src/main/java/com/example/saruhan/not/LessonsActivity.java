package com.example.saruhan.not;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    ListView listViewLessons;
    List<Lesson> lessonsList;
    DatabaseReference databaseLesson;
    FirebaseAuth firebaseAuth;
    String dpName;
    User user;
    DatabaseReference current_user_db;
    String departmentKey;
    TextView textViewLessons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        textViewLessons = (TextView) findViewById(R.id.textViewLessons) ;
        listViewLessons = (ListView) findViewById(R.id.lessonsList) ;
        lessonsList = new ArrayList<>();

        databaseLesson = FirebaseDatabase.getInstance().getReference("Lessons");
        //dptName = (TextView) findViewById(R.id.textViewLessons);

       dpName = getIntent().getExtras().getString("dpName");
        //dpName.setText(departmentName);

/*        DatabaseReference pushRef = databaseLesson.push();
        String key_ID = pushRef.getKey();
        pushRef.setValue(new Lesson(key_ID,"Matematik","Sosyoloji"));

        DatabaseReference pushRef1 = databaseLesson.push();
        String keyID = pushRef1.getKey();
        pushRef1.setValue(new Lesson(keyID,"Sayısal Çözümleme","Bilgisayar Mühendisliği"));

        DatabaseReference pushRef2 = databaseLesson.push();
        String keyID2 = pushRef2.getKey();
        pushRef2.setValue(new Lesson(keyID2,"Veri Yapıları","Bilgisayar Mühendisliği"));

*/

       // departmentKey = getIntent().getExtras().getString("departmentKey");
        textViewLessons.setText(departmentKey);

        listViewLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Lesson lesson = lessonsList.get(position);
                String lessonKey = lesson.getKey();


                Intent i = new Intent(LessonsActivity.this, LessonActivity.class);
                i.putExtra("lessonKey",lessonKey);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
     //   Toast.makeText(LessonsActivity.this,departmentKey,Toast.LENGTH_SHORT).show();

        Query query = databaseLesson.orderByChild("departmentName").equalTo(dpName);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lessonsList.clear();
                for (DataSnapshot lessonSnapshot : dataSnapshot.getChildren()) {
                    Lesson lesson = lessonSnapshot.getValue(Lesson.class);
                    lessonsList.add(lesson);
                }
                LessonsList adapter = new LessonsList(LessonsActivity.this, lessonsList);
                listViewLessons.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    class LessonsList extends ArrayAdapter<Lesson> {
        private Activity context;
        private List<Lesson> lessonsList;

        public LessonsList(Activity context, List<Lesson> lessonsList) {
            super(context, R.layout.activity_lessons, lessonsList);
            this.context = context;
            this.lessonsList = lessonsList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();



            View listViewItem = inflater.inflate(R.layout.activity_lessons, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewLessons);

            Lesson lesson = lessonsList.get(position);

            textViewName.setText(lesson.getLessonName());

            return listViewItem;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_settings){

            Intent i = new Intent(LessonsActivity.this, FacultiesActivity.class);

            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
