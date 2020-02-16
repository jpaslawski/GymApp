package adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.gymapp.R;

import java.util.ArrayList;
import java.util.List;

import activities.DeleteUserAccountActivity;
import activities.RegisterActivity;
import activities.UpdateUserAccountActivity;
import activities.UserListActivity;
import models.User;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;
    private Context context;
    private String loggedUserEmail;

    public UsersRecyclerAdapter(List<User> listUsers, Context context) {
        this.listUsers = listUsers;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        loggedUserEmail = holder.textViewEmail.getText().toString();
        holder.textViewUsername.setText(listUsers.get(position).getUsername());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
        String weight = listUsers.get(position).getWeight().toString();
        holder.textViewWeight.setText(weight);
        String height = listUsers.get(position).getHeight().toString();
        holder.textViewHeight.setText(height);
        String age = listUsers.get(position).getAge().toString();
        holder.textViewAge.setText(age);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, R.layout.account_options, holder.list, holder.hidingItemIndex);
        adapter.setDropDownViewResource(R.layout.dropdown_account_options);

        holder.spinner.setAdapter(adapter);

        // Actions to perform when an item is selected
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    // Empty item - Spinner always have one selection active, so that's a way around :)
                    case 0:
                        break;

                    // OnClick Update
                    case 1:
                        Intent intentUpdate = new Intent(context, UpdateUserAccountActivity.class);
                        intentUpdate.putExtra("EMAIL", holder.textViewEmail.getText().toString());
                        intentUpdate.putExtra("USER", loggedUserEmail);
                        context.startActivity(intentUpdate);
                        break;

                    // OnClick Delete
                    case 2:
                        Intent intentDelete = new Intent(context, DeleteUserAccountActivity.class);
                        intentDelete.putExtra("EMAIL", holder.textViewEmail.getText().toString());
                        intentDelete.putExtra("USER", loggedUserEmail);
                        context.startActivity(intentDelete);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),"" + listUsers.size());
        return listUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewUsername;
        public TextView textViewEmail;
        public TextView textViewPassword;
        public TextView textViewWeight;
        public TextView textViewHeight;
        public TextView textViewAge;
        public Spinner spinner;
        public String list[];
        public int hidingItemIndex = 0;

        public UserViewHolder(View view) {
            super(view);
            textViewUsername = view.findViewById(R.id.textViewUsername);
            textViewEmail = view.findViewById(R.id.textViewEmail);
            textViewPassword = view.findViewById(R.id.textViewPassword);
            textViewWeight = view.findViewById(R.id.textViewWeight);
            textViewHeight = view.findViewById(R.id.textViewHeight);
            textViewAge = view.findViewById(R.id.textViewAge);
            spinner = view.findViewById(R.id.spinnerAccount);

            list = new String[3];
            list[0] = ("");
            list[1] = ("Update");
            list[2] = ("Delete");
        }
    }
}
