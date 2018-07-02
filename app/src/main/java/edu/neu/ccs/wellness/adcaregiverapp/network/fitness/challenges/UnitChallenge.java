package edu.neu.ccs.wellness.adcaregiverapp.network.fitness.challenges;

import org.json.JSONException;
import org.json.JSONObject;

import edu.neu.ccs.wellness.adcaregiverapp.network.fitness.interfaces.UnitChallengeInterface;

/**
 * Created by hermansaksono on 10/16/17.
 */

public class UnitChallenge implements UnitChallengeInterface {
    private int option;
    private String text;
    private String jsonText;
    private float goal;
    private String unit;

    public UnitChallenge(int option, String text, String jsonText, float goal, String unit) {
        this.option = option;
        this.text = text;
        this.goal = goal;
        this.unit = unit;
        this.jsonText = jsonText;
    }

    public static UnitChallenge newInstance(JSONObject jsonObject){
        UnitChallenge unitChallenge = null;
        try {
            unitChallenge = new UnitChallenge(jsonObject.getInt("option"),
                    jsonObject.getString("text"),
                    jsonObject.toString(),
                    (float) jsonObject.getDouble("goal"),
                    jsonObject.getString("unit"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return unitChallenge;
    }

    public int getOption() { return this.option; }

    @Override
    public String getText() { return this.text; }

    @Override
    public float getGoal() { return this.goal; }

    @Override
    public String getUnit() { return this.unit; }

    @Override
    public String getJsonText() {return this.jsonText; }

    public void setOption(int option) {
        this.option = option;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setJsonText(String jsonText) {
        this.jsonText = jsonText;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
