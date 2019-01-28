package fc.dbx0.repositorybrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import fc.dbx0.repositorybrowser.api.GithubService;
import fc.dbx0.repositorybrowser.api.entity.Repo;
import fc.dbx0.repositorybrowser.dummy.DummyContent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fc.dbx0.repositorybrowser.DescriptionActivity.ARG_REPO_DESCRIPTON;
import static fc.dbx0.repositorybrowser.DescriptionActivity.ARG_REPO_FORK;
import static fc.dbx0.repositorybrowser.DescriptionActivity.ARG_REPO_NAME;
import static fc.dbx0.repositorybrowser.DescriptionActivity.ARG_REPO_STAR;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    EditText usernameEditText;
    RecyclerView repositoryList;

    RepositoryRecyclerViewAdapter adapter;

    GithubService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.submit);
        usernameEditText = findViewById(R.id.username);
        repositoryList = findViewById(R.id.list_repository);

        adapter = new RepositoryRecyclerViewAdapter(this);
        repositoryList.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(GithubService.class);
    }

    void onSubmitClick() {
        String username = usernameEditText.getText().toString();

        Call<List<Repo>> apiCall = service.listRepos(username);
        apiCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repoList = response.body();
                adapter.setData(repoList);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void onRepoListItemClick(Repo repo) {
        // TODO: Description show screen
        Intent i = new Intent(this, DescriptionActivity.class);

        i.putExtra(ARG_REPO_NAME, repo.name);
        i.putExtra(ARG_REPO_DESCRIPTON, repo.description);
        i.putExtra(ARG_REPO_STAR, repo.stargazers_count);
        i.putExtra(ARG_REPO_FORK, repo.forks_count);

        startActivity(i);
    }



    static class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<Repo> mValues;
        private MainActivity activity;

        public RepositoryRecyclerViewAdapter(MainActivity activity) {
            this.activity = activity;
        }

        public void setData(List<Repo> items) {
            mValues = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_repository, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Repo repoItem = mValues.get(position);

            holder.mIdView.setText(String.valueOf(position));
            holder.mContentView.setText(repoItem.name);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
activity.onRepoListItemClick(repoItem);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues == null ? 0 : mValues.size();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
