package map.finalproject.weddingplannerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
{
    ImageView mealPlannerButton;
    ImageView todoButton;
    ImageView venueFinderButton;
    ImageView budgetPlannerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mealPlannerButton = findViewById(R.id.meal_planner_button);
        todoButton = findViewById(R.id.todo_button);
        budgetPlannerButton = findViewById(R.id.budget_planner_button);
        venueFinderButton = findViewById(R.id.venue_finder_button);

        mealPlannerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setContentView(R.layout.meal_planner);
            }
        });

        budgetPlannerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setContentView(R.layout.budget_planner);
            }
        });
    }
}
