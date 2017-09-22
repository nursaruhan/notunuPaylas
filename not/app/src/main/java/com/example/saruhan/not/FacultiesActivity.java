package com.example.saruhan.not;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FacultiesActivity extends AppCompatActivity {

    ListView listViewFaculties;
    List<Faculty> facultyList;
    DatabaseReference databaseFaculty, databaseDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);

        listViewFaculties = (ListView) findViewById(R.id.facultiesList);
        facultyList = new ArrayList<>();
        databaseFaculty = FirebaseDatabase.getInstance().getReference("Faculty");
        databaseDepartment = FirebaseDatabase.getInstance().getReference();

        /* FAKÜLTE, BÖLÜMLER VE DERSLER TEK TEK EKLENDİ
        DatabaseReference pushRef = databaseFaculty.push();
        String key_ID = pushRef.getKey();
        pushRef.setValue(new Faculty("Ziraat Fakültesi",key_ID));

        DatabaseReference pushRef3 = databaseDepartment.child("Department").push();
        String key_ID3 = pushRef3.getKey();
        pushRef3.setValue(new Department(key_ID3,"İnşaat Mühendisliği","-KuPHWheHJUE-MvVCutR"));

        DatabaseReference pushRef3 = databaseDepartment.child("Lessons").push();
        String key_ID3 = pushRef3.getKey();
        pushRef3.setValue(new Lesson(key_ID3,"Bilgisayar Mimarisi","Bilgisayar Mühendisliği"));*/

        listViewFaculties.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Faculty faculty = facultyList.get(position);
                String key = faculty.getKey();

                Intent i = new Intent(FacultiesActivity.this, DepartmentActivity.class);
                i.putExtra("key",key);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseFaculty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                facultyList.clear();
                for (DataSnapshot facultySnapshot : dataSnapshot.getChildren()) {
                    Faculty faculty = facultySnapshot.getValue(Faculty.class);
                    facultyList.add(faculty);
                }
                FacultyList adapter = new FacultyList(FacultiesActivity.this, facultyList);
                listViewFaculties.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    class FacultyList extends ArrayAdapter<Faculty> {
        private Activity context;
        private List<Faculty> facultyList;

        public FacultyList(Activity context, List<Faculty> facultyList) {
            super(context, R.layout.activity_faculties, facultyList);
            this.context = context;
            this.facultyList = facultyList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.activity_faculties, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewFaculty);

            Faculty faculty = facultyList.get(position);

            textViewName.setText(faculty.getFacultyName());

            return listViewItem;
        }
    }
}


