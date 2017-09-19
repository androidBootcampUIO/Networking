package uio.androidbootcamp.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EMPTY_USER_NAME = "Empty user name :(";
    private EditText githubUser;
    private Intent resultsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getReposButton = (Button) findViewById(R.id.get_repos_btn);
        githubUser = (EditText) findViewById(R.id.github_user);
        resultsActivity = new Intent(MainActivity.this, ResultsActivity.class);

        getReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasValidText()) {
                    resultsActivity.putExtra("githubUser", githubUser.getText().toString());
                    startActivity(resultsActivity);
                } else {
                    Toast.makeText(MainActivity.this, EMPTY_USER_NAME, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean hasValidText() {
        return !githubUser.getText().toString().equals("");
    }
}
