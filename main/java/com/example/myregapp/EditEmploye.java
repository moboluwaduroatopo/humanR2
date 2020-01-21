package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditEmploye extends AppCompatActivity {
    EditText username;
    Button upateuser,deluser;
    String id;
    RegDatabase regD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employe);

            username =(EditText) findViewById(R.id.deptNameU);
            upateuser=(Button) findViewById(R.id.upadteuBtn);
            deluser=(Button) findViewById(R.id.deleteubtn);
        TextView eview=(TextView) findViewById(R.id.emailview);
            Intent i=getIntent();
            Bundle extras=i.getExtras();
            id =extras.getString("uID");
            String name=extras.getString("uname");
            String em=extras.getString("email");
            username.setText(name);
            eview.setText("Email: "+em);
            regD= new RegDatabase(this);
            updateUser();
            deleteUser();
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
        public  void updateUser()
        {
            upateuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  d=username.getText().toString();
                    long idd = regD.updateUser(d,id);
                    if( idd > 0)
                    {
                        showMessage("Success","Update successfully");
                    }
                    else
                    {
                        showMessage("Warming","Failed!"+regD.error);
                    }
                }
            });
        }
        public void deleteUser()
        {
            deluser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long idd=regD.deleteUser(id);
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
                Intent u = new Intent(getApplicationContext(), Alluser.class);
                startActivity(u);
                return true;
            case R.id.dep_item:
                Intent dept = new Intent(getApplicationContext(), AddDepartment.class);
                startActivity(dept);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
