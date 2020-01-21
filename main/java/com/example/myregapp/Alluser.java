package com.example.myregapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Alluser extends AppCompatActivity implements AdapterView.OnItemClickListener {
    RegDatabase regD;
   TextView view;
    List<String> dptname;
    ListView t;
    String user_id="";
    String user_name="";
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);
        regD= new RegDatabase(this);
        dptname = new ArrayList<>();
        loadListData();
        t = (ListView) findViewById(R.id.listView);
        t.setOnItemClickListener(this);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.activity_list_row,R.id.dname,dptname);
        t.setAdapter(arrayAdapter);

//        Cursor res = regD.viewUsers();
//        String display = "";
//        if (res.getCount() > 0) {
//            while (res.moveToNext()) {
//                display += "ID " + res.getString(0);
//                display += "Name: " + res.getString(1);
//                display += "Email: " + res.getString(3);
//                display += "Password: " + res.getString(2) + " \n";
//            }
//            display += " \r";
//           TextView v=(TextView) findViewById(R.id.users);
//            v.setText(display);
////            showMessage("Users", display);
////
//        } else {
////            alert("No User yet.");
//        }
    }
    public List<String> loadListData() {
//        alert("here");
        Cursor res = regD.viewUsers();
        String displayD = "";

        if(res.getCount() >  0)
        {
            while (res.moveToNext())
            {

                dptname.add("ID: "+res.getString(0)+ " User Name: "+res.getString(1));

            }

        }

        return dptname;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    public void alert(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.logout_item:
                // do your code
                Intent l = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(l);
            case R.id.profile_item:
                if(getIntent().hasExtra("com.example.myregapp.profile")){
                    String str = getIntent().getExtras().getString("com.example.myregapp.profile");
        alert(str);
//
                }else
                {
                    alert("no intent");
                }

            case R.id.user_item:
                return true;
//
            case R.id.dep_item:
                Intent dept = new Intent(getApplicationContext(), AddDepartment.class);
                startActivity(dept);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getApplicationContext(), EditEmploye.class);
        String deptN=parent.getItemAtPosition(position).toString();
//        String deptN="ID: 15 Department Name: Computer";
        String[] sep=deptN.split("\\:");
        String dptname=sep[1];
        String[] dpt=dptname.split("\\s+");
        String d=dpt[1];

        Cursor dn = regD.checkUserid(d);

        if (dn.getCount()>0)
        {
            while (dn.moveToNext())
            {
                user_name = dn.getString(1);
                user_id= dn.getString(0);
                email= dn.getString(3);
            }
            Bundle extras=new Bundle();
            extras.putString("uID",user_id);
            extras.putString("uname",user_name);
            extras.putString("email",email);
            intent.putExtras(extras);
            startActivity(intent);
        }else
        {
            alert("User Name not Exit");
        }
//      alert(d);
    }
}
