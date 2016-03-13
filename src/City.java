/**
* Model class for storing city attribute data.
*/

public class City {
  private int _id;
  private String key;
  private String name;
  private String fullName;
  private String airport_code;
  private String type;
  private String country;
  private GeoPos geo_position;
  private int location_id;
  private boolean inEurope;
  private String countryCode;
  private boolean coreCountry;
  private double distance;

  // Getters for the values we want to extract
  public int getID() {
    return _id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public double getLat() {
    return geo_position.getLatitude();
  }

  public double getLon() {
    return geo_position.getLongitude();
  }
}

