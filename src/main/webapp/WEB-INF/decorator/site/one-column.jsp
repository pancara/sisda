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
    <link href='<c:url value="/css/site/style-one-column.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/ticker.css"/>' rel="stylesheet" type="text/css"/>

    <script src='<c:url value="/js/jquery.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.ticker.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/app.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/moment.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.categories.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.crosshair.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.easing.1.3.js"/>' type="text/javascript"></script>
    
    <script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyDL6NXQzQO7KZnvhMe9OPSYHfpguSfVHQE">
    </script>

    <script src='<c:url value="/js/gmap3.min.js"/>' type="text/javascript"></script>
    
<%--<script src='<c:url value="/js/jquery.clickconfirm.js"/>' type="text/javascript"></script>--%>
    <%--<script src='<c:url value="/js/jquery.vticker.js"/>' type="text/javascript"></script>--%>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btn_to_top").click(function () {
                $('html, body').animate({scrollTop: 0}, "slow");
            });

            ticker_init();
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
        <div id="content">
            <decorator:body/>
        </div>
        <div class="clear">&nbsp;</div>
    </div>
    <%@include file="includes/footer.jsp" %>
    <div class="clear"></div>
</div>
</body>
</html>


