package com.example.myapplication.Domain;

public class MpesaRequestResponse {
    public String MerchantRequestID;
    // @JsonProperty("CheckoutRequestID")
    public String CheckoutRequestID;
    //@JsonProperty("ResponseCode")
    public String ResponseCode;
    //@JsonProperty("ResponseDescription")
    public String ResponseDescription;
    //@JsonProperty("CustomerMessage")
    public String CustomerMessage;

    public MpesaRequestResponse() {
    }

    public MpesaRequestResponse(String merchantRequestID, String checkoutRequestID, String responseCode, String responseDescription, String customerMessage) {
        MerchantRequestID = merchantRequestID;
        CheckoutRequestID = checkoutRequestID;
        ResponseCode = responseCode;
        ResponseDescription = responseDescription;
        CustomerMessage = customerMessage;
    }

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }

    public String getCustomerMessage() {
        return CustomerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        CustomerMessage = customerMessage;
    }
}
