<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" dir="ltr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Certificate</title>

    <link rel="stylesheet" href="../../resources/css/bootstrap-5.3.0/bootstrap.css">
    <link rel="stylesheet" href="../../resources/css/certificate_style.css">
    <script src="../../resources/js/jquery/jquery-3.6.3.js"></script>
    <script src="../../resources/js/bootstrap-5.3.0/bootstrap.bundle.js"></script>
    <script src="../../resources/js/common.js"></script>
</head>
<body class="container-fluid ts-bg-white-subtle">
<!-- ================================================== Header ===================================================== -->
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<!-- ================================================== Header ===================================================== -->

<!-- ================================================== Main Body ================================================== -->
<div class="row ts-bg-white border ts-border-teal rounded-3 mx-2 mb-3">
    <div class="col">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb ts-txt-sm ts-txt-bold my-2">
                <li class="breadcrumb-item">
                    <a href="/index">
                        <i class="bi-house-door"></i>&nbsp;Trang chủ
                    </a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">
                    Phòng học
                </li>
            </ol>
        </nav>
    </div>
</div>
<!-- ================================================== Main Body ================================================== -->

<!-- ================================================== Footer ===================================================== -->
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<!-- ================================================== Footer ===================================================== -->
</body>
</html>
