<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:decorator="http://www.w3.org/1999/xhtml">
<head>
    <title>Intranet BBWS Serayu Opak </title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href='<c:url value="/images/logo-16x16.png"/>' type="image/png"/>
    <link rel="stylesheet" href="<c:url value="/css/banner.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/statusbar.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/content.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/css/no-panel.css"/>"/>


    <script src="<c:url value="/script/jquery.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/script/jquery.form.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/script/jquery.easing.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/script/pirobox.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/script/jquery.tabs.modified.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/script/jquery.tipsy.js"/>" type="text/javascript"></script>

    <script src="<c:url value="/script/jquery.innerfade.js"/>" type="text/javascript"></script>

    <script src="<c:url value="/ui/js/document_ready.html"/>" type="text/javascript"></script>
    <decorator:head/>
</head>
<body>
<page:applyDecorator name="bar" page="/banner.html"/>
<div id="container">
    <div class="top">&nbsp;</div>
    <div class="middle">
        <div id="page-title">
            <span><decorator:title/></span>
        </div>
        <div id="content">
            <decorator:body/>
        </div>
        <div class='clear'>&nbsp;</div>
        <page:applyDecorator name="bar" page='/footer.html'/>
        <div class='clear'>&nbsp;</div>

    </div>
    <div class="bottom">&nbsp;</div>
</div>


</body>
</html>
