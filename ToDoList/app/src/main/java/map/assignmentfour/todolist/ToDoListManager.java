package map.assignmentfour.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import map.assignmentfour.todolist.domain.DatabaseHelper;

public class ToDoListManager
{
    private DatabaseHelper databaseHelper;

    public ToDoListManager(Context incomingContext)
    {
        databaseHelper = DatabaseHelper.getInstance(incomingContext);
    }

    public List<ToDoItem> getItems()
    {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);
        List<ToDoItem> items = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                ToDoItem item = new ToDoItem(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ENTRY_TIME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPLETED)) != 0,
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID)));
                items.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return items;
    }

    public void addItem(ToDoItem incomingItem)
    {
        ContentValues newItem = new ContentValues();
        newItem.put(DatabaseHelper.DESCRIPTION, incomingItem.getDescription());
        newItem.put(DatabaseHelper.ENTRY_TIME, incomingItem.getEntryTime());
        newItem.put(DatabaseHelper.COMPLETED, incomingItem.isComplete());

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insert(DatabaseHelper.TABLE_NAME, null, newItem);
    }

    public void updateItem(ToDoItem incomingItem)
    {
        ContentValues updateItem = new ContentValues();
        updateItem.put(DatabaseHelper.DESCRIPTION, incomingItem.getDescription());
        updateItem.put(DatabaseHelper.ENTRY_TIME, incomingItem.getEntryTime());
        updateItem.put(DatabaseHelper.COMPLETED, incomingItem.isComplete());

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] args = new String[] { String.valueOf(incomingItem.getId())};

        db.update(DatabaseHelper.TABLE_NAME, updateItem, DatabaseHelper.ID + "=?", args);
    }

    public void deleteItem(ToDoItem incomingItem)
    {
        System.out.println(incomingItem.getId());
        String[] args = new String[] { String.valueOf(incomingItem.getId())};
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=?", args);
        getItems();
    }
}
