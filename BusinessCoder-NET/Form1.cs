using System;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.IO;
using Newtonsoft.Json.Linq;
using System.Diagnostics;

namespace BusinessCoder_NET
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnSubmit_Click(object sender, EventArgs e)
        {
            String RESTRequest = "";
            String Columns = "";
            String Options = "";

            // *************************************************************************************
            // Set the License String in the Request
            // *************************************************************************************
            RESTRequest += @"&id=" + Uri.EscapeDataString(txtLicense.Text);

            // *************************************************************************************
            // Set the Columns in the Request
            // *************************************************************************************
            foreach (object itemChecked in colsIndividual.CheckedItems)
            {
                Columns += itemChecked.ToString() + ",";
            }

            foreach (object itemChecked in colsGroup.CheckedItems)
            {
                Columns += itemChecked.ToString() + ",";
            }
            
            // Set Options
            if (Columns.Contains(","))
            {
                Columns = Columns.Substring(0, Columns.Length - 1);
                RESTRequest += @"&cols=" + Columns;
            }



            // *************************************************************************************
            // Set the Options in the Request
            // *************************************************************************************

            // Set the centric hint option
            if (!string.IsNullOrEmpty(optCentricHint.Text))
                Options += "CentricHint:" + optCentricHint.Text + ",";

            // Set the return dominant business option
            if (!string.IsNullOrEmpty(optReturnDominantBusiness.Text))
                Options += "ReturnDominantBusiness:" + optReturnDominantBusiness.Text + ",";

            // Set the SIC NAICS Confidence option
            if (!string.IsNullOrEmpty(optSICNAICSConfidence.Text))
                Options += "SICNAICSConfidence:" + optSICNAICSConfidence.Text + ",";

            // Set the max contacts option
            if (!string.IsNullOrEmpty(optMaxContacts.Text))
                Options += "MaxContacts:" + optMaxContacts.Text + ",";

            // Set Options
            if (Options.Length > 0)
            {
                Options = Options.Substring(0, Options.Length - 1);
                RESTRequest += @"&opt=" + Options;
            }

            // *************************************************************************************
            // Set the Input Parameters
            // *************************************************************************************
            RESTRequest += @"&comp=" + Uri.EscapeDataString(txtCompanyIn.Text);
            RESTRequest += @"&a1=" + Uri.EscapeDataString(txtAddress1In.Text);
            RESTRequest += @"&a2=" + Uri.EscapeDataString(txtAddress2In.Text);

            RESTRequest += @"&city=" + Uri.EscapeDataString(txtCityIn.Text);
            RESTRequest += @"&state=" + Uri.EscapeDataString(txtStateIn.Text);
            RESTRequest += @"&postal=" + Uri.EscapeDataString(txtPostalCodeIn.Text);
            RESTRequest += @"&ctry=" + Uri.EscapeDataString(txtCountryIn.Text);
            RESTRequest += @"&ff=" + Uri.EscapeDataString(txtFreeFormIn.Text);

            RESTRequest += @"&phone=" + Uri.EscapeDataString(txtPhoneIn.Text);
            RESTRequest += @"&stock=" + Uri.EscapeDataString(txtStockTickerIn.Text);
            RESTRequest += @"&web=" + Uri.EscapeDataString(txtWebAddressIn.Text);
            RESTRequest += @"&mak=" + Uri.EscapeDataString(txtMAKIn.Text);
            RESTRequest += @"&mek=" + Uri.EscapeDataString(txtMEKIn.Text);

            // Set JSON Response Protocol
            RESTRequest += @"&format=json";

            // Build the final REST String Query
            RESTRequest = @"https://businesscoder.melissadata.net/WEB/BusinessCoder" + @"/doBusinessCoderUS?t=" + RESTRequest;

            // Output the REST Query
            txtRESTRequest.Text = RESTRequest;

            // *************************************************************************************
            // Submit to the Web Service. 
            // Make sure to set a retry block in case of any timeouts
            // *************************************************************************************
            Boolean Success = false;
            Int16 RetryCounter = 0;
            Stream ResponseReaderFile = null;
            do
            {
                try
                {
                    HttpWebRequest WebRequestObject = (HttpWebRequest)HttpWebRequest.Create(RESTRequest);
                    WebResponse Response = WebRequestObject.GetResponse();
                    ResponseReaderFile = Response.GetResponseStream();
                    Success = true;
                }
                catch (Exception ex)
                {
                    RetryCounter++;
                    MessageBox.Show("Exception: " + ex.Message);
                    return;
                }
            } while ((Success != true) && (RetryCounter < 5));

            // *************************************************************************************
            // Output Formatted JSON String
            // *************************************************************************************
            StreamReader Reader = new StreamReader(ResponseReaderFile, Encoding.UTF8);
            String ResponseString = Reader.ReadToEnd();

            txtResponse.Text = JValue.Parse(ResponseString).ToString(Newtonsoft.Json.Formatting.Indented);
        }

        // *************************************************************************************
        // Clear the Input Strings
        // *************************************************************************************
        private void btnClear_Click(object sender, EventArgs e)
        {
            // Address input fields
            txtAddress1In.Text = "";
            txtAddress2In.Text = "";
            txtCityIn.Text = "";
            txtStateIn.Text = "";
            txtPostalCodeIn.Text = "";
            txtCountryIn.Text = "";

            // Company input fields
            txtCompanyIn.Text = "";
            txtStockTickerIn.Text = "";
            txtMAKIn.Text = "";
            txtMEKIn.Text = "";
            txtWebAddressIn.Text = "";
            txtPhoneIn.Text = "";

            // Reset options to defaults
            optMaxContacts.Text = string.Empty;
            optCentricHint.Text = string.Empty;
            optReturnDominantBusiness.Text = string.Empty;
            optSICNAICSConfidence.Text = string.Empty;

            // Reset columns to defaults
            chkAllIndividualCols.Checked = false;
            for (int i = 0; i < colsIndividual.Items.Count; i++)
            {
                colsIndividual.SetItemChecked(i, false);
            }
            chkAllGroupCols.Checked = false;
            for (int i = 0; i < colsGroup.Items.Count; i++)
            {
                colsGroup.SetItemChecked(i, false);
            }

            // Clear outputs
            txtResponse.Text = string.Empty;
            txtRESTRequest.Text = string.Empty;
        }

        // *************************************************************************************
        // Wiki Link
        // *************************************************************************************
        private void lnkWiki_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
            Process.Start("http://wiki.melissadata.com/index.php?title=Business_Coder");
        }

        // *************************************************************************************
        // Check all Column Boxes
        // *************************************************************************************
        private void chkAllCols_CheckedChanged(object sender, EventArgs e)
        {
            for (int i = 0; i < colsIndividual.Items.Count; i++)
            {
                colsIndividual.SetItemChecked(i, chkAllIndividualCols.Checked);
            }
        }

        private void chkAllGroupCols_CheckedChanged(object sender, EventArgs e)
        {
            for (int i = 0; i < colsGroup.Items.Count; i++)
            {
                colsGroup.SetItemChecked(i, chkAllGroupCols.Checked);
            }
        }
    }
}
