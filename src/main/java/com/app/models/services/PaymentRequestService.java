package com.app.models.services;

import com.app.models.MemberModel;
import com.app.models.PricingModel;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PaymentRequestService {
  private final FileService FILE_SERVICE;

  public PaymentRequestService(String path) throws IOException {
    FILE_SERVICE = new FileService(path);
  }

  public void createPaymentRequest(MemberModel[] memberModels) throws FileNotFoundException {
    String[] paymentRequests = new String[memberModels.length];
    for (int i = 0; i < paymentRequests.length; i++) {
      int price = PricingModel.calculateMemberPrice(memberModels[i]);
      String memberId = memberModels[i].getId();
      paymentRequests[i] = String.join(";", memberId, String.valueOf(price));
    }
    FILE_SERVICE.writeToFile(paymentRequests);
  }
}
