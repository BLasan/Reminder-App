package com.example.benura.myapplication;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javax.mail.Session;


import static com.example.benura.myapplication.App.CHANNEL_ID;


public class Login extends MainActivity {
    public String str,str1;
    private NotificationManagerCompat notificationManager;
    private String mailhost = "smtp.gmail.com";
    private String string;
    private String password;
    private Session session;
    public ImageButton img1,img2;
    public TextView textview1,textview2,textview3;
    public EditText edittext1,edittext2,edittext3;
    public Button submit;
    public int hour,minute;
    public int year,day,month;
    public boolean isRegister=false;
   private ProgressDialog progressBar1;
   public  final Calendar myCalender = Calendar.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        notificationManager=NotificationManagerCompat.from(this);
        FirebaseDatabase database1=FirebaseDatabase.getInstance();
        edittext1=(EditText)findViewById(R.id.eventEdit);
        edittext2=(EditText)findViewById(R.id.dateText);
        edittext3=(EditText)findViewById(R.id.timeText);
        textview1=(TextView)findViewById(R.id.EventView);
        img1=(ImageButton)findViewById(R.id.dropDown2);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    hour = myCalender.get(Calendar.HOUR_OF_DAY);
                    minute = myCalender.get(Calendar.MINUTE);


                    TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (view.isShown()) {

                                hour=hourOfDay;
                                minute=minute;
                                edittext3.setText(new StringBuilder().append(hour)

                                        .append(":").append(minute).append(":").append("00"));

                            }
                            }

                    };
                    TimePickerDialog timePickerDialog = new TimePickerDialog(Login.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                    timePickerDialog.setTitle("Choose Time");
                    timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    timePickerDialog.show();

            }

        });

        img2= (ImageButton) findViewById(R.id.dropDown1);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=myCalender.get(Calendar.YEAR);
                month=myCalender.get(Calendar.MONTH);
                day=myCalender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Login.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1) {
                                year = year1;
                                month = month1;
                                day = day1;
                                edittext2.setText(new StringBuilder().append(year)

                                        .append("-").append(month+1).append("-").append(day).append(" "));


                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });



        submit=(Button)findViewById(R.id.submit);

    }

    public void sendChannel(View view){


        Bundle bundle = getIntent().getExtras();
        String string5 = bundle.getString("d");
        String strings = bundle.getString("e");
                  try {


                      string = edittext1.getText().toString();
                      String string1 = edittext2.getText().toString();
                      String string2 = edittext3.getText().toString();
                      FirebaseDatabase database = FirebaseDatabase.getInstance();
                      DatabaseReference myref = database.getReference();
                      myref.child(string5).child("event").setValue(string);
                      myref.child(string5).child("date").setValue(string1);
                      myref.child(string5).child("time").setValue(string2);

                  }catch (Exception e){
                      Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                  }

        String toyBornTime =edittext2.getText().toString()+" "+edittext3.getText().toString();

        edittext1.setText(null);
        edittext2.setText(null);
        edittext3.setText(null);

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {

            Date oldDate = dateFormat.parse(toyBornTime);
            Date currentDate = new Date();

            long diff = oldDate.getTime()-currentDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours2 = minutes / 60;
            long days = hours2 / 24;


            String mins3=Long.toString(minutes);

            if (currentDate.before(oldDate)) {

                new CountDownTimer(diff, 1000) {

                    public void onTick(long millisUntilFinished) {
                      //  edittext1.setText("seconds remaining: " + millisUntilFinished / 1000);

                    }

                    public void onFinish() {
                        // String y=Long.toString(diff);
                        //   edittext1.setText(y);
                        show();
                    }
                }.start();

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours2 + " days: " + days);

            }
            else
                Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }

             mAuth.createUserWithEmailAndPassword(strings, string5)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                          //  Log.d(TAG, "createUserWithEmail:success");

                            Toast.makeText(Login.this, "Authentication Success.", Toast.LENGTH_SHORT).show();

                            isRegister= true;

                        } else {

                          //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "User Already exist.", Toast.LENGTH_SHORT).show();
                            isRegister= false;

                        }

                    }});

          sendRegistrationLink();

         finish();

    }


   public void show(){
       Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID).setSmallIcon(R.drawable.ic_query).setContentTitle("Reminder")
                .setContentText(string).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_MESSAGE).setSound(alarmSound).setStyle(new NotificationCompat.BigTextStyle().bigText(string)).build();

        notificationManager.notify(1,notification);


    }

    private void sendRegistrationLink() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(Login.this, new OnCompleteListener() {

                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,"Verification email sent to " + user.getEmail(),Toast.LENGTH_SHORT).show();
                            //validateUsername();
                        } else {
                          //  Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Login.this,"Failed to send verification email.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}


