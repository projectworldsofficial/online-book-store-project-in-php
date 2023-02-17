import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;

public class Registration_jsp extends HttpJspBase {


//
//   Filename: Common.jsp
//   Generated with CodeCharge  v.1.2.0
//   JSP.ccp build 05/21/2001
//

  static final String CRLF = "\r\n";

  static final int UNDEFINT=Integer.MIN_VALUE;

  static final int adText = 1;
  static final int adDate = 2;
  static final int adNumber = 3;
  static final int adSearch_ = 4;
  static final int ad_Search_ = 5;
  static final String appPath   ="/";

//Database connection string

  static final String DBDriver  ="";
  static final String strConn   ="";
  static final String DBusername="";
  static final String DBpassword="";

  public static String loadDriver () {
    String sErr = "";
    try {
      java.sql.DriverManager.registerDriver((java.sql.Driver)(Class.forName(DBDriver).newInstance()));
    }
    catch (Exception e) {
      sErr = e.toString();
    }
    return (sErr);
  }

  public static void absolute(java.sql.ResultSet rs, int row) throws java.sql.SQLException{
    for(int x=1;x<row;x++) rs.next();
  }

  java.sql.ResultSet openrs(java.sql.Statement stat, String sql) throws java.sql.SQLException {
    java.sql.ResultSet rs = stat.executeQuery(sql);
    return (rs);
  }

  String dLookUp(java.sql.Statement stat, String table, String fName, String where) {
    java.sql.Connection conn1 = null;
    java.sql.Statement stat1 = null;
    try {
      conn1 = cn();
      stat1 = conn1.createStatement();
      java.sql.ResultSet rsLookUp = openrs( stat1, "SELECT " + fName + " FROM " + table + " WHERE " + where);
      if (! rsLookUp.next()) {
        rsLookUp.close();
        stat1.close();
        conn1.close();
        return "";
      }
      String res = rsLookUp.getString(1);
      rsLookUp.close();
      stat1.close();
      conn1.close();
      return (res == null ? "" : res);
    }
    catch (Exception e) {
      return "";
    }
  }

  long dCountRec(java.sql.Statement stat, String table, String sWhere) {
    long lNumRecs = 0;
    try {
      java.sql.ResultSet rs = stat.executeQuery("select count(*) from " + table + " where " + sWhere);
      if ( rs != null && rs.next() ) {
        lNumRecs = rs.getLong(1);
      }
      rs.close();
    }
    catch (Exception e ) {};
    return lNumRecs;
  }

  String proceedError(javax.servlet.http.HttpServletResponse response, Exception e) {
    return e.toString();
  }

  String[] getFieldsName ( java.sql.ResultSet rs ) throws java.sql.SQLException {
    java.sql.ResultSetMetaData metaData = rs.getMetaData();
    int count = metaData.getColumnCount();
    String[] aFields = new String[count];
    for(int j = 0; j < count; j++) {
      aFields[j] = metaData.getColumnLabel(j+1);
    }
    return aFields;
  }

  java.util.Hashtable getRecordToHash ( java.sql.ResultSet rs, java.util.Hashtable rsHash, String[] aFields ) throws java.sql.SQLException {
    for ( int iF = 0; iF < aFields.length; iF++ ) {
      rsHash.put( aFields[iF], getValue(rs, aFields[iF]));
    }
    return rsHash;
  }

  java.sql.Connection cn() throws java.sql.SQLException {
    return java.sql.DriverManager.getConnection(strConn , DBusername, DBpassword);
  }

  String toURL(String strValue){
    if ( strValue == null ) return "";
    if ( strValue.compareTo("") == 0 ) return "";
    return java.net.URLEncoder.encode(strValue);
  }

  String toHTML(String value) {
    if ( value == null ) return "";
    value = replace(value, "&", "&amp;");
    value = replace(value, "<", "&lt;");
    value = replace(value, ">", "&gt;");
    value = replace(value, "\"", "&" + "quot;");
    return value;
  }

  String getValueHTML(java.sql.ResultSet rs, String fieldName) {
    try {
      String value = rs.getString(fieldName);
      if (value != null) {
        return toHTML(value);
      }
    }
    catch (java.sql.SQLException sqle) {}
    return "";
  }

  String getValue(java.sql.ResultSet rs, String strFieldName) {
    if ((rs==null) ||(isEmpty(strFieldName)) || ("".equals(strFieldName))) return "";
    try {
      String sValue = rs.getString(strFieldName);
      if ( sValue == null ) sValue = "";
      return sValue;
    }
    catch (Exception e) {
      return "";
    }
  }
  
