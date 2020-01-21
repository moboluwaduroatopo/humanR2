package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RegDatabase regD;
    EditText email,pwd;
//    Button view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText) findViewById(R.id.email);
        pwd=(EditText) findViewById(R.id.Pwd);
//        view=(Button) findViewById(R.id.view);
        TextView register = (TextView)findViewById(R.id.lnkRegister);
//        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });

        regD= new RegDatabase(this);
        logIn();
//        getuser();
    }
    public void alert(String msg)
    {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }
    public void showMessage(String title, String msg)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();
    }
    public  void  logIn()
    {
        Button profile = (Button) findViewById(R.id.btnLogin);
//        register.setMovementMethod(LinkMovementMethod.getInstance());
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  e=email.getText().toString();
                String p=pwd.getText().toString();
                if( e.equals("") || p.equals("")){
//                    alert("Please complete the required field!");
                    showMessage("Warming","Please complete the required field!");
                }
                else{
//                    alert("Yes");
                    Cursor idd = regD.login(e,p);
                    String display = "";
                    String id="";
                    String emails="";
                    String name="";
                    String password="";
                    if(idd.getCount() >  0)
                    {
                        while (idd.moveToNext()) {
//                            display += "ID " + idd.getString(0);
//                            display += "Name: " + idd.getString(1);
//                            display += "Email: " + idd.getString(3);
//                            display += "Password: " + idd.getString(2) + " \n";
                            id = idd.getString(0);
                            name =  idd.getString(1);
                            emails = idd.getString(3);
                            password = idd.getString(2);
                        }
//                        display += " \r";
                      Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                      Bundle extras=new Bundle();
                      extras.putString("ID",id);
                      extras.putString("name",name);
                        extras.putString("emial",emails);
                        extras.putString("pass",password);
                      intent.putExtras(extras);
                      startActivity(intent);
                        alert("User login successfully ");
                    }
                    else
                    {
                        alert("Invalid Email or password :"+regD.error);
                    }
                }
//                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                startActivity(intent);
            }
        });
    }
//    public  void getuser()
//    {
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor res = regD.viewUsers();
//                String display = "";
//                if (res.getCount() > 0) {
//                    while (res.moveToNext()) {
//                        display += "ID " + res.getString(0);
//                        display += "Name: " + res.getString(1) + " \n";
//                        display += "Email: " + res.getString(3) + " \n";
//                        display += "Password: " + res.getString(2) + " \n";
//                    }
//                    display += " \r";
//                    showMessage("Users", display);
////                    intent.putExtra("com.example.humanresource.department",display);
////                    startActivity(intent);
//                } else {
//                    alert("No User yet.");
//                }
//            }
//            });
//    }
}
