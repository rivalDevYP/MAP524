package map.finalproject.weddingplannermaterial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;

public class LandingPage extends Fragment
{
    private MaterialCardView mealPlannerCard, venuePlannerCard, budgetPlannerCard, todoListCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.landing_page, container, false);

        mealPlannerCard = view.findViewById(R.id.mealPlannerCardView);
        venuePlannerCard = view.findViewById(R.id.venueFinderCardView);

        setOnClickHandlers();

        return view;
    }

    private void setOnClickHandlers()
    {
        mealPlannerCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MealPlanner mealPlanner = new MealPlanner();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.replaceable, mealPlanner).commit();
            }
        });

        venuePlannerCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                VenueFinderMap venueFinderMap = new VenueFinderMap();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.replaceable, venueFinderMap).commit();
            }
        });
    }
}