  String getParam(javax.servlet.http.HttpServletRequest req, String paramName) {
    String param = req.getParameter(paramName);
    if ( param == null || param.equals("") ) return "";
    param = replace(param,"&amp;","&");
    param = replace(param,"&lt;","<");
    param = replace(param,"&gt;",">");
    param = replace(param,"&amp;lt;","<");
    param = replace(param,"&amp;gt;",">");
    return param;
  }

  boolean isNumber (String param) {
    boolean result;
    if ( param == null || param.equals("")) return true;
    param=param.replace('d','_').replace('f','_');
    try {
      Double dbl = new Double(param);
      result = true;
    }
    catch (NumberFormatException nfe) {
      result = false;
    }
    return result;
  }

  boolean isEmpty (int val){
    return val==UNDEFINT;
  }

  boolean isEmpty (String val){
    return (val==null || val.equals("")||val.equals(Integer.toString(UNDEFINT))); 
  }

  String getCheckBoxValue (String val, String checkVal, String uncheckVal, int ctype) {
    if (val==null || val.equals("") ) return toSQL(uncheckVal, ctype);
    else return toSQL(checkVal, ctype);
  }

  String toWhereSQL(String fieldName, String fieldVal, int type) {
    String res = "";
    switch(type) {
      case adText: 
        if (! "".equals(fieldVal)) {
          res = " " + fieldName + " like '%" + fieldVal + "%'";
        }
      case adNumber:
        res = " " + fieldName + " = " + fieldVal + " ";
      case adDate:
        res = " " + fieldName + " = '" + fieldVal + "' ";
      default:
        res = " " + fieldName + " = '" + fieldVal + "' ";
    }
    return res;
  }

  String toSQL(String value, int type) {
    if ( value == null ) return "Null";
    String param = value;
    if ("".equals(param) && (type == adText || type == adDate) ) {
      return "Null";
    } 
    switch (type) {
      case adText: {
        param = replace(param, "'", "''");
        param = replace(param, "&amp;", "&");
        param = "'" + param + "'";
        break;
      }
      case adSearch_:
      case ad_Search_: {
        param = replace(param, "'", "''");
        break;
      }
      case adNumber: {
        try {
          if (! isNumber(value) || "".equals(param)) param="null";
          else param = value;
        }
        catch (NumberFormatException nfe) {
          param = "null";
        }
        break;
      }
      case adDate: {
        param = "'" + param + "'";
        break;      
      }
    }
    return param;
  }

  private String replace(String str, String pattern, String replace) {
    if (replace == null) {
      replace = "";
    }
    int s = 0, e = 0;
    StringBuffer result = new StringBuffer((int) str.length()*2);
    while ((e = str.indexOf(pattern, s)) >= 0) {
      result.append(str.substring(s, e));
      result.append(replace);
      s = e + pattern.length();
    }
    result.append(str.substring(s));
    return result.toString();
  }

  String getOptions( java.sql.Connection conn, String sql, boolean isSearch, boolean isRequired, String selectedValue ) {

    String sOptions = "";
    String sSel = "";

    if ( isSearch ) {
     sOptions += "<option value=\"\">All</option>";
    }
    else {
      if ( ! isRequired ) {
       sOptions += "<option value=\"\"></option>";
      }
    }
    try {
      java.sql.Statement stat = conn.createStatement();
      java.sql.ResultSet rs = null;
      rs = openrs (stat, sql);
      while (rs.next() ) {
        String id = toHTML( rs.getString(1) );
        String val = toHTML( rs.getString(2) );
        if ( id.compareTo(selectedValue) == 0 ) {
          sSel = "SELECTED";
        }
        else  {
          sSel = "";
        }
        sOptions += "<option value=\""+id+"\" "+sSel+">"+val+"</option>";
      }
      rs.close();
      stat.close();
    }
    catch (Exception e) {}
    return sOptions;
  }

