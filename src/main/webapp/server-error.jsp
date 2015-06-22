<%@ page language="java" isErrorPage="true" %>
<html xmlns="http://www.w3.org/1999/xhtml" dir='ltr'>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Error 404</title>
    <style type="text/css">
        html {
            background: #f9f9f9;
        }

        body {
            background: #fff;
            color: #333;
            font-family: sans-serif;
            margin: 2em auto;
            padding: 1em 2em;
            -webkit-border-radius: 3px;
            border-radius: 3px;
            border: 1px solid #dfdfdf;
            max-width: 700px;
        }

        h1 {
            border-bottom: 1px solid #dadada;
            clear: both;
            color: #666;
            font: 24px Georgia, "Times New Roman", Times, serif;
            margin: 30px 0 0 0;
            padding: 0;
            padding-bottom: 7px;
        }

        #error-page {
            margin-top: 50px;
        }

        #error-page p {
            font-size: 14px;
            line-height: 1.5;
            margin: 25px 0 20px;
        }

        #error-page code {
            font-family: Consolas, Monaco, monospace;
        }

        ul li {
            margin: 0px 0px 10px 0px;
            font-size: 14px;
        }

        a {
            color: #21759B;
            text-decoration: none;
        }

        a:hover {
            color: #D54E21;
        }

        .button {
            font-family: sans-serif;
            text-decoration: none;
            font-size: 14px !important;
            line-height: 16px;
            padding: 6px 12px;
            cursor: pointer;
            border: 1px solid #bbb;
            color: #464646;
            -webkit-border-radius: 15px;
            border-radius: 15px;
            -moz-box-sizing: content-box;
            -webkit-box-sizing: content-box;
            box-sizing: content-box;
            background-color: #f5f5f5;
            background-image: -ms-linear-gradient(top, #ffffff, #f2f2f2);
            background-image: -moz-linear-gradient(top, #ffffff, #f2f2f2);
            background-image: -o-linear-gradient(top, #ffffff, #f2f2f2);
            background-image: -webkit-gradient(linear, left top, left bottom, from(#ffffff), to(#f2f2f2));
            background-image: -webkit-linear-gradient(top, #ffffff, #f2f2f2);
        }

        .button:hover {
            color: #000;
            border-color: #666;
        }

        .button:active {
            background-image: -ms-linear-gradient(top, #f2f2f2, #ffffff);
            background-image: -moz-linear-gradient(top, #f2f2f2, #ffffff);
            background-image: -o-linear-gradient(top, #f2f2f2, #ffffff);
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f2f2f2), to(#ffffff));
            background-image: -webkit-linear-gradient(top, #f2f2f2, #ffffff);
        }

        div.error {
            font-size: 11px;
            line-height: 18px;
            border-top: 1px solid #999;
            padding-top: 24px;
        }
    </style>
</head>
<body id="error-page">

<h1>Server Internal Error</h1>

<ul>
    <li>
        <p>
            Laporkan masalah ini untuk membantu kami dalam melayani Anda lebih baik.
            Silakan klik di <a href="mailto:">sini</a> untuk melaporkan.
        </p>
    </li>

    <li>
        <p>Untuk kembali ke halaman depan klik di <a href='http://bbws-so.net/'>sini</a></p>
    </li>
</ul>
<div class="error">
    <%
        exception.printStackTrace(new java.io.PrintWriter(out));
    %>
</div>
</body>
</html>