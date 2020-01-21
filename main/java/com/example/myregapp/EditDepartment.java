package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditDepartment extends AppCompatActivity implements AdapterView.OnItemClickListener {
EditText deptname,list;
Button upatedpt,deldpt;
String id;
    List<String> dptname;
    RegDatabase regD;
    ListView t;
    String user_id="";
    String user_name="";
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_department);
        deptname =(EditText) findViewById(R.id.deptNameT);
        upatedpt=(Button) findViewById(R.id.upadteBtn);
        deldpt=(Button) findViewById(R.id.deletebtn);
        Intent i=getIntent();
        Bundle extras=i.getExtras();
        id =extras.getString("ID");
        String name=extras.getString("name");
        deptname.setText(name);
        regD= new RegDatabase(this);
        dptname = new ArrayList<>();
        loadListData();
       TextView list=(TextView) findViewById(R.id.list);
        t = (ListView) findViewById(R.id.listView);


        t.setOnItemClickListener(this);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.activity_list_row,R.id.dname,dptname);
        t.setAdapter(arrayAdapter);
        if (t.equals("")){
            alert("here");
        }else{
            list.setText("Click List To Edit or Delete");
        }
updateDepartment();
deleteDepartment();

    }
    public List<String> loadListData() {
//        alert("here");
        Cursor res = regD.viewemployeBydept(id);
        String displayD = "";

        if(res.getCount() >  0)
        {
            while (res.moveToNext())
            {

                dptname.add("ID: "+res.getString(0)+ " User Name: "+res.getString(1));

            }

        }
        else
        {

            alert("No Employe ");
        }
        return dptname;

    }

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void showMessage(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
    public  void updateDepartment()
    {
       upatedpt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String  d=deptname.getText().toString();
               long idd = regD.updateDepartment(d,id);
               if( idd > 0)
               {
                   showMessage("Success","Update successfully");
               }
               else
               {
                   showMessage("Warming","Failed!"+regD.error);
               }
              alert(d+id);
           }
       });
    }
    public void deleteDepartment()
    {
     deldpt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             long idd=regD.deleteDepartment(id);
             if( idd > 0)
             {
                 showMessage("Success","Delete successfully");
             }
             else
             {
                 showMessage("Warming","Failed!"+regD.error);
             }
//             alert("delete");
         }
     });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
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
                Intent u = new Intent(getApplicationContext(), Alluser.class);
                startActivity(u);
//
            case R.id.dep_item:
                Intent dept = new Intent(getApplicationContext(), AddDepartment.class);
                startActivity(dept);

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
    }
}