import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;

public class EditorialsRecord_jsp extends HttpJspBase {


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
//   Filename: EditorialsRecord.jsp
//   Generated with CodeCharge  v.1.2.0
//   JSP.ccp build 05/21/2001
//

static final String sFileName = "EditorialsRecord.jsp";
              




  String editorialsAction(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String sAction, String sForm, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
  
    String seditorialsErr ="";
    try {

      if (sAction.equals("")) return "";

      String sSQL="";
      String transitParams = "";
      String primaryKeyParams = "";
      String sQueryString = "";
      String sPage = "";
      String sParams = "";
      String sActionFileName = "EditorialsGrid.jsp";
      String sWhere = " ";
      boolean bErr = false;
      long iCount = 0;

  
      String pPKarticle_id = "";
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

      if ( iAction == iupdateAction || iAction == ideleteAction ) { 
        pPKarticle_id = getParam( request, "PK_article_id");
        if ( isEmpty(pPKarticle_id)) return seditorialsErr;
        sWhere = "article_id=" + toSQL(pPKarticle_id, adNumber);
      }


      String fldarticle_desc="";
      String fldarticle_title="";
      String fldeditorial_cat_id="";
      String flditem_id="";
      String fldarticle_id="";

      // Load all form fields into variables
    
      fldarticle_desc = getParam(request, "article_desc");
      fldarticle_title = getParam(request, "article_title");
      fldeditorial_cat_id = getParam(request, "editorial_cat_id");
      flditem_id = getParam(request, "item_id");
      // Validate fields
      if ( iAction == iinsertAction || iAction == iupdateAction ) {
        if ( isEmpty(fldeditorial_cat_id) ) {
          seditorialsErr = seditorialsErr + "The value in field Editorial Category is required.<br>";
        }
        if ( ! isNumber(fldeditorial_cat_id)) {
          seditorialsErr = seditorialsErr + "The value in field Editorial Category is incorrect.<br>";
        }
        if ( ! isNumber(flditem_id)) {
          seditorialsErr = seditorialsErr + "The value in field Item is incorrect.<br>";
        }
        if (seditorialsErr.length() > 0 ) {
          return (seditorialsErr);
        }
      }


      sSQL = "";
      // Create SQL statement

      switch (iAction) {
  
        case iinsertAction :
          
            sSQL = "insert into editorials (" + 
                "article_desc," +
                "article_title," +
                "editorial_cat_id," +
                "item_id)" +

                " values (" + 
                toSQL(fldarticle_desc, adText) + "," +
                toSQL(fldarticle_title, adText) + "," +
                toSQL(fldeditorial_cat_id, adNumber) + "," +
                toSQL(flditem_id, adNumber) + ")";
          break;
  
      case iupdateAction:
        
          sSQL = "update editorials set " +
                "article_desc=" + toSQL(fldarticle_desc, adText) +
                ",article_title=" + toSQL(fldarticle_title, adText) +
                ",editorial_cat_id=" + toSQL(fldeditorial_cat_id, adNumber) +
                ",item_id=" + toSQL(flditem_id, adNumber);
          sSQL = sSQL + " where " + sWhere;
        break;
      
      case ideleteAction:
           sSQL = "delete from editorials where " + sWhere;
          
        break;
  
      }

      if ( seditorialsErr.length() > 0 ) return seditorialsErr;
      try {
        // Execute SQL statement
        stat.executeUpdate(sSQL);
      }
      catch(java.sql.SQLException e) {
        seditorialsErr = e.toString(); return (seditorialsErr);
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
    return (seditorialsErr);
  }

  


  void editorials_Show(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session, javax.servlet.jsp.JspWriter out, String seditorialsErr, String sForm, String sAction, java.sql.Connection conn, java.sql.Statement stat) throws java.io.IOException {
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
      
      String particle_id = "";

      String fldarticle_id="";
      String fldarticle_desc="";
      String fldarticle_title="";
      String fldeditorial_cat_id="";
      String flditem_id="";


      boolean bPK = true;

      if ( "".equals(seditorialsErr)) {
        // Load primary key and form parameters
        fldarticle_id = getParam( request, "article_id");
        particle_id = getParam( request, "article_id");
      }
      else {
        // Load primary key, form parameters and form fields
        fldarticle_id = getParam( request, "article_id");
        fldarticle_desc = getParam( request, "article_desc");
        fldarticle_title = getParam( request, "article_title");
        fldeditorial_cat_id = getParam( request, "editorial_cat_id");
        flditem_id = getParam( request, "item_id");
        particle_id = getParam( request, "PK_article_id");
      }

      
      if ( isEmpty(particle_id)) { bPK = false; }
      
      sWhere += "article_id=" + toSQL(particle_id, adNumber);
      primaryKeyParams += "<input type=\"hidden\" name=\"PK_article_id\" value=\""+particle_id+"\"/>";

      sSQL = "select * from editorials where " + sWhere;


      out.println("    <table style=\"\">");
      out.println("     <tr>\n      <td style=\"background-color: #336699; text-align: Center; border-style: outset; border-width: 1\" colspan=\"2\"><font style=\"font-size: 12pt; color: #FFFFFF; font-weight: bold\">Editorial</font></td>\n     </tr>");
      if ( ! seditorialsErr.equals("")) {
        out.println("     <tr>\n      <td style=\"background-color: #FFFFFF; border-width: 1\" colspan=\"2\"><font style=\"font-size: 10pt; color: #000000\">"+seditorialsErr+"</font></td>\n     </tr>");
      }
      seditorialsErr="";
      out.println("     <form method=\"get\" action=\""+sFileName+"\" name=\"editorials\">");

      java.sql.ResultSet rs = null;

      if ( bPK &&  ! (sAction.equals("insert") && "editorials".equals(sForm))) {

        // Open recordset
        rs = openrs( stat, sSQL);
        rs.next();
        String[] aFields = getFieldsName( rs );
        getRecordToHash( rs, rsHash, aFields );
        rs.close();
        fldarticle_id = (String) rsHash.get("article_id");
        if ( "".equals(seditorialsErr)) {
          // Load data from recordset when form displayed first time
          fldarticle_desc = (String) rsHash.get("article_desc");
          fldarticle_title = (String) rsHash.get("article_title");
          fldeditorial_cat_id = (String) rsHash.get("editorial_cat_id");
          flditem_id = (String) rsHash.get("item_id");
        }

        if (sAction.equals("") || ! "editorials".equals(sForm)) {
      
          fldarticle_id = (String) rsHash.get("article_id");
          fldarticle_desc = (String) rsHash.get("article_desc");
          fldarticle_title = (String) rsHash.get("article_title");
          fldeditorial_cat_id = (String) rsHash.get("editorial_cat_id");
          flditem_id = (String) rsHash.get("item_id");
        }
        
      }
      else {
        if ( "".equals(seditorialsErr)) {
          fldarticle_id = toHTML(getParam(request,"article_id"));
        }
      }
      


      // Show form field
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Article Description</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"article_desc\" maxlength=\"\" value=\""+toHTML(fldarticle_desc)+"\" size=\"\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Article Title</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); out.print("<input type=\"text\"  name=\"article_title\" maxlength=\"200\" value=\""+toHTML(fldarticle_title)+"\" size=\"50\">");

      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Editorial Category</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); 
      out.print("<select name=\"editorial_cat_id\">"+getOptions( conn, "select editorial_cat_id, editorial_cat_name from editorial_categories order by 2",false,true,fldeditorial_cat_id)+"</select>");
      
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td style=\"background-color: #FFEAC5; border-style: inset; border-width: 0\"><font style=\"font-size: 10pt; color: #000000\">Item</font></td><td style=\"background-color: #FFFFFF; border-width: 1\">"); 
      out.print("<select name=\"item_id\">"+getOptions( conn, "select item_id, name from items order by 2",false,false,flditem_id)+"</select>");
      
