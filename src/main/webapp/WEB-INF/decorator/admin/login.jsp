<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="robots" content="index, follow"/>
    <title>Pengelolaan SISDA</title>

    <link rel="shortcut icon" href='<c:url value="/images/logo.png"/>' type="image/png"/>
    <style type="text/css">
        body {
            background: #355161;
        }

        div {
            margin: 150px auto auto auto;
            padding: 16px 16px 16px 16px;
            background: #f4fbfe;
            border-radius: 6px;
            border: 5px solid #dde9fc;
            width: 320px;
        }

        #form input[type=text], #form input[type=password] {
            border-radius: 3px;
            border: 1px solid #CCC;
            padding: 2px 2px 2px 2px;
        }

        #form input[type=text]:focus, #form input[type=password]:focus {
            border: 1px solid #0094E0;
            box-shadow: 0px 0px 5px #AACCFF;
        }

        #form input[type=submit], button {
            padding: 2px 4px 2px 4px;

            font-family: "Trebuchet MS";
            font-size: 12px;
            border: 1px solid #DDD;
            background: #F5F5F5;
            background: -moz-linear-gradient(top, #dddddd, #f5f5f5);
            background: -webkit-gradient(linear, left top, left bottom, from(#dddddd), to(#f5f5f5));
            filter: progid:dximagetransform.microsoft.gradient(StartColorStr = '#DDD', EndColorStr = '#F5F5F5', GradientType = 0);
            border-radius: 4px;
            outline: none;
            color: #000;
        }

        #form input[type=submit]:focus, button:focus {
            border: 1px solid #0094E0;
            outline: none;
        }

        #form td {
            font-family: "Trebuchet MS";
            font-size: 12px;
            color: #02253a;
        }

        span.glyph {
            float: left;
            background: #FFF;
            color: #ee9356;
            border: 3px solid #ede2da;
            padding: 4px;
            border-radius: 6px;
            font-family: "Trebuchet MS";
            font-size: 12px;
            margin-right: 8px;
        }

        input {
            font-family: "Trebuchet MS";
            font-size: 14px;
            color: #333;
        }

        input.text {
            width: 150px;
        }

        ul.error {
            color: #FF0000;
            font-family: "Trebuchet MS";
            font-size: 12px;
        }

    </style>
</head>
<body>
<div>
    <decorator:body/>
</div>
</body>
</html>