  String getOptionsLOV( String sLOV, boolean isSearch, boolean isRequired, String selectedValue ) {
    String sSel = "";
    String slOptions = "";
    String sOptions = "";
    String id = "";
    String val = "";
    java.util.StringTokenizer LOV = new java.util.StringTokenizer( sLOV, ";", true);
    int i = 0;
    String old = ";";
    while ( LOV.hasMoreTokens() ) {
      id = LOV.nextToken();
      if ( ! old.equals(";") && ( id.equals(";") ) ) {
        id = LOV.nextToken();
      }
      else {
        if ( old.equals(";") && ( id.equals(";") ) ) {
          id = "";
        }
      }
      if ( ! id.equals("") )  { old = id; }

      i++;

      if (LOV.hasMoreTokens()) {
        val = LOV.nextToken();
        if ( ! old.equals(";") && (val.equals(";") ) ) {
          val = LOV.nextToken();
        }
        else {
          if (old.equals(";") && (val.equals(";"))) {
            val = "";
          }
        }
        if ( val.equals(";") ) { val = ""; }
        if ( ! val.equals("")) { old = val; }
        i++;
      }

      if ( id.compareTo( selectedValue ) == 0 ) {
        sSel = "SELECTED";
      }
      else {
        sSel = "";
      }
      slOptions += "<option value=\""+id+"\" "+sSel+">"+val+"</option>";
    }
    if (  ( i % 2 ) == 0 ) sOptions += slOptions;
    return sOptions;
  }

  String getValFromLOV( String selectedValue , String sLOV) {
    String sRes = "";
    String id = "";
    String val = "";
    java.util.StringTokenizer LOV = new java.util.StringTokenizer( sLOV, ";", true);
    int i = 0;
    String old = ";";
    while ( LOV.hasMoreTokens() ) {
      id = LOV.nextToken();
      if ( ! old.equals(";") && ( id.equals(";") ) ) {
        id = LOV.nextToken();
      }
      else {
        if ( old.equals(";") && ( id.equals(";") ) ) {
          id = "";
        }
      }
      if ( ! id.equals("") )  { old = id; }

      i++;

      if (LOV.hasMoreTokens()) {
        val = LOV.nextToken();
        if ( ! old.equals(";") && (val.equals(";") ) ) {
          val = LOV.nextToken();
        }
        else {
          if (old.equals(";") && (val.equals(";"))) {
            val = "";
          }
        }
        if ( val.equals(";") ) { val = ""; }
        if ( ! val.equals("")) { old = val; }
        i++;
      }

      if ( id.compareTo( selectedValue ) == 0 ) {
        sRes = val;
      }
    }
    return sRes;
  }


  String checkSecurity(int iLevel, javax.servlet.http.HttpSession session, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpServletRequest request){
    try {
      Object o1 = session.getAttribute("UserID");
      Object o2 = session.getAttribute("UserRights");
      boolean bRedirect = false;
      if ( o1 == null || o2 == null ) { bRedirect = true; }
      if ( ! bRedirect ) {
        if ( (o1.toString()).equals("")) { bRedirect = true; }
        else if ( (new Integer(o2.toString())).intValue() < iLevel) { bRedirect = true; }
      }

      if ( bRedirect ) {
        response.sendRedirect("Login.jsp?querystring=" + toURL(request.getQueryString()) + "&ret_page=" + toURL(request.getRequestURI()));
        return "sendRedirect";
      }
    }
    catch(Exception e){};
    return "";
  }



//
//   Filename: Registration.jsp
//   Generated with CodeCharge  v.1.2.0
//   JSP.ccp build 05/21/2001
//

static final String sFileName = "Registration.jsp";
              




