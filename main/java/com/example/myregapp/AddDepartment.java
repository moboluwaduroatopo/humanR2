package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddDepartment extends AppCompatActivity implements OnItemClickListener{
    EditText deptName;
    Button addDept, viewDept;
    RegDatabase hrD;
   List<String> dptname;
    ListView t;
    String dept_id="";
    String dept_name="";
//String ani[]={"loed","cupter","asbdn"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        dptname = new ArrayList<>();
        deptName = (EditText) findViewById(R.id.deptNameText);
        addDept = (Button) findViewById(R.id.addButton);
//            viewDept = (Button) findViewById(R.id.viewButton);
        hrD = new RegDatabase(this);
        addDepartment();
//        viewDepartment();
        loadListData();
        t = (ListView) findViewById(R.id.listView);
        t.setOnItemClickListener(this);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.activity_list_row,R.id.dname,dptname);
        t.setAdapter(arrayAdapter);

    }
    public List<String> loadListData() {
//        alert("here");
        Cursor res = hrD.viewDepartment();
        String displayD = "";

        if(res.getCount() >  0)
        {
            while (res.moveToNext())
            {

                dptname.add("ID: "+res.getString(0)+ " Department Name: "+res.getString(1));

            }

        }

        return dptname;
    }

        public void alert(String msg)
        {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
        public void showMessage(String title, String msg)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.show();
        }
        public void addDepartment()
        {
            addDept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = deptName.getText().toString();
                    Cursor ce = hrD.checkDeptname(s);
                    if( s.equals("")){
//                    alert("Please complete the required field!");
                        showMessage("Warming","Please complete the required field!");
                    }else{
                        if (ce.getCount()>0)
                        {
                            alert("Department already taken");
                        }else
                        {
                            long idd = hrD.addDepartment(s);
                            if( idd != 0)
                            {
                                alert("Department added successfully + insertId : " + idd);
                                deptName.setText("");
                            }
                            else
                            {
                                alert("Failed :"+hrD.error);
                            }
                        }
                    }


                }
            });
        }


        public void viewDepartment()
        {

            viewDept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
//                startActivity(intent);
                    Cursor res = hrD.viewDepartment();
                    String display = "";
                    if(res.getCount() >  0)
                    {
                        while (res.moveToNext())
                        {
                            display += "ID "+res.getString(0);
                            display += " Department Name: "+res.getString(1)+" \n";
                        }
                        display+=" \r";
                        showMessage("Departments",display);

                        intent.putExtra("com.example.humanresource.department",display);
                        startActivity(intent);
                    }
                    else
                    {
                        alert("No Department yet.");
                    }

                }
            });
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), EditDepartment.class);
        String deptN=parent.getItemAtPosition(position).toString();
//        String deptN="ID: 15 Department Name: Computer";
        String[] sep=deptN.split("\\:");
        String dptname=sep[1];
        String[] dpt=dptname.split("\\s+");
        String d=dpt[1];

//        String dept_id="";12
        Cursor dn = hrD.checkDeptid(d);

        if (dn.getCount()>0)
        {
            while (dn.moveToNext())
            {
                dept_name = dn.getString(1);
                dept_id= dn.getString(0);
            }
//            dept_id+=" \r";
            Bundle extras=new Bundle();
            extras.putString("ID",dept_id);
            extras.putString("name",dept_name);
            intent.putExtras(extras);
//            intent.putExtra("com.example.myregapp.editdepartment",dept_id);

            startActivity(intent);
//           alert(dept_id);
        }else
        {
            alert("Department Name not Exit");
        }
//      alert(d);
    }
}
