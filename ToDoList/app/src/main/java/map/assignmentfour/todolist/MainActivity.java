package map.assignmentfour.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ToDoListManager listManager;
    private ToDoItemAdapter adapter;
//    private RelativeDateTimeFormatter relativeDateTimeFormatter = new RelativeDateTimeFormatter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView todoList = findViewById(R.id.todo_list);
        ImageButton addButton = findViewById(R.id.add_item);

        listManager = new ToDoListManager(this);

        adapter = new ToDoItemAdapter(this,listManager.getItems());

        todoList.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onAddButtonClick();
            }
        });
    }

    private void onAddButtonClick()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_item);
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() // when user pressed "OK" to Add Item in the dialog
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                long now = System.currentTimeMillis();

                ToDoItem item = new ToDoItem(input.getText().toString(), now, false);
                listManager.addItem(item);
                adapter.updateItems(listManager.getItems());
            }
        });

        builder.show();
    }

    private class ToDoItemAdapter extends ArrayAdapter<ToDoItem>
    {
        private Context context;
        private List<ToDoItem> items;

        private ToDoItemAdapter(Context incomingContext, List<ToDoItem> incomingItems)
        {
            super(incomingContext, -1, incomingItems);
            this.context = incomingContext;
            this.items = incomingItems;
        }

        private void updateItems(List<ToDoItem> items)
        {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return items.size();
        }

        @NonNull
        @Override
        public View getView(int incomingPosition, View incomingConvertView, @NonNull ViewGroup incomingParent)
        {
            final ItemViewHolder holder;

            if (incomingConvertView == null)
            {
                incomingConvertView = LayoutInflater.from(context).inflate(
                        R.layout.to_do_item_layout,
                        incomingParent,
                        false
                );

                holder = new ItemViewHolder();
                holder.itemDescription = incomingConvertView.findViewById(R.id.itemText);
                holder.itemEntryDate = incomingConvertView.findViewById(R.id.entryTime);
                holder.itemState = incomingConvertView.findViewById(R.id.completedCheckbox);
                incomingConvertView.setTag(holder);
            }
            else
            {
                holder = (ItemViewHolder) incomingConvertView.getTag();
            }

            final ImageButton deleteButton = incomingConvertView.findViewById(R.id.delete_item_button);

            holder.itemDescription.setText(items.get(incomingPosition).getDescription());
            holder.itemEntryDate.setText(DateUtils.getRelativeTimeSpanString(
                    items.get(incomingPosition).getEntryTime(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS
            ));
            holder.itemState.setChecked(items.get(incomingPosition).isComplete());

            holder.itemState.setTag(items.get(incomingPosition));
            deleteButton.setTag(items.get(incomingPosition));

            View.OnClickListener onClickListener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ToDoItem item = (ToDoItem) holder.itemState.getTag();
                    item.toggleComplete();
                    listManager.updateItem(item);
                    notifyDataSetChanged();
                }
            };

            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Are you sure you want to delete?");
                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            System.out.println("deleted");
                            ToDoItem item = (ToDoItem) v.getTag();
                            listManager.deleteItem(item);
                            adapter.updateItems(listManager.getItems());
                            notifyDataSetChanged();
                        }
                    });

                    builder.show();
                }
            });

            incomingConvertView.setOnClickListener(onClickListener);
            holder.itemState.setOnClickListener(onClickListener);

            return incomingConvertView;
        }
    }

    public static class ItemViewHolder
    {
        public TextView itemDescription;
        public TextView itemEntryDate;
        public CheckBox itemState;
    }
}
