package map.finalproject.weddingplannermaterial;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

public class MealPlanner extends Fragment
{
    private Context myContext;
    private MaterialCardView starterPanel, entreePanel, dessertPanel, winePanel;
    private MaterialTextView starterTextView, entreeTextView, dessertTextView, wineTextView;
    private ArrayAdapter<String> starterChoiceCollection, entreeChoiceCollection, dessertChoiceCollection, wineChoiceCollection;
    private MealPlannerDatabaseManager mealPlannerDatabaseManager;
    public final String STARTER = "starter", ENTREE = "entree", DESSERT = "dessert", WINE = "wine";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View mealPlannerView = inflater.inflate(R.layout.meal_planner, container, false);

        myContext = getActivity();

        mealPlannerDatabaseManager = MealPlannerDatabaseManager.getInstance(myContext);

        starterPanel = mealPlannerView.findViewById(R.id.starterSelectionPanel);
        entreePanel = mealPlannerView.findViewById(R.id.entreeSelectionPanel);
        dessertPanel = mealPlannerView.findViewById(R.id.dessertSelectionPanel);
        winePanel = mealPlannerView.findViewById(R.id.wineSelectionPanel);

        starterTextView = mealPlannerView.findViewById(R.id.starterSelectionTextView);
        entreeTextView = mealPlannerView.findViewById(R.id.entreeSelectionTextView);
        dessertTextView = mealPlannerView.findViewById(R.id.dessertSelectionTextView);
        wineTextView = mealPlannerView.findViewById(R.id.wineSelectionTextView);

        refreshViews();
        setCollections();
        setOnClickListeners();

