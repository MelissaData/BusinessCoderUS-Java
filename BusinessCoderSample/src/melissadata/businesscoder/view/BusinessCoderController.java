package melissadata.businesscoder.view;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import melissadata.businesscoder.model.BusinessCoderTransaction;
import melissadata.businesscoder.model.BusinessCoderOptions;
import melissadata.businesscoder.Main;

public class BusinessCoderController  {

    private Main main;
    private BusinessCoderOptions option;

    private BusinessCoderTransaction Transaction;

    @FXML
    private Button buttonSend;
    @FXML
    private Button buttonClear;
    @FXML
    private TabPane tabPane;
    private final int CONFIGURATION_TAB = 0;
    private final int RESPONSE_TAB = 1;

    @FXML
    private TextField inputLicenseKeyText;
    @FXML
    private TextField inputCompanyText;
    @FXML
    private TextField inputPhoneNumberText;
    @FXML
    private TextField inputWebAddressText;

    @FXML
    private TextField inputStockTickerText;
    @FXML
    private TextField inputAddressLine1Text;
    @FXML
    private TextField inputAddressLine2Text;
    @FXML
    private TextField inputCityText;
    @FXML
    private TextField inputStateText;
    @FXML
    private TextField inputPostalText;
    @FXML
    private TextField inputCountryText;

    @FXML
    private ComboBox<String> optionReturnDominantBusinessBox;
    @FXML
    private ComboBox<String> optionCentricHintBox;
    @FXML
    private ComboBox<String> optionMaxContactsBox;
    @FXML
    private ComboBox<String> optionSICNAISConfidenceBox;

    @FXML
    private CheckBox groupAllCheckbox;
    @FXML
    private CheckBox groupAddressDetailsCheckbox;
    @FXML
    private CheckBox groupBusinessDescriptionCheckbox;
    @FXML
    private CheckBox groupGeoCodeCheckbox;
    @FXML
    private CheckBox groupCensusCheckbox;
    @FXML
    private CheckBox groupBusinessCodesCheckbox;

    @FXML
    private CheckBox columnCheckAllColumnsCheckbox;
    @FXML
    private CheckBox columnLocationTypeCheckbox;
    @FXML
    private CheckBox columnPhoneCheckbox;
    @FXML
    private CheckBox columnEmployeesEstimateCheckbox;
    @FXML
    private CheckBox columnSalesEstimateCheckbox;
    @FXML
    private CheckBox columnStockTickerCheckbox;
    @FXML
    private CheckBox columnWebAddressCheckbox;
    @FXML
    private CheckBox columnContactsCheckbox;

    @FXML
    private TextArea RequestTextArea;
    @FXML
    private TextArea ResponseTextArea;

    @FXML
    private RadioButton jsonResponseFormatRadio;
    @FXML
    private RadioButton xmlResponseFormatRadio;
    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public BusinessCoderController() {
    	Transaction = new BusinessCoderTransaction();
		option = new BusinessCoderOptions();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        setupOptionSelections();
        initializeFormatRadioButtons();
        selectColumnCheckbox();
        selectAllColumnAction();
        selectAllGroupAction();
        selectGroupCheckbox();
        initializeTextFields();
        sendButtonAction();
        clearButtonAction();
        updateRequestText();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * //@param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    public void sendButtonAction() {
        buttonSend.setOnAction((event) -> {
            ResponseTextArea.setText(Transaction.processTransaction(RequestTextArea.getText()));
            tabPane.getSelectionModel().select(RESPONSE_TAB);
        });
    }

    public void clearButtonAction(){
        buttonClear.setOnAction((event) -> {
            inputCompanyText.clear();
            inputWebAddressText.clear();
            inputPhoneNumberText.clear();
            inputStockTickerText.clear();
            inputAddressLine1Text.clear();
            inputAddressLine2Text.clear();
            inputCityText.clear();
            inputPostalText.clear();
            inputStateText.clear();
            inputCountryText.clear();
            returnToConfiguration();
        });
    }

