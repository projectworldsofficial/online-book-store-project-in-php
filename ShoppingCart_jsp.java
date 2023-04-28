import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;

public class ShoppingCart_jsp extends HttpJspBase {


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
//   Filename: ShoppingCart.jsp
//   Generated with CodeCharge  v.1.2.0
//   JSP.ccp build 05/21/2001
//

static final String sFileName = "ShoppingCart.jsp";
              



  void Items_Show (javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sItemsErr, String sForm, String sAction, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException  {
  
    String sWhere = "";
    int iCounter=0;
    int iPage = 0;
    boolean bIsScroll = true;
    boolean hasParam = false;
    String sOrder = "";
    String sSQL="";
    String transitParams = "";
    String sQueryString = "";
    String sPage = "";
    int RecordsPerPage = 20;
    String sSortParams = "";
    String formParams = "";

      String pUserID="";

boolean bReq = true; 
    // Build WHERE statement
        
    //-- Check UserID parameter and create a valid sql for where clause
  
    pUserID = (String)session.getAttribute("UserID");
    if ( ! isNumber (pUserID)) {
      pUserID = "";
    }
    
    if (pUserID != null && ! pUserID.equals("")) {
            
        hasParam = true;
        sWhere += "member_id=" + pUserID;
    }
    
    else bReq = false;
    if (hasParam) { sWhere = " AND (" + sWhere + ")"; }

  // Build full SQL statement
  
  sSQL = "SELECT order_id, name, price, quantity, member_id, quantity*price as sub_total FROM items, orders WHERE orders.item_id=items.item_id" + sWhere + " ORDER BY order_id";
  

  String sNoRecords = "     <tr>\n      <td colspan=\"6\" style=\"background-color: #FFFFFF; border-width: 1\"><font style=\"font-size: 10pt; color: #000000\">No records</font></td>\n     </tr>";


  String tableHeader = "";
    tableHeader = "     <tr>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Details</font></td>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Order #</font></td>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Item</font></td>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Price</font></td>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Quantity</font></td>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Total</font></td>\n     </tr>";
  
  
  try {
    out.println("    <table style=\"\">");
    out.println("     <tr>\n      <td style=\"background-color: #336699; text-align: Center; border-style: outset; border-width: 1\" colspan=\"6\"><a name=\"Items\"><font style=\"font-size: 12pt; color: #FFFFFF; font-weight: bold\">Items</font></a></td>\n     </tr>");
    out.println(tableHeader);

    if ( ! bReq ) {
      out.println(sNoRecords);
      out.println("    </table>");
      return;
    }

  }
  catch (Exception e) {}

  
  try {
    java.sql.ResultSet rs = null;
    // Open recordset
    rs = openrs( stat, sSQL);
    iCounter = 0;
    
    java.util.Hashtable rsHash = new java.util.Hashtable();
    String[] aFields = getFieldsName( rs );

    // Show main table based on recordset
    while ( rs.next() ) {

      getRecordToHash( rs, rsHash, aFields );
      String flditem_id = (String) rsHash.get("name");
      String fldorder_id = (String) rsHash.get("order_id");
      String fldprice = (String) rsHash.get("price");
      String fldquantity = (String) rsHash.get("quantity");
      String fldsub_total = (String) rsHash.get("sub_total");
      String fldField1= "Details";

      out.println("     <tr>");
      
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<a href=\"ShoppingCartRecord.jsp?"+transitParams+"order_id="+toURL((String) rsHash.get("order_id"))+"&\"><font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldField1)+"</font></a>");

      out.println("</td>");
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldorder_id)+"&nbsp;</font>");
      out.println("</td>");
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(flditem_id)+"&nbsp;</font>");
      out.println("</td>");
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldprice)+"&nbsp;</font>");
      out.println("</td>");
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldquantity)+"&nbsp;</font>");
      out.println("</td>");
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldsub_total)+"&nbsp;</font>");
      out.println("</td>");
      out.println("     </tr>");
    
      iCounter++;
    }
    if (iCounter == 0) {
      // Recordset is empty
      out.println(sNoRecords);
    
      iCounter = RecordsPerPage+1;
      bIsScroll = false;
    }

    if ( rs != null ) rs.close();
    out.println("    </table>");
    
  }
  catch (Exception e) { out.println(e.toString()); }
}


  void Total_Show (javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sTotalErr, String sForm, String sAction, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException  {
  
    String sWhere = "";
    int iCounter=0;
    int iPage = 0;
    boolean bIsScroll = true;
    boolean hasParam = false;
    String sOrder = "";
    String sSQL="";
    String transitParams = "";
    String sQueryString = "";
    String sPage = "";
    int RecordsPerPage = 20;
    String sSortParams = "";
    String formParams = "";

      String pUserID="";

boolean bReq = true; 
    // Build WHERE statement
        
    //-- Check UserID parameter and create a valid sql for where clause
  
    pUserID = (String)session.getAttribute("UserID");
    if ( ! isNumber (pUserID)) {
      pUserID = "";
    }
    
    if (pUserID != null && ! pUserID.equals("")) {
            
        hasParam = true;
        sWhere += "member_id=" + pUserID;
    }
    
    else bReq = false;
    if (hasParam) { sWhere = " AND (" + sWhere + ")"; }

  // Build full SQL statement
  
  sSQL = "SELECT member_id, sum(quantity*price) as sub_total FROM items, orders WHERE orders.item_id=items.item_id" + sWhere + " GROUP BY member_id";
  

  String sNoRecords = "     <tr>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-width: 1\"><font style=\"font-size: 10pt; color: #000000\">No records</font></td>\n     </tr>";


  String tableHeader = "";
    tableHeader = "     <tr>\n      <td colspan=\"1\" style=\"background-color: #FFFFFF; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #CE7E00; font-weight: bold\">Total</font></td>\n     </tr>";
  
  
  try {
    out.println("    <table style=\"\">");
    
    out.println(tableHeader);

    if ( ! bReq ) {
      out.println(sNoRecords);
      out.println("    </table>");
      return;
    }

  }
  catch (Exception e) {}

  
  try {
    java.sql.ResultSet rs = null;
    // Open recordset
    rs = openrs( stat, sSQL);
    iCounter = 0;
    
    java.util.Hashtable rsHash = new java.util.Hashtable();
    String[] aFields = getFieldsName( rs );

    // Show main table based on recordset
    while ( rs.next() ) {

      getRecordToHash( rs, rsHash, aFields );
      String fldsub_total = (String) rsHash.get("sub_total");

      out.println("     <tr>");
      
      out.print("      <td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldsub_total)+"&nbsp;</font>");
      out.println("</td>");
      out.println("     </tr>");
    
      iCounter++;
    }
    if (iCounter == 0) {
      // Recordset is empty
      out.println(sNoRecords);
    
      iCounter = RecordsPerPage+1;
      bIsScroll = false;
    }

    if ( rs != null ) rs.close();
    out.println("    </table>");
    
  }
  catch (Exception e) { out.println(e.toString()); }
}



  String MemberAction(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sAction, String sForm, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
  
    String sMemberErr ="";
    try {

      if (sAction.equals("")) return "";

      String sSQL="";
      String transitParams = "";
      String primaryKeyParams = "";
      String sQueryString = "";
      String sPage = "";
      String sParams = "";
      String sActionFileName = "AdminMenu.jsp";
      String sWhere = " ";
      boolean bErr = false;
      long iCount = 0;

  
      sParams = "?";
      sParams += "UserID=" + toURL(getParam( request, "Trn_UserID"));
      String pPKmember_id = "";

      final int iinsertAction = 1;
      final int iupdateAction = 2;
      final int ideleteAction = 3;
      int iAction = 0;

      if ( sAction.equalsIgnoreCase("insert") ) { iAction = iinsertAction; }
      if ( sAction.equalsIgnoreCase("update") ) { iAction = iupdateAction; }
      if ( sAction.equalsIgnoreCase("delete") ) { iAction = ideleteAction; }

      // Create WHERE statement


      String fldUserID="";
      String fldmember_id="";

      // Load all form fields into variables
    
      fldUserID = (String) session.getAttribute("UserID");

      sSQL = "";
      // Create SQL statement

      if ( sMemberErr.length() > 0 ) return sMemberErr;
      try {
        // Execute SQL statement
        stat.executeUpdate(sSQL);
      }
      catch(java.sql.SQLException e) {
        sMemberErr = e.toString(); return (sMemberErr);
      }
  
      try {
        if ( stat != null ) stat.close();
        if ( conn != null ) conn.close();
      }
      catch ( java.sql.SQLException ignore ) {}
      response.sendRedirect (sActionFileName + sParams);

      return "sendRedirect";
    }
    catch (Exception e) {out.println(e.toString()); }
    return (sMemberErr);
  }

  


  void Member_Show(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sMemberErr, String sForm, String sAction, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
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
      

      String fldUserID="";
      String fldmember_id="";
      String fldmember_login="";
      String fldname="";
      String fldlast_name="";
      String fldaddress="";
      String fldemail="";
      String fldphone="";


      boolean bPK = true;

      if ( "".equals(sMemberErr)) {
        // Load primary key and form parameters
      }
      else {
        // Load primary key, form parameters and form fields
        fldmember_id = getParam( request, "member_id");
      }

      
      String pmember_id = (String) session.getAttribute("UserID");
      if ( isEmpty(pmember_id)) { bPK = false; }
      
      sWhere += "member_id=" + toSQL(pmember_id, adNumber);
      primaryKeyParams += "<input type=\"hidden\" name=\"PK_member_id\" value=\""+pmember_id+"\"/>";

      sSQL = "select * from members where " + sWhere;


      out.println("    <table style=\"\">");
      out.println("     <tr>\n      <td style=\"background-color: #336699; text-align: Center; border-style: outset; border-width: 1\" colspan=\"2\"><font style=\"font-size: 12pt; color: #FFFFFF; font-weight: bold\">User Information</font></td>\n     </tr>");
      if ( ! sMemberErr.equals("")) {
        out.println("     <tr>\n      <td style=\"background-color: #FFFFFF; border-width: 1\" colspan=\"2\"><font style=\"font-size: 10pt; color: #000000\">"+sMemberErr+"</font></td>\n     </tr>");
      }
      sMemberErr="";
      out.println("     <form method=\"get\" action=\""+sFileName+"\" name=\"Member\">");

      java.sql.ResultSet rs = null;

      if ( bPK &&  ! (sAction.equals("insert") && "Member".equals(sForm))) {

        // Open recordset
        rs = openrs( stat, sSQL);
        rs.next();
        String[] aFields = getFieldsName( rs );
        getRecordToHash( rs, rsHash, aFields );
        rs.close();
        fldmember_id = (String) rsHash.get("member_id");
        fldmember_login = (String) rsHash.get("member_login");
        fldname = (String) rsHash.get("first_name");
        fldlast_name = (String) rsHash.get("last_name");
        fldaddress = (String) rsHash.get("address");
        fldemail = (String) rsHash.get("email");
        fldphone = (String) rsHash.get("phone");

        if (sAction.equals("") || ! "Member".equals(sForm)) {
      
          fldmember_id = (String) rsHash.get("member_id");
          fldmember_login = (String) rsHash.get("member_login");
          fldname = (String) rsHash.get("first_name");
          fldlast_name = (String) rsHash.get("last_name");
          fldaddress = (String) rsHash.get("address");
          fldemail = (String) rsHash.get("email");
          fldphone = (String) rsHash.get("phone");
        }
        else {
          fldmember_id = (String) rsHash.get("member_id");
          fldmember_login = (String) rsHash.get("member_login");
          fldname = (String) rsHash.get("first_name");
          fldlast_name = (String) rsHash.get("last_name");
          fldaddress = (String) rsHash.get("address");
          fldemail = (String) rsHash.get("email");
          fldphone = (String) rsHash.get("phone");
        }
        
      }
      else {
        if ( "".equals(sMemberErr)) {
          fldmember_id = toHTML((String) session.getAttribute("UserID"));
        }
      }
      


      // Show form field
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Login</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<a href=\"MyInfo.jsp?"+transitParams+"\"><font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldmember_login)+"</font></a>");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">First Name</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldname)+"&nbsp;</font>");
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Last Name</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldlast_name)+"&nbsp;</font>");
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Address</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldaddress)+"&nbsp;</font>");
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Email</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldemail)+"&nbsp;</font>");
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Phone</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<font style=\"font-size: 10pt; color: #000000\">"+toHTML(fldphone)+"&nbsp;</font>");
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td colspan=\"2\" align=\"right\">");
      

      if ( bPK && ! (sAction.equals("insert") && "Member".equals(sForm))) {
        
        out.print("<input type=\"hidden\" name=\"FormName\" value=\"Member\"><input type=\"hidden\" value=\"\" name=\"FormAction\">");
      }
      out.print("<input type=\"hidden\" name=\"member_id\" value=\""+toHTML(fldmember_id)+"\">");
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