  String RegAction(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sAction, String sForm, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
  
    String sRegErr ="";
    try {

      if (sAction.equals("")) return "";

      String sSQL="";
      String transitParams = "";
      String primaryKeyParams = "";
      String sQueryString = "";
      String sPage = "";
      String sParams = "";
      String sActionFileName = "Default.jsp";
      String sWhere = " ";
      boolean bErr = false;
      long iCount = 0;

  
      String pPKmember_id = "";
      if (sAction.equalsIgnoreCase("cancel") ) {
        try {
          if ( stat != null ) stat.close();
          if ( conn != null ) conn.close();
        }
        catch ( java.sql.SQLException ignore ) {}
        response.sendRedirect (sActionFileName);
        return "sendRedirect";
      }

      final int iinsertAction = 1;
      final int iupdateAction = 2;
      final int ideleteAction = 3;
      int iAction = 0;

      if ( sAction.equalsIgnoreCase("insert") ) { iAction = iinsertAction; }
      if ( sAction.equalsIgnoreCase("update") ) { iAction = iupdateAction; }
      if ( sAction.equalsIgnoreCase("delete") ) { iAction = ideleteAction; }

      // Create WHERE statement


      String fldmember_login="";
      String fldmember_password="";
      String fldfirst_name="";
      String fldlast_name="";
      String fldemail="";
      String fldaddress="";
      String fldphone="";
      String fldcard_type_id="";
      String fldcard_number="";
      String fldmember_password2="";
      String fldmember_id="";

      // Load all form fields into variables
    
      fldmember_login = getParam(request, "member_login");
      fldmember_password = getParam(request, "member_password");
      fldmember_password2 = getParam(request, "member_password2");
      fldfirst_name = getParam(request, "first_name");
      fldlast_name = getParam(request, "last_name");
      fldemail = getParam(request, "email");
      fldaddress = getParam(request, "address");
      fldphone = getParam(request, "phone");
      fldcard_type_id = getParam(request, "card_type_id");
      fldcard_number = getParam(request, "card_number");
      // Validate fields
      if ( iAction == iinsertAction || iAction == iupdateAction ) {
        if ( isEmpty(fldmember_login) ) {
          sRegErr = sRegErr + "The value in field Login* is required.<br>";
        }
        if ( isEmpty(fldmember_password) ) {
          sRegErr = sRegErr + "The value in field Password* is required.<br>";
        }
        if ( isEmpty(fldmember_password2) ) {
          sRegErr = sRegErr + "The value in field Confirm Password* is required.<br>";
        }
        if ( isEmpty(fldfirst_name) ) {
          sRegErr = sRegErr + "The value in field First Name* is required.<br>";
        }
        if ( isEmpty(fldlast_name) ) {
          sRegErr = sRegErr + "The value in field Last Name* is required.<br>";
        }
        if ( isEmpty(fldemail) ) {
          sRegErr = sRegErr + "The value in field Email* is required.<br>";
        }
        if ( ! isNumber(fldcard_type_id)) {
          sRegErr = sRegErr + "The value in field Credit Card Type is incorrect.<br>";
        }
        if ( ! isEmpty(fldmember_login)) {
          iCount = 0;
          if ( iAction == iinsertAction ) {
            iCount = dCountRec(stat, "members", "member_login=" + toSQL(fldmember_login, adText));
          }
          else {
            if ( iAction == iupdateAction ) {
              iCount = dCountRec( stat, "members", "member_login=" + toSQL(fldmember_login, adText) + " and not(" + sWhere + ")");
            }
          }
          if (iCount > 0) {
            sRegErr = sRegErr + "The value in field Login* is already in database.<br>";
          }
        }
if ( ! getParam( request, "member_password" ).equals(getParam( request,"member_password2"))) {
  sRegErr += "\nPassword and Confirm Password fields don't match";
}
        if (sRegErr.length() > 0 ) {
          return (sRegErr);
        }
      }


      sSQL = "";
      // Create SQL statement

      switch (iAction) {
  
        case iinsertAction :
          
            sSQL = "insert into members (" + 
                "member_login," +
                "member_password," +
                "first_name," +
                "last_name," +
                "email," +
                "address," +
                "phone," +
                "card_type_id," +
                "card_number)" +

                " values (" + 
                toSQL(fldmember_login, adText) + "," +
                toSQL(fldmember_password, adText) + "," +
                toSQL(fldfirst_name, adText) + "," +
                toSQL(fldlast_name, adText) + "," +
                toSQL(fldemail, adText) + "," +
                toSQL(fldaddress, adText) + "," +
                toSQL(fldphone, adText) + "," +
                toSQL(fldcard_type_id, adNumber) + "," +
                toSQL(fldcard_number, adText) + ")";
          break;
  
      }

      if ( sRegErr.length() > 0 ) return sRegErr;
      try {
        // Execute SQL statement
        stat.executeUpdate(sSQL);
      }
      catch(java.sql.SQLException e) {
        sRegErr = e.toString(); return (sRegErr);
      }
  
      try {
        if ( stat != null ) stat.close();
        if ( conn != null ) conn.close();
      }
      catch ( java.sql.SQLException ignore ) {}
      response.sendRedirect (sActionFileName);

      return "sendRedirect";
    }
    catch (Exception e) {out.println(e.toString()); }
    return (sRegErr);
  }

  


