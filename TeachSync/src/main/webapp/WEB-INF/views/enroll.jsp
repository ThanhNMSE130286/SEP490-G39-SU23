<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Course Detail</title>

  <link rel="stylesheet" href="../../resources/css/bootstrap-5.3.0/bootstrap.css">

  <link rel="stylesheet" href="../../resources/css/teachsync_style.css">

  <script src="../../resources/js/jquery/jquery-3.6.3.js"></script>
  <script src="../../resources/js/bootstrap-5.3.0/bootstrap.js"></script>

  <script src="../../resources/js/common.js"></script>
</head>
<body class="container-fluid ts-bg-white-subtle">
<!-- ================================================== Header ===================================================== -->
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<!-- ================================================== Header ===================================================== -->


<!-- ================================================== Breadcrumb ================================================= -->
<div class="row ts-bg-white border ts-border-teal rounded-3 mx-2 mb-3">
  <div class="col">
    <nav aria-label="breadcrumb">
      <ol class="breadcrumb ts-txt-sm ts-txt-bold my-2">
        <li class="breadcrumb-item">
          <a href="/">
            <i class="bi-house-door"></i>&nbsp;Trang chủ
          </a>
        </li>
        <li class="breadcrumb-item" aria-current="page">
          <a href="/course">
            Khóa học
          </a>
        </li>
        <li class="breadcrumb-item active" aria-current="page">
          <c:out value="${course.courseName}"/>
        </li>
      </ol>
    </nav>
  </div>
</div>
<!-- ================================================== Breadcrumb ================================================= -->


<!-- ================================================== Main Body ================================================== -->
<div class="row ts-bg-white border ts-border-teal rounded-3 pt-3 mx-2 mb-3">
  <!-- Course List paging -->
  <div class="col-12 mb-3">
    <div class="row gy-3">
      <div class="col-sm-12 col-md-3 px-sm-3 pe-md-0">
        <img src="${course.courseImg}" class="rounded-2 border ts-border-blue w-100 h-auto">
      </div>

      <div class="col-sm-12 col-md-9 px-3">
        <div class="card ts-border-yellow h-100">

          <div class="card-header">
            <label>
              Tên khóa:
              <input type="text" value="${course.courseName}"/>
            </label>
          </div>

          <div class="card-body d-flex">
            <c:set var="currentPrice" value="${course.currentPrice}"/>
            <c:set var="isPromotion" value="${currentPrice.isPromotion}"/>
            <h5 class="card-subtitle">
              <c:if test="${!isPromotion}">
                <c:out value="${currentPrice.price}"/> ₫
              </c:if>

              <label>
                Học phí:
                <input type="text" value="${course.courseName}"/>
              </label>


              <c:if test="${isPromotion}">
                <span class="ts-txt-orange ts-txt-bold"><c:out value="${currentPrice.finalPrice}"/>&nbsp;₫</span>
                <br/>
                <span class="ts-txt-grey ts-txt-light ts-txt-sm ts-txt-italic ts-txt-line-through">
          &nbsp;<c:out value="${currentPrice.price}"/>&nbsp;₫
          </span>
                <span class="ts-txt-orange ts-txt-sm">
          &nbsp;-<c:out value="${currentPrice.promotionAmount}"/>
          <c:choose>
            <c:when test="${currentPrice.promotionType eq 'PERCENT'}">%</c:when>
            <c:when test="${currentPrice.promotionType eq 'AMOUNT'}">₫</c:when>
          </c:choose>
          </span>
              </c:if>
            </h5>

            <p class="card-text">
              <c:out value="${course.courseDesc}"/>
            </p>

            <!-- Course schedule -->

          </div>


          <c:if test="${isGuest}">
            <div class="card-footer text-center">
              <c:set var="courseId" value="${course.id}" scope="session"/>
              <a href="login" class="btn btn-primary w-25">Đăng ký học</a>
            </div>
          </c:if>

          <c:if test="${isStudent}">
            <div class="card-footer text-center">
              <c:url var="enrollLink" value="enroll">
                <c:param name="id" value="${course.id}"/>
              </c:url>
              <a href="${enrollLink}" class="btn btn-primary w-25">Đăng ký học</a>
            </div>
          </c:if>

        </div>
      </div>
    </div>
  </div>
</div>
<!-- ================================================== Main Body ================================================== -->


<!-- ================================================== Footer ===================================================== -->
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<!-- ================================================== Footer ===================================================== -->
</body>
</html>