String cSec = checkSecurity(1, session, response, request);
if ("sendRedirect".equals(cSec) ) return;
                
boolean bDebug = false;

String sAction = getParam( request, "FormAction");
String sForm = getParam( request, "FormName");
String sItemsErr = "";
String sTotalErr = "";
String sMemberErr = "";

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
if ( sForm.equals("Member") ) {
  sMemberErr = MemberAction(request, response, session, out, sAction, sForm, conn, stat);
  if ( "sendRedirect".equals(sMemberErr)) return;
}


      out.write("            \r\n<html>\r\n<head>\r\n<title>Book Store</title>\r\n<meta name=\"GENERATOR\" content=\"YesSoftware CodeCharge v.1.2.0 / JSP.ccp build 05/21/2001\"/>\r\n<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n<meta http-equiv=\"expires\" content=\"0\"/>\r\n<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n</head>\r\n<body style=\"background-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helveticabackground-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helvetica\">\r\n");
                                                                        JspRuntimeLibrary.include(request, response, "Header.jsp", out, true);
      out.write("\r\n <table>\r\n  <tr>\r\n   \r\n   <td valign=\"top\">\r\n");
                   Member_Show(request, response, session, out, sMemberErr, sForm, sAction, conn, stat); 
      out.write("\r\n    \r\n   </td>\r\n  </tr>\r\n </table>\r\n <table>\r\n  <tr>\r\n   <td valign=\"top\">\r\n");
                                     Items_Show(request, response, session, out, sItemsErr, sForm, sAction, conn, stat); 
      out.write("\r\n    \r\n   </td>\r\n  </tr>\r\n </table>\r\n <table>\r\n  <tr>\r\n   <td valign=\"top\">\r\n");
                                     Total_Show(request, response, session, out, sTotalErr, sForm, sAction, conn, stat); 
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
