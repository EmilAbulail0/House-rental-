package com.example.houserentals;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

public class JasonParser {
    public static List<HouseProperties> getObjectFromJason(String jason) {
        List<HouseProperties> houses;
        try {
            JSONArray houseArr = new JSONArray(jason);
            houses = new ArrayList<>();
            for(int i = 0; i<houseArr.length(); i++)
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject= (JSONObject) houseArr.get(i);
                HouseProperties house = new HouseProperties();
                house.setCity(jsonObject.getString("city"));
                house.setPostalAddress(jsonObject.getString("postal address"));
                house.setSurfaceArea(jsonObject.getString("surface area"));
                house.setConstructionYear(jsonObject.getString("construction year"));
                house.setNumOfBedrooms(jsonObject.getString("numOfBedroom"));
                house.setRentalPrice(jsonObject.getString("rental price"));
                house.setStatus(jsonObject.getString("status"));
                house.setPhoto(jsonObject.getString("photos"));
                house.setAvailabilityDate(jsonObject.getString("availability date"));
                house.setDescription(jsonObject.getString("description"));
                houses.add(house);
                HouseProperties.rentHouses.add(house);
            }

        } catch (JSONException exception)
        {
            exception.printStackTrace();
            return null;
        }
        return houses;
    }
}