      out.println("</td>\n     </tr>");
      
      out.print("     <tr>\n      <td colspan=\"2\" align=\"right\">");
      

      if ( bPK && ! (sAction.equals("insert") && "editorials".equals(sForm))) {
        out.print("<input type=\"submit\" value=\"Update\" onclick=\"document.editorials.FormAction.value = 'update';\">");out.print("<input type=\"submit\" value=\"Delete\" onclick=\"document.editorials.FormAction.value = 'delete';\">");out.print("<input type=\"submit\" value=\"Cancel\" onclick=\"document.editorials.FormAction.value = 'cancel';\">");
        out.print("<input type=\"hidden\" name=\"FormName\" value=\"editorials\"><input type=\"hidden\" value=\"update\" name=\"FormAction\">");
      }
      
      else {
        out.print("<input type=\"submit\" value=\"Insert\" onclick=\"document.editorials.FormAction.value = 'insert';\">");out.print("<input type=\"submit\" value=\"Cancel\" onclick=\"document.editorials.FormAction.value = 'cancel';\">");
        out.print("<input type=\"hidden\" name=\"FormName\" value=\"editorials\"><input type=\"hidden\" value=\"insert\" name=\"FormAction\">");
      }out.print("<input type=\"hidden\" name=\"article_id\" value=\""+toHTML(fldarticle_id)+"\">");
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



String cSec = checkSecurity(2, session, response, request);
if ("sendRedirect".equals(cSec) ) return;
                
boolean bDebug = false;

String sAction = getParam( request, "FormAction");
String sForm = getParam( request, "FormName");
String seditorialsErr = "";

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
if ( sForm.equals("editorials") ) {
  seditorialsErr = editorialsAction(request, response, session, out, sAction, sForm, conn, stat);
  if ( "sendRedirect".equals(seditorialsErr)) return;
}


      out.write("            \r\n<html>\r\n<head>\r\n<title>Book Store</title>\r\n<meta name=\"GENERATOR\" content=\"YesSoftware CodeCharge v.1.2.0 / JSP.ccp build 05/21/2001\"/>\r\n<meta http-equiv=\"pragma\" content=\"no-cache\"/>\r\n<meta http-equiv=\"expires\" content=\"0\"/>\r\n<meta http-equiv=\"cache-control\" content=\"no-cache\"/>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n</head>\r\n<body style=\"background-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helveticabackground-color: #FFFFFF; color: #000000; font-family: Arial, Tahoma, Verdana, Helvetica\">\r\n");
                                                                        JspRuntimeLibrary.include(request, response, "Header.jsp", out, true);
      out.write("\r\n <table>\r\n  <tr>\r\n   \r\n   <td valign=\"top\">\r\n");
                   editorials_Show(request, response, session, out, seditorialsErr, sForm, sAction, conn, stat); 
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
