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

    <link href='<c:url value="/css/site/ticker-style.css"/>' rel="stylesheet" type="text/css"/>

    <script src='<c:url value="/js/jquery.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/moment.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.categories.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/flot/jquery.flot.crosshair.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.tooltip.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.easing.1.3.js"/>' type="text/javascript"></script>

    <script src='<c:url value="/js/jquery.jqote2.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.center.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/piroBox.1_2.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.colorbox-min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.clickconfirm.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.ticker.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.vticker.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/jquery.wj_progress_indicator.js"/>' type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#btn_to_top").click(function () {
                $('html, body').animate({scrollTop: 0}, "slow");
            });

            $('#sidebar-left div.box div.news_latest').vTicker({
                speed: "slow",
                pause: 3000,
                animation: 'fade',
                showItems: 1,
                mousePause: true,
                direction: 'down'
            });
            $('#sidebar-right div.box div.publication_latest').vTicker({
                speed: "slow",
                pause: 3000,
                animation: 'fade',
                showItems: 1,
                mousePause: true,
                direction: 'down'

            });
            $('#ticker').ticker({
                titleText: 'Update :'
            });

            $("#cont_ticker").css({
                "left": $("#header").offset().left,
                "width": $("#header").width(),
                "position": "fixed",
                "top": "-1px",
                "visibility": "visible"
            });
        });
    </script>
</head>
<body>
<div id="container">
    <div id="header">
        <div class="menubar_cont">
            <page:apply-decorator name="body_only" page="/menubar.html"/>
        </div>

        <div style="padding: 8px 8px 0px 8px; background: transparent;">
            <img src='<c:url value="/images/pu_90.jpg"/>'
                 style="float: left; margin: 8px 16px 0px 16px; width: 50px; height: 50px;"/>

            <img src='<c:url value="/images/header.png"/> ' style="float: right;"/>

            <div style="display: inline-block;">
                <%--<h1>Sistem Informasi Sumber Daya Air</h1>--%>

                <h2>Balai Besar Wilayah Sungai Serayu Opak</h2>

                <h3>Direktorat Jenderal Sumber Daya Air</h3>

                <h3>Kementerian Pekerjaan Umum</h3>
            </div>
        </div>
    </div>

    <div id="page">
        <div id="sidebar-left">
            <div class="box">
                <page:apply-decorator name="body_only" page="/menu.html"/>
            </div>
            <div class="box">
                <h1><img src='<c:url value="/images/16x16/news_latest.png"/>'/> &nbsp; Berita Terkini</h1>
                <page:apply-decorator name="body_only" page="/news/latest.html"/>
            </div>

            <div class="box">
                <h1>Leaflet</h1>
                <page:apply-decorator name="body_only" page="/leaflet/latest.html"/>
            </div>

            <div class="box">
                <page:apply-decorator name="body_only" page="/calendar.html"/>
            </div>
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

            <div class="box">
                <h1>
                    <%--<img src='<c:url value="/images/16x16/chart_line.png"/>'/> &nbsp; --%>
                    Tinggi Muka Air
                </h1>
                <page:apply-decorator name="body_only" page="/ffws/water_level/latest.html"/>
            </div>

            <div class="box">
                <h1>Pengumuman Terbaru</h1>
                <page:apply-decorator name="body_only" page="/publication/latest.html"/>
            </div>

            <div class="box">
                <h1>&nbsp;Aplikasi BBWS-SO</h1>
                <page:apply-decorator name="body_only" page="/app/list.html"/>
            </div>
            <div class="box">
                <h1>Link Instansi</h1>
                <page:apply-decorator name="body_only" page="/link/list/2.html"/>
            </div>
            <%--<div class="box">--%>
            <%--<h1><img src='<c:url value="/images/16x16/world_link.png"/>'/> Media</h1>--%>
            <%--<page:apply-decorator name="bar" page="/site_url/list/1.html"/>--%>
            <%--</div>--%>
        </div>

        <div style="clear: both; height: 1px;">
            &nbsp;
        </div>
    </div>
    <div id="footer">
        <%--<a id='btn_to_top' title="kembali ke atas">--%>
        <%--<img src='<c:url value="/images/22x22/to_top.png"/>'/>--%>
        <%--</a>--%>

        <div class="stats">
            <h1><img src='<c:url value="/images/16x16/chart_curve_edit.png"/>'/> Stats</h1>
            <page:apply-decorator name="body_only" page="/statistic.html"/>
        </div>

        <div>
            <div class="menubar">
                <page:apply-decorator name="body_only" page="/menubar.html"/>
            </div>
            <div style="margin-right: 4px;">
                <jsp:include page="../includes/footer.jsp"/>
            </div>
        </div>
    </div>
</div>
<div id="cont_ticker">
    <page:apply-decorator name="body_only" page="/ticker/list.html"/>
</div>
</body>
</html>


