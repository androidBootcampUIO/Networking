package uio.androidbootcamp.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsActivity extends AppCompatActivity {

    public static final String GITHUB_API = "https://api.github.com/";
    private ListView listView;
    private String githubUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            githubUser = extras.getString("githubUser");
        }

        listView = (ListView) findViewById(R.id.pagination_list);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GITHUB_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> call = client.reposForUser(githubUser);

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                List<GitHubRepo> repos = response.body();

                listView.setAdapter(new GitHubRepoAdapter(ResultsActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Toast.makeText(ResultsActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
