package ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopping.R;
import util.SharedPrefManager;
import ui.LoginActivity;

public class ProfileFragment extends Fragment {

    SharedPrefManager pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pref = new SharedPrefManager(getContext());

        TextView tvInfo = view.findViewById(R.id.tvInfo);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        String info = "Email: " + pref.getEmail() + "\nRole: " + pref.getRole();
        tvInfo.setText(info);

        btnLogout.setOnClickListener(v -> {
            pref.logout();
            Intent i = new Intent(getContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            getActivity().finish();
        });

        return view;
    }
}