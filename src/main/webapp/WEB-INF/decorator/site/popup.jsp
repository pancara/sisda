<%@ page import="java.util.LinkedList" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:decorator="http://www.w3.org/1999/xhtml">
<head>
    <title>SISDA - BBWS Serayu Opak</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="robots" content="index, follow"/>
    <meta name="keywords"
          content="sisda, sistem informasi sumber daya air, BBWSSO, hidrologi, waduk, embung, yogyakarta"/>
    <meta name="description" content="sistem informasi sumber daya air, BBWSSO, hidrologi, waduk, embung, yogyakarta"/>
    <link rel="shortcut icon" href='<c:url value="/images/logo.png"/>' type="image/png"/>
    <decorator:head/>
    <%
        java.util.List<String> styles = new LinkedList<String>();
        styles.add("site/popup.css");
        styles.add("jquery.clickconfirm.css");
        styles.add("jquery.tooltip.css");

        request.setAttribute("styles", styles);

        java.util.List<String> scripts = new LinkedList<String>();
        scripts.add("jquery_1.6.2.min.js");
        scripts.add("date.js");

        scripts.add("jquery.bgiframe.js");
        scripts.add("jquery.dimensions.js");
        scripts.add("jquery.clickconfirm.js");
        scripts.add("jquery.tooltip.js");
        scripts.add("jquery.form.js");
        scripts.add("jquery.jqote2.js");
        
        request.setAttribute("scripts", scripts);
    %>
    <c:forEach items="${styles}" var="style">
        <link href='<c:url value="/css/${style}"/>' type="text/css" rel="stylesheet"/>
    </c:forEach>
    <c:forEach items="${scripts}" var="script">
        <script src='<c:url value="/js/${script}"/>' type="text/javascript"></script>
    </c:forEach>
</head>
<body>
<div id="container">
    <h1>
        <decorator:title/>
    </h1>

    <div id="content">
        <decorator:body/>
    </div>
</div>
</body>
</html>


