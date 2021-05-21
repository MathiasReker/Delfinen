package com.app.controllers.menuactions;

import com.app.controllers.MenuController;

public class PaymentSubMenu extends MenuActions{

    public PaymentSubMenu(String description){
        super(description);
    }

    public void run(){
    new MenuController("Payment Menu","Please choose an option: ",setupMenu());
    }

    private MenuActions[] setupMenu(){
        return new MenuActions[] {
                new RenewalRequestMenuAction("Send payment requests for expiring Members"),
                new PredictIncomeMenuAction("Predict Income - input days"),
                new PredictIncomeDefinedMenuAction("Predict Income - 30 days"),
                new EditMemberMenuAction("Back")
        };
    }

}
