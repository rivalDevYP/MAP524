package map.finalproject.weddingplannermaterial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;
import java.util.Vector;

public class VenueFinderMap extends Fragment implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener
{

    private static final int MAX_LOCATIONS = 20;
    private static final String ACCESS_TOKEN = "pk.eyJ1Ijoicml2YWxkZXZ5cCIsImEiOiJjazRqYjg2MzUwamIzM2lxczg2OWl0bm44In0.nqM7hrdpKkqnEQ4Bk0bNVw";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Vector<VenueManager> venueManagers;
    private CameraPosition senecaCollegeNewnhamCampus = new CameraPosition.Builder().target(new LatLng(43.794413, -79.350118)).zoom(17).build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Mapbox.getInstance(getActivity().getApplicationContext(), ACCESS_TOKEN);
        View myView = inflater.inflate(R.layout.venue_finder_map, container, false);
        mapView = myView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return myView;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap)
    {
        VenueFinderMap.this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.TRAFFIC_NIGHT, new Style.OnStyleLoaded()
        {
            @Override
            public void onStyleLoaded(@NonNull Style style)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Map loaded", Toast.LENGTH_SHORT).show();
                mapboxMap.addOnMapClickListener(VenueFinderMap.this);
            }
        });
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point)
    {
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(senecaCollegeNewnhamCampus), 7000);

        return true;
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain)
    {

    }

    @Override
    public void onPermissionResult(boolean granted)
    {

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}