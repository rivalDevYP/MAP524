package map.finalproject.weddingplannermaterial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MealPlannerDatabaseManager extends SQLiteOpenHelper
{
    public static final String
            DATABASE_NAME = "mealPlanner.db",
            TABLE_NAME = "mealChoices",
            MEAL_TYPE = "mealType",
            MEAL_CHOICE = "mealChoice";

    private static final int DATABASE_VERSION = 1;

    private static MealPlannerDatabaseManager instance = null;

    public static MealPlannerDatabaseManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new MealPlannerDatabaseManager(context);
        }

        return instance;
    }

    public MealPlannerDatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createDatabase =
                "CREATE TABLE " + TABLE_NAME + "( " + MEAL_TYPE + " TEXT PRIMARY KEY NOT NULL, " + MEAL_CHOICE + " TEXT NOT NULL)";

        db.execSQL(createDatabase);

        db.execSQL("INSERT INTO " + TABLE_NAME + " ("+ MEAL_TYPE + ", " + MEAL_CHOICE +") VALUES ('starter', 'Starter')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " ("+ MEAL_TYPE + ", " + MEAL_CHOICE +") VALUES ('entree', 'Entree')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " ("+ MEAL_TYPE + ", " + MEAL_CHOICE +") VALUES ('dessert', 'Dessert')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " ("+ MEAL_TYPE + ", " + MEAL_CHOICE +") VALUES ('wine', 'Wine')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
