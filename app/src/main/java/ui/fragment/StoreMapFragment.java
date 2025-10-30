package ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopping.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_map_fragment, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng dnCenter = new LatLng(16.047079, 108.206230);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dnCenter, 13f));

        googleMap.addMarker(new MarkerOptions().position(dnCenter).title("Đà Nẵng Center"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(16.061532, 108.223008)).title("PhoneHub Lê Duẩn"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(16.045678, 108.210542)).title("Mobile City Hải Châu"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(16.071213, 108.222613)).title("Hoàng Hà Mobile"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(16.047380, 108.218260)).title("CellphoneS Trần Phú"));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(16.074111, 108.224421)).title("Thế Giới Di Động – Nguyễn Văn Linh"));
    }
}