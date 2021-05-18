package com.app.models.services;

import com.app.models.MemberModel;
import com.app.models.PricingModel;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PaymentRequestService{
    private FileService fileService;

    public PaymentRequestService(String path) throws IOException{
        fileService = new FileService(path);
    }

    public void createPaymentRequest(MemberModel[] memberModels) throws FileNotFoundException {
        String[] paymentRequests = new String[memberModels.length];
        for(int i = 0; i < paymentRequests.length; i++){
            int price = PricingModel.calculateMemberPrice(memberModels[i]);
            String memberId = memberModels[i].getID();
            paymentRequests[i] = String.join(";",memberId,String.valueOf(price));
        }
        fileService.writeToFile(paymentRequests);
    }
}
