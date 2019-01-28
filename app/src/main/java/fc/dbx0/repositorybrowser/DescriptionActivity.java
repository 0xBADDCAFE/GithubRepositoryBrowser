package fc.dbx0.repositorybrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {
    public static final String ARG_REPO_NAME = "repo-name";
    public static final String ARG_REPO_DESCRIPTION = "repo-description";
    public static final String ARG_REPO_STAR = "repo-star";
    public static final String ARG_REPO_FORK = "repo-fork";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent i = getIntent();

        String name = i.getStringExtra(ARG_REPO_NAME);
        String description = i.getStringExtra(ARG_REPO_DESCRIPTION);
        String star = i.getStringExtra(ARG_REPO_STAR);
        String fork = i.getStringExtra(ARG_REPO_FORK);

        TextView repoNameTextView = findViewById(R.id.repo_name);
        TextView descriptionTextView = findViewById(R.id.description);
        TextView starTextView = findViewById(R.id.star);
        TextView forkTextView = findViewById(R.id.fork);

        repoNameTextView.setText(name);
        descriptionTextView.setText(description);
        starTextView.setText(star);
        forkTextView.setText(fork);

    }
}
