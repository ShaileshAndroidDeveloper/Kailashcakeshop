package videoplayer.vishcoder.com.kailashcakeshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseModel implements Serializable {

    String CART_ID;
    String CAKE_ID ;
    String CAKE_TYPE;
    String CAKE_TITLE;
    String OFFER;

    public String getOFFER() {
        return OFFER;
    }

    public void setOFFER(String OFFER) {
        this.OFFER = OFFER;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    String MESSAGE;

    public String getCART_ID() {
        return CART_ID;
    }

    public void setCART_ID(String CART_ID) {
        this.CART_ID = CART_ID;
    }

    public String getCAKE_ID() {
        return CAKE_ID;
    }

    public void setCAKE_ID(String CAKE_ID) {
        this.CAKE_ID = CAKE_ID;
    }

    public String getCAKE_TYPE() {
        return CAKE_TYPE;
    }

    public void setCAKE_TYPE(String CAKE_TYPE) {
        this.CAKE_TYPE = CAKE_TYPE;
    }

    public String getCAKE_TITLE() {
        return CAKE_TITLE;
    }

    public void setCAKE_TITLE(String CAKE_TITLE) {
        this.CAKE_TITLE = CAKE_TITLE;
    }

    public String getCAKE_DETAIL() {
        return CAKE_DETAIL;
    }

    public void setCAKE_DETAIL(String CAKE_DETAIL) {
        this.CAKE_DETAIL = CAKE_DETAIL;
    }

    public String getCAKE_FLAVOUR() {
        return CAKE_FLAVOUR;
    }

    public void setCAKE_FLAVOUR(String CAKE_FLAVOUR) {
        this.CAKE_FLAVOUR = CAKE_FLAVOUR;
    }

    public String getCAKE_RATE() {
        return CAKE_RATE;
    }

    public void setCAKE_RATE(String CAKE_RATE) {
        this.CAKE_RATE = CAKE_RATE;
    }

    public String getCAKE_IMAGE() {
        return CAKE_IMAGE;
    }

    public void setCAKE_IMAGE(String CAKE_IMAGE) {
        this.CAKE_IMAGE = CAKE_IMAGE;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public void setFIRSTNAME(String FIRSTNAME) {
        this.FIRSTNAME = FIRSTNAME;
    }

    public String getLASTNAME() {
        return LASTNAME;
    }

    public void setLASTNAME(String LASTNAME) {
        this.LASTNAME = LASTNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getPINCODE() {
        return PINCODE;
    }

    public void setPINCODE(String PINCODE) {
        this.PINCODE = PINCODE;
    }

    public String getDELIVERYDATE() {
        return DELIVERYDATE;
    }

    public void setDELIVERYDATE(String DELIVERYDATE) {
        this.DELIVERYDATE = DELIVERYDATE;
    }

    public String getDELIVERYTIME() {
        return DELIVERYTIME;
    }

    public void setDELIVERYTIME(String DELIVERYTIME) {
        this.DELIVERYTIME = DELIVERYTIME;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getPHOTOCKAE_URL() {
        return PHOTOCKAE_URL;
    }

    public void setPHOTOCKAE_URL(String PHOTOCKAE_URL) {
        this.PHOTOCKAE_URL = PHOTOCKAE_URL;
    }

    public String getORDERDATETIME() {
        return ORDERDATETIME;
    }

    public void setORDERDATETIME(String ORDERDATETIME) {
        this.ORDERDATETIME = ORDERDATETIME;
    }

    String CAKE_DETAIL;
    String CAKE_FLAVOUR;
    String CAKE_RATE  ;
    String CAKE_IMAGE  ;
    String QUANTITY  ;
    String ORDERID  ;
    String FIRSTNAME ;
    String LASTNAME  ;
    String ADDRESS  ;
    String AREA  ;
    String PINCODE;


    String DELIVERYDATE;
    String DELIVERYTIME ;
    String MOBILE  ;
    String PHOTOCKAE_URL;
    String ORDERDATETIME ;
    ArrayList<ListData_Model> list;

    public ArrayList<ListData_Model> getList() {
        return list;
    }

    public void setList(ArrayList<ListData_Model> list) {
        this.list = list;
    }



    ArrayList<ListData_Model> user_detail;

    private String message,status,response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public ArrayList<ListData_Model> getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(ArrayList<ListData_Model> user_detail) {
        this.user_detail = user_detail;
    }

}
