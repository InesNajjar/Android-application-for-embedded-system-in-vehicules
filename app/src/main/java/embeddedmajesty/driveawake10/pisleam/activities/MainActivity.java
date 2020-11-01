package embeddedmajesty.driveawake10.pisleam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import embeddedmajesty.driveawake10.pisleam.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
     public static String a="",b="",c="",d="";
    private FirebaseFirestore  db = FirebaseFirestore.getInstance();

    private CollectionReference usersRef = db.collection("User");

    TextView signup;
    EditText mail,pwd;
    Button btnfb,btninsta,btntwit,btnsubmit;
    CheckBox remember;
    boolean find=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       signup = findViewById(R.id.tv_login);
        mail = findViewById(R.id.editText_email);
        pwd = findViewById(R.id.editText_pass);
        btnsubmit =findViewById(R.id.btnLogin);
        remember = findViewById(R.id.checkBox);



        SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox =sharedPreferences.getString("remember","");

        if(checkbox.equals("true"))
        {
            Intent home = new Intent(MainActivity.this, DriveAwakeActivity.class);
            startActivity(home);
        }
        else if(checkbox.equals("false"))
        {
            Toast.makeText(MainActivity.this,"Please sign in",Toast.LENGTH_SHORT).show();

        }

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(MainActivity.this,"checked",Toast.LENGTH_SHORT).show();
                }
                if(!isChecked){
                    SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(MainActivity.this,"unchecked",Toast.LENGTH_SHORT).show();
                }

            }
        });



        btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email = mail.getText().toString();
               String password = pwd.getText().toString();

               if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                   //  login_user(email, password);
                          usersRef.get()
                           .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                   if (task.isSuccessful()) {

                                       for (QueryDocumentSnapshot doc : task.getResult()) {
                                           a = doc.getString("email");
                                           System.out.println("a="+a);

                                           b = doc.getString("password");
                                           System.out.println("b="+b);

                                           c=doc.getString("role");


                                           if (email.equals(a) && password.equals(b) && c.equals("client")) {
                                               d=doc.getString("roleRefId");
                                               System.out.println("ID"+d);
                                               Intent home = new Intent(MainActivity.this, DriveAwakeActivity.class);
                                               startActivity(home);

                                               find=true;
                                               break;
                                           }else{
                                               /*Toast.makeText(MainActivity.this, "invalid login", Toast.LENGTH_SHORT).show();
                                               System.out.println("are you here ?");*/
                                               ;
                                           }
                                       }
                                       if(find==false)
                                       {
                                          Toast.makeText(MainActivity.this, "invalid login", Toast.LENGTH_SHORT).show();
                                              // System.out.println("are you here ?");
                                       }





                                   }


                               }
                           });
               }
               else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                   mail.setError("Email is required");
                   pwd.setError("Password is required");
               }

           }
       });
    }


            public void onClickSingUp(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.driveawake.tn"));
                startActivity(intent);
            }


            public void onClickTwitter(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://twitter.com/AwakeDrive"));
                startActivity(intent);
            }

            public void onClickInstagram(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.instagram.com/drive_awake/"));
                startActivity(intent);
            }

            public void onClickFacebook(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.facebook.com/DriveAwakeTN/"));
                startActivity(intent);
            }
        }
