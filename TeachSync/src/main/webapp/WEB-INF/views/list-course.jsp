<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Course List</title>

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
                    <li class="breadcrumb-item active" aria-current="page">
                        Khóa học
                    </li>
                </ol>
            </nav>
        </div>
    </div>
    <!-- ================================================== Breadcrumb ================================================= -->



    <!-- ================================================== Main Body ================================================== -->
    <div class="row ts-bg-white border ts-border-teal rounded-3 pt-3 mx-2 mb-3">
        <!-- Hot Course -->
        <c:if test="${hotCourseList ne null}">
            <div class="col-12 border-bottom ts-border-blue mb-3">
                <h5>Thịnh hành</h5>

                <div class="row flex-row flex-nowrap overflow-auto gx-3 mb-3">
                    <c:forEach var="hotCourse" items="${hotCourseList}">
                        <div class="col-sm-4 col-md-2">
                            <div class="card ts-border-orange h-100">
                                <%--<img src="${hotCourse.img}" class="rounded-1 border ts-border-yellow w-100 h-auto mb-2">--%>
                                <img src="../../resources/img/logo-wide.png" class="card-img-top">

                                <div class="card-body">
                                    <h6 class="card-title">
                                        <c:url var="courseLink" value="course-detail">
                                            <c:param name="id" value="${hotCourse.id}"/>
                                        </c:url>
                                        <a href="${courseLink}">
                                            <c:out value="${hotCourse.courseName}"/>
                                        </a>
                                    </h6>

                                    <p class="card-text ts-txt-sm">
                                        <c:out value="${hotCourse.courseDesc}"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <!-- Course List paging -->
        <div class="col-12 mb-3">
<%--            <select name="" id="">--%>
<%--                <c:forEach var="field" items="${searchableFieldList}">--%>
<%--                    <option value="${field}"><c:out value="${field}"/></option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>

            <h5>Danh sách</h5>

            <div class="row gy-3 mb-3">
                <c:forEach var="course" items="${courseList}">
                    <div class="col-12">
                        <div class="row px-3">
                            <div class="col-2 rounded-start-2 border ts-border-orange overflow-hidden px-0">
                                    <%--<img src="${hotCourse.img}" class="rounded-1 border ts-border-yellow w-100 h-auto mb-2">--%>
                                <img src="../../resources/img/engbook.jpg" class="w-100 h-auto">
                            </div>

                            <div class="col-10 px-0">
                                <div class="card rounded-start-0 border-start-0 ts-border-orange h-100">
                                    <div class="card-header">
                                        <h5 class="card-title mb-0">
                                            <c:url var="courseLink" value="course-detail">
                                                <c:param name="id" value="${course.id}"/>
                                            </c:url>
                                            <a href="${courseLink}">
                                                <c:out value="${course.courseName}"/>
                                            </a>
                                        </h5>
                                    </div>
                                    
                                    <div class="card-body">
                                        <p class="card-text">
                                            <c:out value="${course.courseDesc}"/>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <!-- ================================================== Main Body ================================================== -->



    <!-- ================================================== Footer ===================================================== -->
    <%@ include file="/WEB-INF/fragments/footer.jspf" %>
    <!-- ================================================== Footer ===================================================== -->
</body>
</html>