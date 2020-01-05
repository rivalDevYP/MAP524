package map.assignmentfour.todolist;

import androidx.annotation.NonNull;

import java.util.Date;

public class ToDoItem
{
    private String description;
    private long entryTime;
    private boolean isComplete;
    private long id;

    public ToDoItem()
    {
        description = "";
        entryTime = 0;
        isComplete = false;
    }

    public ToDoItem(String incoming_description, long incoming_entryTime, boolean incoming_isComplete)
    {
        this(incoming_description, incoming_entryTime, incoming_isComplete, -1);
    }

    public ToDoItem(String incomingDescription, long incomingEntryTime, boolean incomingIsComplete, long incomingID)
    {
        this.description = incomingDescription;
        this.entryTime = incomingEntryTime;
        this.isComplete = incomingIsComplete;
        this.id = incomingID;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public long getEntryTime()
    {
        return entryTime;
    }

    public long getId()
    {
        return id;
    }

    public void toggleComplete()
    {
        isComplete = !isComplete;
    }

    @NonNull
    @Override
    public String toString()
    {
        return getDescription();
    }
}
