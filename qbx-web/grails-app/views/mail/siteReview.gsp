<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; "/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

  <style type="text/css">
  /* /\/\/\/\/\/\/\/\/ CLIENT-SPECIFIC STYLES /\/\/\/\/\/\/\/\/ */
  #outlook a {
    padding: 0;
  }

  /* Force Outlook to provide a "view in browser" message */
  .ReadMsgBody {
    width: 100%;
  }

  .ExternalClass {
    width: 100%;
  }

  /* Force Hotmail to display emails at full width */
  .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {
    line-height: 100%;
  }

  /* Force Hotmail to display normal line spacing */
  body, table, td, p, a, li, blockquote {
    -webkit-text-size-adjust: 100%;
    -ms-text-size-adjust: 100%;
  }

  /* Prevent WebKit and Windows mobile changing default text sizes */
  table, td {
    mso-table-lspace: 0pt;
    mso-table-rspace: 0pt;
  }

  /* Remove spacing between tables in Outlook 2007 and up */
  img {
    -ms-interpolation-mode: bicubic;
  }

  /* Help Microsoft platforms smoothly render resized images */

  /* /\/\/\/\/\/\/\/\/ RESET STYLES /\/\/\/\/\/\/\/\/ */
  body, #bodyTable, #bodyCell {
    height: 100% !important;
    width: 100% !important;
    margin: 0;
    padding: 0;
  }

  table {
    border-collapse: collapse !important;
  }

  /* /\/\/\/\/\/\/\/\/ MOBILE STYLES /\/\/\/\/\/\/\/\/ */
  @media only screen and (max-width: 480px) {
    /* /\/\/\/\/\/\/ CLIENT-SPECIFIC STYLES /\/\/\/\/\/\/ */
    body {
      width: 100% !important;
      min-width: 100% !important;
    }

    /* Prevent iOS Mail from adding padding to the body */
    /* /\/\/\/\/\/\/ RESET STYLES /\/\/\/\/\/\/ */
    td[id="bodyCell"] {
      padding: 30px 0 !important;
    }

    table[id="emailContainer"] {
      max-width: 600px !important;
      width: 100% !important;
    }

    /* /\/\/\/\/\/\/ ELEMENT STYLES /\/\/\/\/\/\/ */
    h1 {
      font-size: 32px !important;
    }

    td[class="bodyContent"] {
      font-size: 18px !important;
    }

    table[class="emailButton"] {
      max-width: 480px !important;
      width: 100% !important;
    }

    td[class="emailButtonContent"] {
      font-size: 18px !important;
    }

    td[class="emailButtonContent"] a {
      display: block;
    }

    td[class="emailColumn"] {
      display: block !important;
      max-width: 600px !important;
      width: 100% !important;
    }

    td[class="footerContent"] {
      font-size: 15px !important;
      padding-right: 15px;
      padding-left: 15px;
    }
  }
  </style>
</head>

<body style="background-color:#F2F2F2;">
<center>
  <table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" id="bodyTable"
         style="background-color:#F2F2F2;">
    <tr>
      <td align="center" valign="top" id="bodyCell" style="padding:40px 20px;">
        <table border="0" cellpadding="0" cellspacing="0" id="emailContainer" style="width:600px;">
          <tr>
            <td align="center" valign="top" style="padding-top:30px; padding-bottom:30px;">
              <table border="0" cellpadding="0" cellspacing="0" width="100%" id="emailBody"
                     style="background-color:#FFFFFF; border-collapse:separate !important; border-radius:4px;">
                <tr>
                  <td align="center" valign="top" class="bodyContent"
                      style="color:#606060; font-family:Helvetica, Arial, sans-serif; font-size:15px; line-height:150%; padding-top:40px; padding-right:40px; padding-bottom:30px; padding-left:40px; text-align:center;">

                    <h3 style="color:#606060 !important; font-family:Helvetica, Arial, sans-serif; font-size:18px; letter-spacing:-.5px; line-height:115%; margin:0; padding:0; text-align:center;">
                      <g:message code="${titleCode}"/>
                    </h3>
                    <br/>
                    <table>
                      <tr>
                        <td><g:message code="site.review.name"/> :</td>
                        <td>${siteName}</td>
                      </tr>
                      <tr>
                        <td><g:message code="site.review.email"/> :</td>
                        <td>${email}</td>
                      </tr>
                      <tr>
                        <td><g:message code="site.review.phone"/> :</td>
                        <td>${phone}</td>
                      </tr>
                      <tr>
                        <td><g:message code="site.review.billingAddress"/> :</td>
                        <td>${billingAddress}</td>
                      </tr>
                      <tr>
                        <td><g:message code="site.review.visitAddress"/> :</td>
                        <td>${visitAddress}</td>
                      </tr>
                      <tr>
                        <td><g:message code="site.review.paymentMethod"/> :</td>
                        <td>${paymentMethod}</td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  Site
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</center>
</body>
</html>


</body>
</html>