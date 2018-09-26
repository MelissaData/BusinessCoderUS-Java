package melissadata.businesscoder.model;

import org.apache.sling.commons.json.JSONObject;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class BusinessCoderTransaction {

    private final String endpoint;
    private BusinessCoderOptions options;
    private String columns;
    private String identNumber;
    private String company, phoneNumber, webAddress, stockTicker, addressLine1, addressLine2, city, postal, state, country, format;
    private boolean selectAllGroups, grpAddressDetails, grpBusinessDescription, grpGeoCode, grpCensus, grpBusinessCodes;
    private boolean selectAllColumns, columnLocationType, columnPhone, columnEmployeesEstimate, columnSalesEstimate, columnStockTicker, columnWebAddress, columnContacts;

    public BusinessCoderTransaction () {
        endpoint = "https://businesscoder.melissadata.net/WEB/BusinessCoder/doBusinessCoderUS?";
        options = new BusinessCoderOptions();
        columns = "";
        identNumber = "";
        company = "";
        phoneNumber = "";
        webAddress = "";
        stockTicker = "";
        addressLine1 = "";
        addressLine2 = "";
        city = "";
        postal = "";
        state = "";
        country = "";
        format = "";
    }
    public String processTransaction(String request) {
        String response = "";
        URI uri;
        URL url;
        try {
            uri = new URI(request);
            url = new URL(uri.toURL().toString());

            HttpURLConnection urlConn = (HttpURLConnection)(url.openConnection());

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringReader reader;
            StringWriter writer = new StringWriter();
            StringBuffer jsonResponse = new StringBuffer();
            String inputLine = "";

            if (format.equals("XML"))
            {
                String xmlLine = "";
                String xmlString = "";

                while((xmlLine = in.readLine()) != null) {
                    xmlString += xmlLine + "\n";
                }

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                t.setOutputProperty(OutputKeys.INDENT, "yes");

                reader = new StringReader(xmlString);
                try {
                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                response = writer.toString();

            } else {
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
                @SuppressWarnings("deprecation")
                JSONObject test = new JSONObject(jsonResponse.toString());
                response = test.toString(4);

            }
        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    public String generateRequestString() {
        String request = "";
        request = endpoint;
        request += "&id=" + getIdentNumber();
        request += "&opt=" + options.generateOptionString();
        request += "&cols=" + getColumns();
        try {
        if(!getCompany().equals(""));
        request += "&comp=" + URLEncoder.encode(getCompany(), "UTF-8");

        if(!getPhoneNumber().equals(""));
        request += "&full=" + URLEncoder.encode(getPhoneNumber(), "UTF-8");

        if(!getWebAddress().equals(""));
        request += "&phone=" + URLEncoder.encode(getWebAddress(), "UTF-8");

        if(!getAddressLine1().equals(""));
        request += "&email=" + URLEncoder.encode(getAddressLine1(), "UTF-8");

        if(!getAddressLine2().equals(""));
        request += "&a1=" + URLEncoder.encode(getAddressLine2(), "UTF-8");

        if(!getCity().equals(""));
        request += "&a2=" + URLEncoder.encode(getCity(), "UTF-8");

        if(!getState().equals(""));
        request += "&city=" + URLEncoder.encode(getState(), "UTF-8");

        if(!getPostal().equals(""));
        request += "&state=" + URLEncoder.encode(getPostal(), "UTF-8");

        if(!getCountry().equals(""));
        request += "&ctry=" + URLEncoder.encode(getCountry(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
         System.out.println("Unsupported Encoding Exception: " +e);
        }
        request += "&format=" + getFormat();

        return request;
    }

    public BusinessCoderOptions getOptions() {
        return options;
    }

    public void setOptions(BusinessCoderOptions options) {
        this.options = options;
    }

    public String getColumns() {
        String columnString = "";
        if(isSelectAllGroups()){
            columnString += "grpAll";
        } else {
            if(isGrpAddressDetails()){
                columnString += "grpAddressDetails";
            }

            if (isGrpBusinessDescription() && columnString.equals(""))
                columnString += "grpBusinessDescripton";
            else if (isGrpBusinessDescription() && !columnString.equals(""))
                columnString += ",grpBusinessDescripton";

            if (isGrpGeoCode() && columnString.equals(""))
                columnString += "grpGeocode";
            else if (isGrpGeoCode() && !columnString.equals(""))
                columnString += ",grpGeocode";

            if (isGrpCensus() && columnString.equals(""))
                columnString += "grpCensus";
            else if (isGrpCensus() && !columnString.equals(""))
                columnString += ",grpCensus";

            if (isGrpBusinessCodes() && columnString.equals(""))
                columnString += "grpBusinessCodes";
            else if (isGrpBusinessCodes() && !columnString.equals(""))
                columnString += ",grpBusinessCodes";
        }

        if (isColumnLocationType() && columnString.equals(""))
            columnString += "LocationType";
        else if (isColumnLocationType() && !columnString.equals(""))
            columnString += ",LocationType";

        if (isColumnPhone() && columnString.equals(""))
            columnString += "Phone";
        else if (isColumnPhone() && !columnString.equals(""))
            columnString += ",Phone";

        if (isColumnEmployeesEstimate() && columnString.equals(""))
            columnString += "EmployeesEstimate";
        else if (isColumnEmployeesEstimate() && !columnString.equals(""))
            columnString += ",EmployeesEstimate";

        if (isColumnSalesEstimate() && columnString.equals(""))
            columnString += "SalesEstimate";
        else if (isColumnSalesEstimate() && !columnString.equals(""))
            columnString += ",SalesEstimate";

        if (isColumnStockTicker() && columnString.equals(""))
            columnString += "StockTicker";
        else if (isColumnStockTicker() && !columnString.equals(""))
            columnString += ",StockTicker";

        if (isColumnWebAddress() && columnString.equals(""))
            columnString += "WebAddress";
        else if (isColumnWebAddress() && !columnString.equals(""))
            columnString += ",WebAddress";

        if (isColumnContacts() && columnString.equals(""))
            columnString += "Contacts";
        else if (isColumnContacts() && !columnString.equals(""))
            columnString += ",Contacts";


        return columnString;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStockTicker() {
        return stockTicker;
    }

    public void setStockTicker(String stockTicker) {
        this.stockTicker = stockTicker;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isSelectAllGroups() {
        return selectAllGroups;
    }

    public void setSelectAllGroups(boolean selectAllGroups) {
        this.selectAllGroups = selectAllGroups;
    }

    public boolean isGrpAddressDetails() {
        return grpAddressDetails;
    }

    public void setGrpAddressDetails(boolean grpAddressDetails) {
        this.grpAddressDetails = grpAddressDetails;
    }

    public boolean isGrpBusinessDescription() {
        return grpBusinessDescription;
    }

    public void setGrpBusinessDescription(boolean grpBusinessDescription) {
        this.grpBusinessDescription = grpBusinessDescription;
    }

    public boolean isGrpGeoCode() {
        return grpGeoCode;
    }

    public void setGrpGeoCode(boolean grpGeoCode) {
        this.grpGeoCode = grpGeoCode;
    }

    public boolean isGrpCensus() {
        return grpCensus;
    }

    public void setGrpCensus(boolean grpCensus) {
        this.grpCensus = grpCensus;
    }

    public boolean isGrpBusinessCodes() {
        return grpBusinessCodes;
    }

    public void setGrpBusinessCodes(boolean grpBusinessCodes) {
        this.grpBusinessCodes = grpBusinessCodes;
    }

    public boolean isSelectAllColumns() {
        return selectAllColumns;
    }

    public void setSelectAllColumns(boolean selectAllColumns) {
        this.selectAllColumns = selectAllColumns;
    }

    public boolean isColumnLocationType() {
        return columnLocationType;
    }

    public void setColumnLocationType(boolean columnLocationType) {
        this.columnLocationType = columnLocationType;
    }

    public boolean isColumnPhone() {
        return columnPhone;
    }

    public void setColumnPhone(boolean columnPhone) {
        this.columnPhone = columnPhone;
    }

    public boolean isColumnEmployeesEstimate() {
        return columnEmployeesEstimate;
    }

    public void setColumnEmployeesEstimate(boolean columnEmployeesEstimate) {
        this.columnEmployeesEstimate = columnEmployeesEstimate;
    }

    public boolean isColumnSalesEstimate() {
        return columnSalesEstimate;
    }

    public void setColumnSalesEstimate(boolean columnSalesEstimate) {
        this.columnSalesEstimate = columnSalesEstimate;
    }

    public boolean isColumnStockTicker() {
        return columnStockTicker;
    }

    public void setColumnStockTicker(boolean columnStockTicker) {
        this.columnStockTicker = columnStockTicker;
    }

    public boolean isColumnWebAddress() {
        return columnWebAddress;
    }

    public void setColumnWebAddress(boolean columnWebAddress) {
        this.columnWebAddress = columnWebAddress;
    }

    public boolean isColumnContacts() {
        return columnContacts;
    }

    public void setColumnContacts(boolean columnContacts) {
        this.columnContacts = columnContacts;
    }

}
