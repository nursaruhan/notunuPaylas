package com.example.saruhan.not;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends AppCompatActivity {

    private TextView department;
    ListView listViewDepartments;
    List<Department> departmentList;
    DatabaseReference databaseDepartment;
    String facultykey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        listViewDepartments = (ListView) findViewById(R.id.departmentsList) ;
        departmentList = new ArrayList<>();
        databaseDepartment = FirebaseDatabase.getInstance().getReference("Department");

        department = (TextView) findViewById(R.id.textViewDepartment);
        facultykey = getIntent().getExtras().getString("key");
        department.setText(facultykey);

        listViewDepartments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Department department= departmentList.get(position);
                String dpName = department.getDepartmentName();

                Intent i = new Intent(DepartmentActivity.this, LessonsActivity.class);
                i.putExtra("dpName",dpName);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Seçilen fakültelerin bölümlerini getiriyor.
        Query query = databaseDepartment.orderByChild("facultyKey").equalTo(facultykey);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                departmentList.clear();
                for (DataSnapshot departmentSnapshot : dataSnapshot.getChildren()) {
                    Department department = departmentSnapshot.getValue(Department.class);
                    departmentList.add(department);
                }
                DepartmentList adapter = new DepartmentList(DepartmentActivity.this, departmentList);
                listViewDepartments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class DepartmentList extends ArrayAdapter<Department> {
        private Activity context;
        private List<Department> departmentList;

        public DepartmentList(Activity context, List<Department> departmentList) {
            super(context, R.layout.activity_department, departmentList);
            this.context = context;
            this.departmentList = departmentList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.activity_department, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewDepartment);

            Department department = departmentList.get(position);

            textViewName.setText(department.getDepartmentName());
            return listViewItem;
        }
    }
}