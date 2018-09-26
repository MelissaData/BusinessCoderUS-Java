package melissadata.businesscoder.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BusinessCoderOptions {

    private final StringProperty optionReturnDominantBusiness;
    private final StringProperty optionCentricHint;
    private final StringProperty optionMaxContacts;
    private final StringProperty optionSICNAISConfidence;

    public BusinessCoderOptions() {
        optionReturnDominantBusiness = new SimpleStringProperty("");
        optionCentricHint = new SimpleStringProperty("");
        optionMaxContacts = new SimpleStringProperty("");
        optionSICNAISConfidence = new SimpleStringProperty("");
    }

    public String generateOptionString() {
        String optionString = "";

        if(!getOptionReturnDominantBusiness().equals(""))
            optionString += "ReturnDominantBusiness:" + getOptionReturnDominantBusiness();
        //System.out.println(optionString);

        if(!getOptionCentricHint().equals("") && !optionString.equals(""))
            optionString += ",CentricHint:" + getOptionCentricHint();
        else if(!getOptionCentricHint().equals("") && optionString.equals(""))
            optionString += "CentricHint:" + getOptionCentricHint();
        //System.out.println(optionString);

        if(!getOptionMaxContacts().equals("") && !optionString.equals(""))
            optionString += ",MaxContacts:" + getOptionMaxContacts();
        else if(!getOptionMaxContacts().equals("") && optionString.equals(""))
            optionString += "MaxContacts:" + getOptionMaxContacts();
        //System.out.println(optionString);

        if(!getOptionSICNAISConfidence().equals("") && !optionString.equals(""))
            optionString += ",SICNAISConfidence:" + getOptionSICNAISConfidence();
        else if(!getOptionSICNAISConfidence().equals("") && optionString.equals(""))
            optionString += "SICNAISConfidence:" + getOptionSICNAISConfidence();
        //System.out.println(optionString);
        return optionString;
    }

    public String getOptionReturnDominantBusiness() {
        return optionReturnDominantBusiness.get();
    }

    public StringProperty optionReturnDominantBusinessProperty() {
        return optionReturnDominantBusiness;
    }

    public void setOptionReturnDominantBusiness(String optionReturnDominantBusiness) {
        this.optionReturnDominantBusiness.set(optionReturnDominantBusiness);
    }

    public String getOptionCentricHint() {
        return optionCentricHint.get();
    }

    public StringProperty optionCentricHintProperty() {
        return optionCentricHint;
    }

    public void setOptionCentricHint(String optionCentricHint) {
        this.optionCentricHint.set(optionCentricHint);
    }

    public String getOptionMaxContacts() {
        return optionMaxContacts.get();
    }

    public StringProperty optionMaxContactsProperty() {
        return optionMaxContacts;
    }

    public void setOptionMaxContacts(String optionMaxContacts) {
        this.optionMaxContacts.set(optionMaxContacts);
    }

    public String getOptionSICNAISConfidence() {
        return optionSICNAISConfidence.get();
    }

    public StringProperty optionSICNAISConfidenceProperty() {
        return optionSICNAISConfidence;
    }

    public void setOptionSICNAISConfidence(String optionSICNAISConfidence) {
        this.optionSICNAISConfidence.set(optionSICNAISConfidence);
    }

}
