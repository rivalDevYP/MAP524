package example.com;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView redText;
    private TextView greenText;
    private TextView triesText;
    private Button loginButton;
    private int illegalLoginCounter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onClickLoginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        redText = findViewById(R.id.redText);
        greenText = findViewById(R.id.greenText);
        triesText = findViewById(R.id.triesText);
    }

    private View.OnClickListener onClickLoginButton = new View.OnClickListener()
    {
        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v)
        {
            LoginManager loginManager = new LoginManager(usernameEditText.getText().toString(), passwordEditText.getText().toString());

            if (loginManager.authenticated())
            {
                redText.setVisibility(View.INVISIBLE);

                triesText.setVisibility(View.INVISIBLE);

                greenText.setVisibility(View.VISIBLE);

                illegalLoginCounter = 3;
            }
            else if (illegalLoginCounter == 0)
            {
                redText.setText(getString(R.string.errorMessageInvalidCredentials));

                triesText.setText(getString(R.string.outOfChances));

                loginButton.setBackgroundColor(Color.parseColor("#888888"));

                loginButton.setEnabled(false);
            }
            else
            {
                greenText.setVisibility(View.INVISIBLE);

                redText.setText(getString(R.string.errorMessageInvalidCredentials));

                triesText.setText(Integer.toString(illegalLoginCounter) + " " + getString(R.string.counterMessage));

                redText.setVisibility(View.VISIBLE);

                triesText.setVisibility(View.VISIBLE);

                illegalLoginCounter--;
            }
        }
    };
}