    public void initializeTextFields(){
        inputLicenseKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setIdentNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCompanyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCompany(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputWebAddressText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setWebAddress(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputPhoneNumberText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPhoneNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputStockTickerText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setStockTicker(newvalue);
            updateRequestText();
            returnToConfiguration();

        });

        inputAddressLine1Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine1(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine2Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine2(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCityText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCity(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputPostalText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPostal(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputStateText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setState(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCountryText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCountry(newvalue);
            updateRequestText();
            returnToConfiguration();
        });


    }
    // Define what values each combo box can hold
    // For example, ReturnDominantBusiness can be set to BLANK, "TRUE", or "FALSE"
    private void setupOptionSelections() {
        optionReturnDominantBusinessBox.setItems(FXCollections.observableArrayList("", "True", "False"));
        optionCentricHintBox.setItems(FXCollections.observableArrayList("", "Company", "Address", "Phone"));
        optionMaxContactsBox.setItems(FXCollections.observableArrayList("", "1", "5", "10", "50"));
        optionSICNAISConfidenceBox.setItems(FXCollections.observableArrayList("", "Strict", "Loose"));
    }

    public void setReturnDominanOptiontBusiness() {
        option.setOptionReturnDominantBusiness(optionReturnDominantBusinessBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }
    public void setOptionCentricHint() {

        option.setOptionCentricHint(optionCentricHintBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void setOptionMaxContacts() {
        option.setOptionMaxContacts(optionMaxContactsBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void setOptionSICNAISConfidence() {
        option.setOptionSICNAISConfidence(optionSICNAISConfidenceBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    private void initializeFormatRadioButtons(){
        jsonResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("JSON");
            xmlResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        xmlResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("XML");
            jsonResponseFormatRadio.setSelected(false);
            updateRequestText();
        });
    }

    private void returnToConfiguration(){
        if(tabPane.getSelectionModel().getSelectedIndex() != 0)	tabPane.getSelectionModel().select(CONFIGURATION_TAB);

    }
    private void selectAllGroupAction() {
        groupAllCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAllGroups()){
                Transaction.setGrpAddressDetails(true);
                groupAddressDetailsCheckbox.setSelected(true);

                Transaction.setGrpBusinessCodes(true);
                groupBusinessCodesCheckbox.setSelected(true);

                Transaction.setGrpCensus(true);
                groupCensusCheckbox.setSelected(true);

                Transaction.setGrpBusinessDescription(true);
                groupBusinessDescriptionCheckbox.setSelected(true);

                Transaction.setGrpGeoCode(true);
                groupGeoCodeCheckbox.setSelected(true);

            } else {
                Transaction.setGrpAddressDetails(false);
                groupAddressDetailsCheckbox.setSelected(false);

                Transaction.setGrpBusinessCodes(false);
                groupBusinessCodesCheckbox.setSelected(false);

                Transaction.setGrpCensus(false);
                groupCensusCheckbox.setSelected(false);

                Transaction.setGrpBusinessDescription(false);
                groupBusinessDescriptionCheckbox.setSelected(false);

                Transaction.setGrpGeoCode(false);
                groupGeoCodeCheckbox.setSelected(false);
            }
            Transaction.setSelectAllGroups(groupAllCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void selectGroupCheckbox(){
        groupBusinessCodesCheckbox.setOnAction((event) -> {
            Transaction.setGrpBusinessCodes(groupBusinessCodesCheckbox.isSelected());
            updateRequestText();
        });

        groupBusinessDescriptionCheckbox.setOnAction((event) -> {
            Transaction.setGrpBusinessDescription(groupBusinessDescriptionCheckbox.isSelected());
            updateRequestText();
        });

        groupCensusCheckbox.setOnAction((event) -> {
            Transaction.setGrpCensus(groupCensusCheckbox.isSelected());
            updateRequestText();
        });

        groupGeoCodeCheckbox.setOnAction((event) -> {
            Transaction.setGrpGeoCode(groupGeoCodeCheckbox.isSelected());
            updateRequestText();
        });

        groupAddressDetailsCheckbox.setOnAction((event) -> {
            Transaction.setGrpAddressDetails(groupAddressDetailsCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void selectAllColumnAction() {
        columnCheckAllColumnsCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAllColumns()){
                Transaction.setColumnLocationType(true);
                columnLocationTypeCheckbox.setSelected(true);

                Transaction.setColumnPhone(true);
                columnPhoneCheckbox.setSelected(true);

                Transaction.setColumnEmployeesEstimate(true);
                columnEmployeesEstimateCheckbox.setSelected(true);

                Transaction.setColumnSalesEstimate(true);
                columnSalesEstimateCheckbox.setSelected(true);

                Transaction.setColumnStockTicker(true);
                columnStockTickerCheckbox.setSelected(true);

                Transaction.setColumnWebAddress(true);
                columnWebAddressCheckbox.setSelected(true);

                Transaction.setColumnContacts(true);
                columnContactsCheckbox.setSelected(true);

            } else {
                Transaction.setColumnLocationType(false);
                columnLocationTypeCheckbox.setSelected(false);

                Transaction.setColumnPhone(false);
                columnPhoneCheckbox.setSelected(false);

                Transaction.setColumnEmployeesEstimate(false);
                columnEmployeesEstimateCheckbox.setSelected(false);

                Transaction.setColumnSalesEstimate(false);
                columnSalesEstimateCheckbox.setSelected(false);

                Transaction.setColumnStockTicker(false);
                columnStockTickerCheckbox.setSelected(false);

                Transaction.setColumnWebAddress(false);
                columnWebAddressCheckbox.setSelected(false);

                Transaction.setColumnContacts(false);
                columnContactsCheckbox.setSelected(false);
            }
            Transaction.setSelectAllColumns(columnCheckAllColumnsCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void selectColumnCheckbox() {
        columnLocationTypeCheckbox.setOnAction((event) -> {
            Transaction.setColumnLocationType(columnLocationTypeCheckbox.isSelected());
            updateRequestText();
        });

        columnPhoneCheckbox.setOnAction((event) -> {
            Transaction.setColumnPhone(columnPhoneCheckbox.isSelected());
            updateRequestText();
        });

        columnEmployeesEstimateCheckbox.setOnAction((event) -> {
            Transaction.setColumnEmployeesEstimate(columnEmployeesEstimateCheckbox.isSelected());
            updateRequestText();
        });

        columnSalesEstimateCheckbox.setOnAction((event) -> {
            Transaction.setColumnSalesEstimate(columnSalesEstimateCheckbox.isSelected());
            updateRequestText();
        });

        columnStockTickerCheckbox.setOnAction((event) -> {
            Transaction.setColumnStockTicker(columnStockTickerCheckbox.isSelected());
            updateRequestText();
        });

        columnWebAddressCheckbox.setOnAction((event) -> {
            Transaction.setColumnWebAddress(columnWebAddressCheckbox.isSelected());
            updateRequestText();
        });

        columnContactsCheckbox.setOnAction((event) -> {
            Transaction.setColumnContacts(columnContactsCheckbox.isSelected());
            updateRequestText();
        });
    }

    private void updateRequestText(){
        //try {
            RequestTextArea.setText(Transaction.generateRequestString());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }
}
