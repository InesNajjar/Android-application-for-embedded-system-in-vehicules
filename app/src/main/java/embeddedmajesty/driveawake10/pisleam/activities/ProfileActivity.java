package embeddedmajesty.driveawake10.pisleam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import embeddedmajesty.driveawake10.pisleam.R;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseFirestore  db = FirebaseFirestore.getInstance();
    private CollectionReference clientRef = db.collection("Client");
   public static String n="",f="",a="",p="",l="",m="",c="";
    TextView name,familyname,age,phone,loc,postalCode;
    public String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        familyname = findViewById(R.id.familyname);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phonenumber);
        loc = findViewById(R.id.addressclientvalue);
        postalCode = findViewById(R.id.postalcode);
        ID =MainActivity.d;

        clientRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        n = doc.getString("lastName");
                        f = doc.getString("firstName");
                        a = doc.getString("age");
                        p=doc.getString("telephone");
                        l=doc.getString("city");
                        c=doc.getString("country");
                        m=doc.getString("id");

                        if(ID.equals(m)){
                            name.setText(n);
                            familyname.setText(f);
                            age.setText(a);
                            phone.setText(p);
                            loc.setText(l);
                           postalCode.setText(c.toString());
                        }

                    }
                }

            }
        });
}
}
