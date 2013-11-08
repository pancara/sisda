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
    <link href='<c:url value="/css/site/css.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/sidebar_left.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/css/site/home.css"/>' rel="stylesheet" type="text/css"/>

    <script src="http://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.7&key=AIzaSyDL6NXQzQO7KZnvhMe9OPSYHfpguSfVHQE">
    </script>
    <script src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
    <script src='<c:url value="/js/maplace.js"/>' type="text/javascript"></script>


    <script type="text/javascript">

        $(document).ready(function () {
            var LocsA = [
                {
                    lat: -7.82703,
                    lon: 111.12856,
                    title: 'Perangkat A',
                    html: '<div style="width: 400px: height: 300px;">' +
                            'Photo Perangkat A' +
                            '</div>',
                    icon: '<c:url value="/images/ffws/device.png"/>'
                },
                {
                    lat: -7.92703,
                    lon: 111.12856,
                    title: 'Perangkat B',
                    html: '<div>' +
                            'Photo Perangkat B' +
                            '</div>',
                    icon: '<c:url value="/images/ffws/device.png"/>'
                }
            ];

            var latLng = new google.maps.LatLng(-7.82703, 111.12856);
            var mapOptions = {
                center: latLng,
                zoom: 10,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            new Maplace({
                locations: LocsA,
                map_div: "#gmap",
                map_options: mapOptions,
                afterShow: function(index, location, marker) {
                    console.log(index);
                    console.log(location);
                    console.log(marker);
                    $(marker.html).html("halo");
                }
                

            }).Load();
        });
    </script>
</head>
<body>
<div style="position: absolute; bottom: 1px; right: 1px; width: 200px; height: 150px; z-index: 10000; border: 1px solid #FF0000; background: #FF0000; opacity: 0.8;">
    CHART
</div>
    <div id="gmap" style="width: 900px; height: 600px; z-index: 1000;">
    </div>
</body>
</html>


