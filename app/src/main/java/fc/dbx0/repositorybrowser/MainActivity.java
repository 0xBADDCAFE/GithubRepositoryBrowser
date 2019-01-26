package fc.dbx0.repositorybrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import fc.dbx0.repositorybrowser.dummy.DummyContent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText;
    RecyclerView repositoryList;

    RepositoryRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = findViewById(R.id.submit);
        usernameEditText = findViewById(R.id.username);
        repositoryList = findViewById(R.id.list_repository);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });
    }

    void onSubmitClick() {
        repositoryList.setAdapter(new RepositoryRecyclerViewAdapter(DummyContent.ITEMS));
    }

    static class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final List<DummyContent.DummyItem> mValues;

        public RepositoryRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
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
            DummyContent.DummyItem dummyItem = mValues.get(position);

            holder.mIdView.setText(dummyItem.id);
            holder.mContentView.setText(dummyItem.content);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
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
