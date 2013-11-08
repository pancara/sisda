<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="robots" content="index, follow"/>
    <meta name="keywords" content="BBWS Serayu Opak - SISDA"/>
    <meta name="description" content="SISDA Sistem Informasi Sumber Daya Air"/>
    <title>Pengelolaan SISDA</title>
    <link rel="shortcut icon" href='<c:url value="/images/logo.png"/>' type="image/png"/>
    <%
        java.util.List<String> styles = new LinkedList<String>();
        styles.add("admin/default.css");
        styles.add("admin/style.css");
        styles.add("admin/ffws.css");
        styles.add("admin/paging.css");
        styles.add("jquery.colorbox.css");
        styles.add("superfish.css");
        styles.add("jquery.clickconfirm.css");
        styles.add("pirobox/white/style.css");
        styles.add("jquery.tooltip.css");

        request.setAttribute("styles", styles);

        java.util.List<String> scripts = new LinkedList<String>();
        scripts.add("jquery_1.6.2.min.js");
        scripts.add("date.js");

        scripts.add("jquery.bgiframe.js");
        scripts.add("jquery.dimensions.js");
        scripts.add("jquery.tooltip.js");
        scripts.add("jquery.form.js");
        scripts.add("jquery.flexbox.js");
        scripts.add("jquery.jqote2.js");
        scripts.add("jquery.datePicker.js");
        scripts.add("jquery.colorbox-min.js");
        scripts.add("jquery.clickconfirm.js");
        scripts.add("jcarousellite_1.0.1.js");
        scripts.add("jquery.cookie.js");
        scripts.add("jquery.hotkeys.js");
        scripts.add("jquery.jstree.js");

        scripts.add("piroBox.1_2.js");
        scripts.add("jquery.wj_progress_indicator.js");
        scripts.add("jquery.center.js");
        scripts.add("ckeditor/ckeditor.js");
        scripts.add("ckeditor/adapters/jquery.js");

        request.setAttribute("scripts", scripts);
    %>
    <c:forEach items="${styles}" var="style">
        <link href='<c:url value="/css/${style}"/>' type="text/css" rel="stylesheet"/>
    </c:forEach>
    <c:forEach items="${scripts}" var="script">
        <script src='<c:url value="/js/${script}"/>' type="text/javascript"></script>
    </c:forEach>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#logout").clickConfirm({
                position: 'bottom',
                questionText: "Lakukan logout?"
            });
        });
    </script>
</head>
<body>
<div id="container">
    <page:apply-decorator name="body_only" page="/admin/menu.html"/>
    <div id="main_content">
        <h1><decorator:title/></h1>
        <decorator:body/>
    </div>

    <div style="clear: both; height: 1px;">&nbsp;</div>

    <div id="footer">
        <div class="menubar"></div>
    </div>
</div>
</body>
</html>

