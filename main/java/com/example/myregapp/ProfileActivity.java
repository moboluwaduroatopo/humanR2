package com.example.myregapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    TextView t,e,n;
    String id;
    RegDatabase regD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=getIntent();
        Bundle extras=i.getExtras();
        id =extras.getString("ID");
        String name=extras.getString("name");
        String pass =extras.getString("pass");
        String email =extras.getString("emial");
             t = (TextView) findViewById(R.id.profile);
         e = (TextView) findViewById(R.id.pemail);
         n=(EditText) findViewById(R.id.fname);
            t.setText("Employe id: " +id);
            e.setText("Email: " +email);
            n.setText(name);
        regD= new RegDatabase(this);
            updateProfile();
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//    Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.logout_item:
                // do your code
                    Intent l = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(l);
            case R.id.profile_item:
               return true;
            case R.id.user_item:
                Intent u = new Intent(getApplicationContext(), Alluser.class);
                startActivity(u);
                return true;
            case R.id.dep_item:
                Intent dept = new Intent(getApplicationContext(), AddDepartment.class);
                startActivity(dept);
                return true;
                // do your code
//                return true;
//            case R.id.share_item:
//                // do your code
//                return true;
//            case R.id.bookmark_item:
//                // do your code
//                return true;
            default:
                return super.onOptionsItemSelected(item);
       }
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
    public void updateProfile()
    {
        Button updatep=(Button) findViewById(R.id.btnupdatep);
        updatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String name= n.getText().toString();
//              alert(name);
                long idd = regD.updateProfile(name,id);
                if( idd > 0)
                {
                    alert("Update successfully + updateId : " + idd);
                }
                else
                {
                    showMessage("Warming","Failed!"+regD.error);
//                    alert("Failed :"+regD.error);
                }

            }
        });
    }
}
