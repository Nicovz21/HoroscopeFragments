package com.hfad.horoscope;

import org.json.JSONException;
import org.json.JSONObject;

public class HoroscopeHandler {
    final public static String [] horoscopes = new String[12];;
    public HoroscopeHandler(){

        //populate array with zodiac signs
        horoscopes[0] = "Aquarius";
        horoscopes[1] = "Pisces";
        horoscopes[2] = "Aries";
        horoscopes[3] = "Taurus";
        horoscopes[4] = "Gemini";
        horoscopes[5] = "Cancer";
        horoscopes[6] = "Leo";
        horoscopes[7] = "Virgo";
        horoscopes[8] = "Libra";
        horoscopes[9] = "Scorpio";
        horoscopes[10] = "Sagittarius";
        horoscopes[11] = "Capricorn";
    }
    //Read from the JSON and get the string needed
    public String getSignFact(String signFactJsonStr) throws JSONException {
        JSONObject signFactJSONObj = new JSONObject(signFactJsonStr);
        return signFactJSONObj.getString("Today:");
    }
    public String getMatchFact(String signFactJsonStr) throws JSONException {
        JSONObject signFactJSONObj = new JSONObject(signFactJsonStr);
        return signFactJSONObj.getString("Matchs");
    }
}