        return mealPlannerView;
    }

    private void refreshViews()
    {
        SQLiteDatabase database = mealPlannerDatabaseManager.getReadableDatabase();
        int starterIndex = 0, entreeIndex = 0, dessertIndex = 0, wineIndex = 0;

        Cursor starterCursor = database.rawQuery("select mealChoice from mealChoices where mealType = 'starter'", null);
        while (starterCursor.moveToNext())
        {
            starterTextView.setText(starterCursor.getString(starterIndex++));
        }
        starterCursor.close();

        Cursor entreeCursor = database.rawQuery("select mealChoice from mealChoices where mealType = 'entree'", null);
        while (entreeCursor.moveToNext())
        {
            entreeTextView.setText(entreeCursor.getString(entreeIndex++));
        }
        entreeCursor.close();

        Cursor dessertCursor = database.rawQuery("select mealChoice from mealChoices where mealType = 'dessert'", null);
        while (dessertCursor.moveToNext())
        {
            dessertTextView.setText(dessertCursor.getString(dessertIndex++));
        }
        dessertCursor.close();

        Cursor wineCursor = database.rawQuery("select mealChoice from mealChoices where mealType = 'wine'", null);
        while (wineCursor.moveToNext())
        {
            wineTextView.setText(wineCursor.getString(wineIndex++));
        }
        wineCursor.close();
    }

    private void setCollections()
    {
        starterChoiceCollection = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1);
        starterChoiceCollection.add("Tuna Tartare Cones");
        starterChoiceCollection.add("Sliders and Mini Beers");
        starterChoiceCollection.add("Fresh Veggie Platter");
        starterChoiceCollection.add("Prosciutto-Wrapper Persimmons");
        starterChoiceCollection.add("Squash Soup");

        entreeChoiceCollection = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1);
        entreeChoiceCollection.add("Lobster with Mashed Potatoes");
        entreeChoiceCollection.add("Vegetarian Risotto and Couscous Salad");
        entreeChoiceCollection.add("Filet Mignon with Green Beans");
        entreeChoiceCollection.add("Neopolitan Pizza");
        entreeChoiceCollection.add("Fried Chicken & Mac and Cheese");

        dessertChoiceCollection = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1);
        dessertChoiceCollection.add("Apple Pie & Vanilla Ice Cream");
        dessertChoiceCollection.add("Chocolate Eclairs");
        dessertChoiceCollection.add("Strawberry Shortcake");
        dessertChoiceCollection.add("Treacle Tartare");
        dessertChoiceCollection.add("Chocolate Doughnuts");

        wineChoiceCollection = new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1);
        wineChoiceCollection.add("Red wine");
        wineChoiceCollection.add("White wine");
    }

    private void setOnClickListeners()
    {

        starterPanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder starterSelectionDialog = new AlertDialog.Builder(myContext);
                starterSelectionDialog.setTitle("Pick a starter");

                starterSelectionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                starterSelectionDialog.setAdapter(starterChoiceCollection, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContentValues newItem = new ContentValues();
                        SQLiteDatabase database = mealPlannerDatabaseManager.getWritableDatabase();

                        newItem.put(MealPlannerDatabaseManager.MEAL_TYPE, STARTER);
                        newItem.put(MealPlannerDatabaseManager.MEAL_CHOICE, starterChoiceCollection.getItem(which));

                        try {
                            database.update(MealPlannerDatabaseManager.TABLE_NAME, newItem, MealPlannerDatabaseManager.MEAL_TYPE + " = '"+STARTER+"'", null);
                        } catch (SQLiteException err)
                        {
                            Toast.makeText(myContext, err.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        refreshViews();
                    }
                });

                starterSelectionDialog.show();
            }
        });

        entreePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder entreeSelectionDialog = new AlertDialog.Builder(myContext);
                entreeSelectionDialog.setTitle("Pick an entree");

                entreeSelectionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                entreeSelectionDialog.setAdapter(entreeChoiceCollection, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContentValues newItem = new ContentValues();
                        SQLiteDatabase database = mealPlannerDatabaseManager.getWritableDatabase();

                        newItem.put(MealPlannerDatabaseManager.MEAL_TYPE, ENTREE);
                        newItem.put(MealPlannerDatabaseManager.MEAL_CHOICE, entreeChoiceCollection.getItem(which));

                        try {
                            database.update(MealPlannerDatabaseManager.TABLE_NAME, newItem, MealPlannerDatabaseManager.MEAL_TYPE + " = '"+ENTREE+"'", null);
                        } catch (SQLiteException err)
                        {
                            Toast.makeText(myContext, err.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        refreshViews();
                    }
                });

                entreeSelectionDialog.show();
            }
        });

        dessertPanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dessertSelectionDialog = new AlertDialog.Builder(myContext);
                dessertSelectionDialog.setTitle("Pick a dessert");

                dessertSelectionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                dessertSelectionDialog.setAdapter(dessertChoiceCollection, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContentValues newItem = new ContentValues();
                        SQLiteDatabase database = mealPlannerDatabaseManager.getWritableDatabase();

                        newItem.put(MealPlannerDatabaseManager.MEAL_TYPE, DESSERT);
                        newItem.put(MealPlannerDatabaseManager.MEAL_CHOICE, dessertChoiceCollection.getItem(which));

                        try {
                            database.update(MealPlannerDatabaseManager.TABLE_NAME, newItem, MealPlannerDatabaseManager.MEAL_TYPE + " = '"+DESSERT+"'", null);
                        } catch (SQLiteException err)
                        {
                            Toast.makeText(myContext, err.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        refreshViews();
                    }
                });

                dessertSelectionDialog.show();
            }
        });

        winePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder wineSelectionDialog = new AlertDialog.Builder(myContext);
                wineSelectionDialog.setTitle("Pick a wine");

                wineSelectionDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                wineSelectionDialog.setAdapter(wineChoiceCollection, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ContentValues newItem = new ContentValues();
                        SQLiteDatabase database = mealPlannerDatabaseManager.getWritableDatabase();

                        newItem.put(MealPlannerDatabaseManager.MEAL_TYPE, WINE);
                        newItem.put(MealPlannerDatabaseManager.MEAL_CHOICE, wineChoiceCollection.getItem(which));

                        try {
                            database.update(MealPlannerDatabaseManager.TABLE_NAME, newItem, MealPlannerDatabaseManager.MEAL_TYPE + " = '"+WINE+"'", null);
                        } catch (SQLiteException err)
                        {
                            Toast.makeText(myContext, err.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        refreshViews();
                    }
                });

                wineSelectionDialog.show();
            }
        });
    }
}