  void Reg_Show(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sRegErr, String sForm, String sAction, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
    try {

      String sSQL="";
      String sQueryString = "";
      String sPage = "";
      String sWhere = "";
      String transitParams = "";
      String transitParamsHidden = "";
      String requiredParams = "";
      String primaryKeyParams ="";
      java.util.Hashtable rsHash = new java.util.Hashtable();
      
      String pmember_id = "";

      String fldmember_id="";
      String fldmember_login="";
      String fldmember_password="";
      String fldfirst_name="";
      String fldlast_name="";
      String fldemail="";
      String fldaddress="";
      String fldphone="";
      String fldcard_type_id="";
      String fldcard_number="";
      String fldmember_password2="";


      boolean bPK = true;

      if ( "".equals(sRegErr)) {
        // Load primary key and form parameters
        pmember_id = getParam( request, "member_id");
      }
      else {
        // Load primary key, form parameters and form fields
        fldmember_id = getParam( request, "member_id");
        fldmember_login = getParam( request, "member_login");
        fldmember_password = getParam( request, "member_password");
        fldfirst_name = getParam( request, "first_name");
        fldlast_name = getParam( request, "last_name");
        fldemail = getParam( request, "email");
        fldaddress = getParam( request, "address");
        fldphone = getParam( request, "phone");
        fldcard_type_id = getParam( request, "card_type_id");
        fldcard_number = getParam( request, "card_number");
        pmember_id = getParam( request, "PK_member_id");
      }

      
      fldmember_password2 = getParam(request,"member_password2");
      if ( isEmpty(pmember_id)) { bPK = false; }
      
      sWhere += "member_id=" + toSQL(pmember_id, adNumber);
      primaryKeyParams += "<input type=\"hidden\" name=\"PK_member_id\" value=\""+pmember_id+"\"/>";

      sSQL = "select * from members where " + sWhere;


      out.println("    <table style=\"\">");
      out.println("     <tr>\n      <td style=\"background-color: #336699; text-align: Center; border-style: outset; border-width: 1\" colspan=\"2\"><font style=\"font-size: 12pt; color: #FFFFFF; font-weight: bold\">Registration</font></td>\n     </tr>");
      if ( ! sRegErr.equals("")) {
        out.println("     <tr>\n      <td style=\"background-color: #FFFFFF; border-width: 1\" colspan=\"2\"><font style=\"font-size: 10pt; color: #000000\">"+sRegErr+"</font></td>\n     </tr>");
      }
      sRegErr="";
      out.println("     <form method=\"get\" action=\""+sFileName+"\" name=\"Reg\">");

      java.sql.ResultSet rs = null;

      if ( bPK &&  ! (sAction.equals("insert") && "Reg".equals(sForm))) {

        // Open recordset
        rs = openrs( stat, sSQL);
        rs.next();
        String[] aFields = getFieldsName( rs );
        getRecordToHash( rs, rsHash, aFields );
        rs.close();
        fldmember_id = (String) rsHash.get("member_id");
        if ( "".equals(sRegErr)) {
          // Load data from recordset when form displayed first time
          fldmember_login = (String) rsHash.get("member_login");
          fldmember_password = (String) rsHash.get("member_password");
          fldfirst_name = (String) rsHash.get("first_name");
          fldlast_name = (String) rsHash.get("last_name");
          fldemail = (String) rsHash.get("email");
          fldaddress = (String) rsHash.get("address");
          fldphone = (String) rsHash.get("phone");
          fldcard_type_id = (String) rsHash.get("card_type_id");
          fldcard_number = (String) rsHash.get("card_number");
        }

        if (sAction.equals("") || ! "Reg".equals(sForm)) {
      
          fldmember_id = (String) rsHash.get("member_id");
          fldmember_login = (String) rsHash.get("member_login");
          fldmember_password = (String) rsHash.get("member_password");
          fldfirst_name = (String) rsHash.get("first_name");
          fldlast_name = (String) rsHash.get("last_name");
          fldemail = (String) rsHash.get("email");
          fldaddress = (String) rsHash.get("address");
          fldphone = (String) rsHash.get("phone");
          fldcard_type_id = (String) rsHash.get("card_type_id");
          fldcard_number = (String) rsHash.get("card_number");
        }
        
      }
      else {
      }
      


      // Show form field
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Login*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"member_login\" maxlength=\"20\" value=\""+toHTML(fldmember_login)+"\" size=\"20\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Password*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"password\"  name=\"member_password\" maxlength=\"20\" value=\""+toHTML(fldmember_password)+"\" size=\"20\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Confirm Password*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"password\"  name=\"member_password2\" maxlength=\"20\" value=\""+toHTML(fldmember_password2)+"\" size=\"20\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">First Name*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"first_name\" maxlength=\"50\" value=\""+toHTML(fldfirst_name)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Last Name*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"last_name\" maxlength=\"50\" value=\""+toHTML(fldlast_name)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Email*</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"email\" maxlength=\"50\" value=\""+toHTML(fldemail)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Address</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"address\" maxlength=\"50\" value=\""+toHTML(fldaddress)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Phone</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"phone\" maxlength=\"50\" value=\""+toHTML(fldphone)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Credit Card Type</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); 
      out.print("<select name=\"card_type_id\">"+getOptions( conn, "select card_type_id, name from card_types order by 2",false,false,fldcard_type_id)+"</select>");
      
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Credit Card Number</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"card_number\" maxlength=\"20\" value=\""+toHTML(fldcard_number)+"\" size=\"20\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td colspan=\"2\" align=\"right\">");
      

