package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button reg,cli;
EditText fname,email,pwd;
Spinner spinner;
    RegDatabase regD;
    List<String> dptList;
    String dept_id="";
//    String[] users={"computer","python"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView login = (TextView)findViewById(R.id.lnkLogin);
        dptList  = new ArrayList<>();

        reg=(Button) findViewById(R.id.btnRegister);
        email=(EditText) findViewById(R.id.email);
        pwd=(EditText) findViewById(R.id.pwd);
        fname=(EditText) findViewById(R.id.fName);
        cli=(Button) findViewById(R.id.click);
        spinner=(Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        regD= new RegDatabase(this);
        addUser();
       loadSpinnerData();

        clickDept();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dptList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public List<String> loadSpinnerData() {
//        alert("here");
        Cursor res = regD.viewDepartment();
        String display = "";
        if(res.getCount() >  0)
        {
            while (res.moveToNext())
            {
                dptList.add(res.getString(1));
//                display += "ID "+res.getString(0);
//                display += res.getString(1);
            }
//            display+=" \r";
//            dptList.add(display);
//            showMessage("Departments",display);

        }
        return dptList;
    }

    public void alert(String msg)
    {
        Toast.makeText(Main2Activity.this, msg, Toast.LENGTH_LONG).show();
    }
    public void showMessage(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
    public  void clickDept(){
        cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDepartment.class);
               startActivity(intent);
            }
        });
    }
 public  void  addUser(){
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f = fname.getText().toString();
                String  e=email.getText().toString();
                String p=pwd.getText().toString();
                if(f.equals("") || e.equals("") || p.equals("")){
                    showMessage("Warming","Please complete the required field!");
                }
                else
                    {
                        Cursor ce = regD.checkEmail(e);
                        if (ce.getCount()>0)
                        {
                            alert("Email already taken");
                        }else
                        {
                            long idd = regD.addUser(f,p,e,dept_id);
                            if( idd != 0)
                            {
                                alert("User added successfully + insertId : " + idd);
                                fname.setText("");
                                email.setText("");
                                pwd.setText("");
                            }
                            else
                            {
                                alert("Failed :"+regD.error);
                            }
                        }

//                    alert("reg" +f);
                }
//               alert("reg" +dept_id);
            }
        });
 }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String deptN=parent.getItemAtPosition(position).toString();
        Cursor dn = regD.checkDeptname(deptN);
        if (dn.getCount()>0)
        {
            while (dn.moveToNext())
            {
               dept_id += dn.getString(0);
            }
            dept_id+=" \r";
        }else
        {
            alert("Department Name not Exit");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO Auto-generate mothod stub
    }
}
