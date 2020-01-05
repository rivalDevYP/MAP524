package map.assignmentfive.weatherviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable
{
    private String city = "", description = "";
    private int currentTemp = 0, highTemp = 0, lowTemp = 0;
    private int futureHighTemp = 0, futureLowTemp = 0;

    public WeatherData(String incomingCity, int incomingCurrentTemp, int incomingHighTemp, int incomingLowTemp, int incomingFutureHighTemp, int incomingFutureLowTemp, String incomingDesc)
    {
        this.city = incomingCity;
        this.currentTemp = incomingCurrentTemp;
        this.highTemp = incomingHighTemp;
        this.lowTemp = incomingLowTemp;
        this.futureHighTemp = incomingFutureHighTemp;
        this.futureLowTemp = incomingFutureLowTemp;
        this.description = incomingDesc;
    }

    private WeatherData(Parcel in)
    {
        this(in.readString(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readInt(), in.readString());
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>()
    {
        @Override
        public WeatherData createFromParcel(Parcel in)
        {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size)
        {
            return new WeatherData[size];
        }
    };

    public String getCity()
    {
        return city;
    }

    public int getCurrentTemp()
    {
        return currentTemp;
    }

    public int getHighTemp()
    {
        return highTemp;
    }

    public int getLowTemp()
    {
        return lowTemp;
    }

    public int getFutureHighTemp()
    {
        return futureHighTemp;
    }

    public int getFutureLowTemp()
    {
        return futureLowTemp;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(getCity());
        parcel.writeInt(getCurrentTemp());
        parcel.writeInt(getHighTemp());
        parcel.writeInt(getLowTemp());
        parcel.writeInt(getFutureHighTemp());
        parcel.writeInt(getFutureLowTemp());
        parcel.writeString(getDescription());
    }
}