      if ( bPK && ! (sAction.equals("insert") && "Reg".equals(sForm))) {
        out.print("<input type=\"submit\" value=\"Cancel\" onclick=\"document.Reg.FormAction.value = 'cancel';\">");
        out.print("<input type=\"hidden\" name=\"FormName\" value=\"Reg\"><input type=\"hidden\" value=\"\" name=\"FormAction\">");
      }
      
      else {
        out.print("<input type=\"submit\" value=\"Register\" onclick=\"document.Reg.FormAction.value = 'insert';\">");out.print("<input type=\"submit\" value=\"Cancel\" onclick=\"document.Reg.FormAction.value = 'cancel';\">");
        out.print("<input type=\"hidden\" name=\"FormName\" value=\"Reg\"><input type=\"hidden\" value=\"insert\" name=\"FormAction\">");
      }out.print("<input type=\"hidden\" name=\"member_id\" value=\""+toHTML(fldmember_id)+"\">");
      out.print(transitParamsHidden+requiredParams+primaryKeyParams);
      out.println("</td>\n     </tr>\n     </form>\n    </table>");
      



    }
    catch (Exception e) { out.println(e.toString()); }
  } 

  private static java.util.Vector _jspx_includes;

  static {
    _jspx_includes = new java.util.Vector(1);
    _jspx_includes.add("/Common.jsp");
  }

  public java.util.List getIncludes() {
    return _jspx_includes;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    javax.servlet.jsp.PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;



boolean bDebug = false;

String sAction = getParam( request, "FormAction");
String sForm = getParam( request, "FormName");
String sRegErr = "";

java.sql.Connection conn = null;
java.sql.Statement stat = null;
String sErr = loadDriver();
conn = cn();
stat = conn.createStatement();
if ( ! sErr.equals("") ) {
 try {
   out.println(sErr);
 }
 catch (Exception e) {}
}
if ( sForm.equals("Reg") ) {
  sRegErr = RegAction(request, response, session, out, sAction, sForm, conn, stat);
  if ( "sendRedirect".equals(sRegErr)) return;
}


      out.write("            \r\n<html>\r\n<head>\r\n<title>Book Store</title>\r\n<meta name=\"GENERATOR\" content=\"YesSoftware CodeCharge v.1.2.0 / JSP.ccp build 05/21/2001\"/>\r\n<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n<meta http-equiv=\"expires\" content=\"0\"/>\r\n<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n</head>\r\n<body style=\"background-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helveticabackground-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helvetica\">\r\n");
                                                                        JspRuntimeLibrary.include(request, response, "Header.jsp", out, true);
      out.write("<center>\r\n <table>\r\n  <tr>\r\n   \r\n   <td valign=\"top\">\r\n");
                   Reg_Show(request, response, session, out, sRegErr, sForm, sAction, conn, stat); 
      out.write("\r\n    \r\n   </td>\r\n  </tr>\r\n </table>\r\n\r\n");
                        JspRuntimeLibrary.include(request, response, "Footer.jsp", out, true);
      out.write("\r\n<center><font face=\"Arial\"><small>This dynamic site was generated with <a href=\"http://www.codecharge.com\">CodeCharge</a></small></font></center>\r\n</body>\r\n</html>\r\n");
                                                            
      out.write("\r\n");

if ( stat != null ) stat.close();
if ( conn != null ) conn.close();

      out.write("\r\n");
    } catch (Throwable t) {
      out = _jspx_out;
      if (out != null && out.getBufferSize() != 0)
        out.clearBuffer();
      if (pageContext != null) pageContext.handlePageException(t);
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(pageContext);
    }
  }
}
