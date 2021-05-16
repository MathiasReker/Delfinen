package com.app.models;

import java.util.ArrayList;

public class DisciplineModel {

  /**
   * Take an input to give the following style.
   * @param choice the input that define whit style been chosen.
   * @return the chosen style.
   */
  public String chosenStyle(int choice) {

    return switch (choice) {
      case 1 -> String.valueOf(StyleModel.BUTTERFLY);
      case 2 -> String.valueOf(StyleModel.BACKSTROKE);
      case 3 -> String.valueOf(StyleModel.BREASTSTROKE);
      case 4 -> String.valueOf(StyleModel.FREESTYLE);
      case 5 -> String.valueOf(StyleModel.MEDLEY);
      default -> null;
    };

  }

  /**
   * Output a list with the distances for the specific swim style.
   * @param member the member we are looking at.
   * @param style that define the distance.
   * @return a list og distances.
   */
  public ArrayList<String> chosenDistance(Member member, String style) { // member er kun nødvendigt hvis vi har en member før den her mulighed

    ArrayList<String> Distances = new ArrayList<>();

    if (style.equals(StyleModel.BUTTERFLY) || style.equals(StyleModel.BACKSTROKE) || style.equals(StyleModel.BACKSTROKE)) {
      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
    } else if (style.equals(StyleModel.FREESTYLE)) {

      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
      Distances.add(Distance.FOUR_HUNDRED + " m");
      if (member.getGender --> boy) {              // Skal lige fixes :-P
        Distances.add(Distance.FIFTEEN_HUNDRED + " m");
      } else {
        Distances.add(Distance.EIGHT_HUNDRED + " m");
      }

    } else if (style.equals(StyleModel.MEDLEY)) {
      Distances.add(Distance.FIFTY + " m");
      Distances.add(Distance.HUNDRED + " m");
      Distances.add(Distance.TWO_HUNDRED + " m");
      Distances.add(Distance.FOUR_HUNDRED + " m");
    } else {
      //Noget er gået galt, nok ikke vigtigt med validering :-P
    }


    return Distances;
  }


}
