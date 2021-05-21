package com.app.views;

public class PredictionView extends View{

    /** Returns a string in a danish currency format
     *
     * @param prediction predicted price to format
     * @return
     */
    private String formatPrediction(int prediction){
        String result;
        result=String.valueOf(prediction);
        result = result.substring(0,result.length()-2) + "," + result.substring(result.length()-2);
        return result;
    }


    public void printExpectedIncome(int prediction, int days){
        print("Expected income in " + days + "days");
        print(formatPrediction(prediction) + "kr");
    }
}
