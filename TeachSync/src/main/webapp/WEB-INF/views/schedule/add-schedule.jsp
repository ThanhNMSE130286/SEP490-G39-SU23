<%@ page import="com.teachsync.utils.enums.ScheduleType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Thêm lịch học</title>

    <link rel="stylesheet" href="../../../resources/css/bootstrap-5.3.0/bootstrap.css">
    <link rel="stylesheet" href="../../../resources/css/teachsync_style.css">

    <script src="../../../resources/js/jquery/jquery-3.6.3.js"></script>
    <script src="../../../resources/js/bootstrap-5.3.0/bootstrap.bundle.js"></script>
    <script src="../../../resources/js/common.js"></script>
</head>
<body class="container-fluid ts-bg-white-subtle">
<!-- ================================================== Header ===================================================== -->
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<!-- ================================================== Header ===================================================== -->


<!-- ================================================== Main Body ================================================== -->
<div class="row ts-bg-white border ts-border-teal rounded-3 pt-3 mx-2 mb-3">
    <form action="/add-schedule" method="post">

        <div class="row mb-3">
            <div class="col-12">
                <div class="d-flex align-items-center">

                    <p class="mb-0">Tên lớp: <br>
                        ${clazz.clazzName}
                    </p>
                    <input type="hidden" name="clazzId" value="${clazz.id}">

                    <p class="ms-5 mb-0">Tên phòng</p>
                    <div class="dropdown ms-3">
                        <select class="btn btn-secondary dropdown-toggle"
                                id="selRoomId" name="roomId">
                            <c:forEach items="${roomList}" var="room">
                                <option value="${room.id}">${room.roomName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>


        <div class="form-group mb-3">
            <label class="dropdown">Kiểu lịch học: <br>
                <select class="btn btn-secondary dropdown-toggle"
                        id="selScheduleType" name="scheduleType">
                    <option value="${ScheduleType.MON_WED_FRI}">${ScheduleType.MON_WED_FRI.stringValueVie}</option>
                    <option value="${ScheduleType.TUE_THU_SAT}">${ScheduleType.TUE_THU_SAT.stringValueVie}</option>
                    <option value="${ScheduleType.SAT_SUN}">${ScheduleType.SAT_SUN.stringValueVie}</option>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label>Thời gian bắt đầu</label>
            <input type="date" class="form-control" min="${semester.startDate}" max="${semester.endDate}"
                   id="dateStart" name="startDate" placeholder="${semester.startDate}" required>
        </div>

        <div class="form-group">
            <label>Thời gian kết thúc</label>
            <input type="date" class="form-control" min="${semester.startDate}" max="${semester.endDate}"
                   id="dateEnd" name="endDate" required placeholder="${semester.endDate}">
        </div>

        <div class="form-group mb-3">
            <label>Tiết:
                <input type="number" min="1" max="8" class="ms-3" placeholder="Nhập tiết"
                       id="txtSlot"  name="slot" onchange="updateSessionTime()">
            </label>
            <p>Thời gian: <span id="txtSlotStart"></span> - <span id="txtSlotEnd"></span>
            </p>

            <input type="hidden" id="txtSessionStart" name="sessionStart">
            <input type="hidden" id="txtSessionEnd" name="sessionEnd">
        </div>

        <br>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
<!-- ================================================== Main Body ================================================== -->

<!-- ================================================== Footer ===================================================== -->
<%@ include file="/WEB-INF/fragments/footer.jspf" %>
<!-- ================================================== Footer ===================================================== -->

<script>
    const slotStartTime = {
        "1": "07:00",
        "2": "08:45",
        "3": "10:30",

        "4": "12:30",
        "5": "14:15",
        "6": "16:00",

        "7": "18:00",
        "8": "19:45",
    }
    const slotEndTime = {
        "1": "08:30",
        "2": "10:15",
        "3": "12:00",

        "4": "14:00",
        "5": "15:45",
        "6": "17:30",

        "7": "19:30",
        "8": "21:15"
    }

    var mess = '${mess}'
    if (mess != '') {
        alert(mess);
    }

    function updateSessionTime() {
        let slot = $("#txtSlot").val();

        $("#txtSlotStart").text(slotStartTime[slot]);
        $("#txtSlotEnd").text(slotEndTime[slot]);

        $("#txtSessionStart").val(slotStartTime[slot]);
        $("#txtSessionEnd").val(slotEndTime[slot]);
    }
</script>
</body>
</html>