<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:decorator="http://www.w3.org/1999/xhtml">
<head>
    <title>BBWS SERAYU OPAK</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="robots" content="index, follow"/>
    <meta name="keywords"
          content="sisda, sistem informasi sumber daya air, BBWSSO, hidrologi, waduk, embung, yogyakarta"/>
    <meta name="description" content="sistem informasi sumber daya air, BBWSSO, hidrologi, waduk, embung, yogyakarta"/>

    <link rel="shortcut icon" href='<c:url value="/images/logo.png"/>' type="image/png"/>

    <decorator:head/>

    <link href='<c:url value="/css/site/font.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/pirobox/white/style.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/style-three-column-single.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/ticker.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/folder_tree.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/colorbox.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/jquery.tooltip.css"/>' rel="stylesheet" type="text/css"/>

    <script src='<c:url value="/js/jquery.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/app.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.tooltip.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.easing.1.3.js"/>' type="text/javascript"></script>

    <script src='<c:url value="/js/piroBox.1_2.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.colorbox-min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.ticker.js"/>' type="text/javascript"></script>
    <%--<script src='<c:url value="/js/jquery.vticker.js"/>' type="text/javascript"></script>--%>
    <script src='<c:url value="/js/jquery.center.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.wj_progress_indicator.js"/>' type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#btn_to_top").click(function () {
                $('html, body').animate({scrollTop: 0}, "slow");
            });

            ticker_init();
            start_ffws_side_panel_updater();
        });
    </script>
</head>
<body>
<page:apply-decorator name="body_only" page="/ticker.html"/>
<div id="container">
    <div id="header">
        <page:apply-decorator name="body_only" page="/menubar.html"/>
        <%@include file="includes/banner.jsp" %>
    </div>

    <div id="main">
        <div id="sidebar-left">
            <page:apply-decorator name="body_only" page="/menu.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/leaflet.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/calendar.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/guest_book_form.html"/>
        </div>

        <div id="content">
            <h1>
                <decorator:title/>
            </h1>

            <div>
                <decorator:body/>
            </div>
            <div class="clear">&nbsp;</div>
        </div>
        <div id="sidebar-right">
            <page:apply-decorator name="body_only" page="/side_panel/ffws.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/publication.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/presentation/meeting.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/application.html"/>
            <page:apply-decorator name="body_only" page="/side_panel/institution.html"/>

            <%--<page:apply-decorator name="body_only" page="/side_panel/media.html"/>--%>

        </div>

        <div style="clear: both; height: 1px;">
            &nbsp;
        </div>
    </div>

    <%@include file="includes/footer.jsp" %>

    <div class="clear"></div>
</div>
</body>
</html>


