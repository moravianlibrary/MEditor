<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cz.mzk.editor.client.util.Constants" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String hostname = (String) request.getAttribute("hostname");
    List<Constants.USER_IDENTITY_TYPES> enabledIdentities = ( List<Constants.USER_IDENTITY_TYPES>) request.getAttribute("enabledIdentities");
    pageContext.setAttribute("openIdEnabled", enabledIdentities.contains(Constants.USER_IDENTITY_TYPES.OPEN_ID));
    pageContext.setAttribute("ldapEnabled", enabledIdentities.contains(Constants.USER_IDENTITY_TYPES.LDAP));
    pageContext.setAttribute("shibbolethEnabled", enabledIdentities.contains(Constants.USER_IDENTITY_TYPES.SHIBBOLETH));
%>
<!doctype html>
<html>
<head>
    <script type="text/javascript">
        var _gaq = _gaq || [];
        _gaq.push([ '_setAccount', 'UA-19922555-1' ]);
        _gaq.push([ '_trackPageview' ]);

        (function() {
            var ga = document.createElement('script');
            ga.type = 'text/javascript';
            ga.async = true;
            ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(ga, s);
        })();
    </script>

    <!-- Janrain AuthN -->
    <script type="text/javascript">
        (function() {
            if (typeof window.janrain !== 'object') window.janrain = {};
            if (typeof window.janrain.settings !== 'object') window.janrain.settings = {};
            janrain.settings.tokenUrl = '<%=hostname%>/meditor/janrain_spring_security_check';
            function isReady() { janrain.ready = true; };
            if (document.addEventListener) {
                document.addEventListener("DOMContentLoaded", isReady, false);
            } else {
                window.attachEvent('onload', isReady);
            }
            var e = document.createElement('script');
            e.type = 'text/javascript';
            e.id = 'janrainAuthWidget';
            if (document.location.protocol === 'https:') {
                e.src = 'https://rpxnow.com/js/lib/metaeditor/engage.js';
            } else {
                e.src = 'http://widget-cdn.rpxnow.com/js/lib/metaeditor/engage.js';
            }
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(e, s);
        })();
    </script>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen" />
    <style type="text/css">
      #logo {margin:5px; margin-bottom:30px;}
      #authn {margin:5px;}
      #ldap  {margin:25px; margin-top:5px;}
      #shib  {margin:5px; margin-left:25px;}
      .show  {display: block;}
      .hide  {display: none;}
    </style>
    <title>Login</title>
  </head>
  <body>
    <script src="../js/jquery-1.9.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <div id="logo"><a href="http://code.google.com/p/meta-editor/"><img src="../images/editor_logo.png" alt="editor logo" title="Version ${project.version}" /> </a> <br /> <a href="http://code.google.com/p/meta-editor/">Project Page</a>
    </div>
    <div class="alert alert-info fade in hide" id="notification">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <div id="login_error"></div>
    </div>
    <div id="authn">Autentizace</div>
    <div class="tabbable">


        <ul class="nav nav-tabs">

            <c:if test="${openIdEnabled}">
                <!-- OpenID -->
                <li class="active"><a href="#tab1" data-toggle="tab">OpenID</a></li>
            </c:if>

            <c:if test="${ldapEnabled}">
                <!-- LDAP -->
                <li class="show"><a href="#tab2" data-toggle="tab">LDAP</a></li>
            </c:if>

            <c:if test="${shibbolethEnabled}">
                <!-- Shibboleth -->
                <li class="show"><a href="#tab3" data-toggle="tab">Shibboleth</a></li>
            </c:if>
        </ul>


        <div class="tab-content">
            <c:if test="${openIdEnabled}">
                <div class="tab-pane active" id="tab1">
                    <form name="f" action="../meditor/j_spring_security_check" method="POST">
                        <div id="janrainEngageEmbed"></div>
                    </form>
                </div>
            </c:if>
            <c:if test="${ldapEnabled}">
                <div class="tab-pane" id="tab2">
                    <form name="f" action="../meditor/j_spring_security_check" method="POST">
                        <table id="ldap">
                            <tr>
                                <td>Login:</td>
                                <td><input type="text" name="j_username" value=""/></td>
                            </tr>
                            <tr>
                                <td>Password:</td>
                                <td><input type="password" name="j_password"/></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input name="submit" type="submit" value="Login"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </c:if>
            <c:if test="${shibbolethEnabled}">
                <div class="tab-pane" id="tab3">
                    <a id="shib" class="login"
                       href="<%=hostname%>/Shibboleth.sso/Login?target=<%=hostname%>%2Fmeditor%2Fshibboleth_spring_security_check">
                        <img border="0" src="../images/shibboleth.png" alt="shibboleth logo" width="350px"/> </a>
                </div>
            </c:if>
        </div>
    </div>
    <script type="text/javascript" language="javascript">
      var isError = function() {
      	var query = window.location.search.substring(1);
      	var vars = query.split("&");
      	for ( var i = 0; i < vars.length; i++) {
      	  var pair = vars[i].split("=");
      	  if (pair[0] === "login_error") {
            if (pair[1] == 1) {
              return "Unable to login - wrong username or password, please check the credentials and try it again.";
            } else if (pair[1] == 2) {
      	      return "Unable to login - please check whether the database is running. There should be more info in the server log.";
      	    }
          }
      	}
      	return false;
      }();
      if (isError) {
      	document.getElementById('login_error').innerHTML = isError;
      	$(".alert").fadeIn();
      }
    </script>
  </body>
</html>
