package map.finalproject.weddingplannermaterial;

public class VenueManager
{
    private String name;
    private double latitude;
    private double longitude;

    public VenueManager(String incomingName, double incomingLatitude, double incomingLongitude)
    {
        this.name = incomingName;
        this.latitude = incomingLatitude;
        this.longitude = incomingLongitude;
    }

    public String getName()
    {
        return name;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }
}
