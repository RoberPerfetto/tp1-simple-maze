package ar.edu.ips.aus.seminario2.sampleproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private String name;
    private String txt_button;
    public static final String EXTRA_MESSAGE = "ar.edu.ips.aus.seminario2.sampleproject.MESSAGE";
    public static final String BUTTON_MESSAGE = "ar.edu.ips.aus.seminario2.sampleproject.MESSAGEBUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void sendData(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText)findViewById(R.id.nameText);
        Button but = findViewById(R.id.but_enter);

        name = editText.getText().toString();

        if(name != null && !name.isEmpty() && txt_button != null) {
            intent.putExtra(EXTRA_MESSAGE, name);
            intent.putExtra(BUTTON_MESSAGE, txt_button);
            startActivity(intent);
        }else {
            setTextView("You must complete all the data first!");
        }
    }

    public void getTextButton(View view) {
        Button but = (Button) view;
        txt_button = but.getText().toString();
        setTextView("You chose: " + txt_button);
    }

    public void setTextView(String string) {
        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(string);
    }
}
