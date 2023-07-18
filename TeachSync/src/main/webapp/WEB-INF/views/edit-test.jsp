<%--
  Created by IntelliJ IDEA.
  User: Tha
  Date: 6/26/2023
  Time: 8:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Edit Test</title>

    <link rel="stylesheet" href="../../resources/css/bootstrap-5.3.0/bootstrap.css">

    <link rel="stylesheet" href="../../resources/css/teachsync_style.css">

    <script src="../../resources/js/jquery/jquery-3.6.3.js"></script>
    <script src="../../resources/js/bootstrap-5.3.0/bootstrap.bundle.js"></script>

    <script src="../../resources/js/common.js"></script>
    <style>
        /* Áp dụng CSS để tùy chỉnh giao diện form */
        .container {
            display: flex;
            width: 100%;
        }

        .left {
            width: 50%;
            box-sizing: border-box;
            padding: 10px;
        }

        .right {
            width: 50%;
            box-sizing: border-box;
            padding: 10px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        table, th, td {
            border: 1px solid black;
            padding: 5px;
        }

        /* Additional styles for better appearance */
        h1 {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-top: 10px;
        }

        select, input[type="number"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
            resize: vertical;
        }

        #optionsContainer {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/fragments/header.jspf" %>
<h1>Sửa bài test</h1>
<label for="question-type">Môn học:</label>
<input type="hidden" name="idTest" id="idTest">
<select name="courseName">
    <c:forEach var="item" items="${lstCourse}">
        <option value="${item.id}" ${item.id == test.courseId ? 'selected' : ''} >${item.courseName}</option>
    </c:forEach>
</select>
<label for="question-type">Loại kiểm tra:</label>
<select name="testType">
    <option value="15min"  ${test.testName == '15min' ? 'selected' : ''}>15 phút</option>
    <option value="midterm" ${test.testName == 'midterm' ? 'selected' : ''}>Giữa kỳ</option>
    <option value="final" ${test.testName == 'final' ? 'selected' : ''}>Cuối kỳ</option>
</select>
<label>Thời gian:</label>
<input type="number" id="timeLimit" name="timeLimit" min="1" value="${test.timeLimit}" required>
<label for="question-type">Loại câu hỏi:</label>
<select id="question-type" name="questionType" disabled>
    <option value="essay" ${test.testType eq 'essay' ? 'selected' : ''}>Tự luận</option>
    <option value="multipleChoice" ${test.testType eq 'multipleChoice' ? 'selected' : ''}>Trắc nghiệm</option>
</select>
<div class="container">
    <div class="left">
        <table>
            <thead>
            <tr>
                <th>Câu hỏi</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="qs" items="${questionAnswer}">
                <tr onclick="displayQuestion('${qs.key.questionDesc}', '${qs.key.id}', '${test.testType}')">
                    <td>${qs.key.questionDesc}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="right">
        <form action="updateAnswer" method="post">
            <!-- Content for the right div -->
            <label>Câu hỏi:</label>
            <textarea id="multipleChoiceQuestion" name="questionAll"></textarea>
            <input type="hidden" name="idQuestion" id="idQuestion">
            <br>
            <div id="checkEssay" style="display: none">
                <label for="numOfOptions">Số lượng đáp án:</label>
                <input type="number" id="numOfOptions" name="numOfOptions" min="2" max="10"
                       onchange="createMultipleChoiceOptions()">

                <div id="optionsContainer"></div>
            </div>


            <input type="submit" value="Submit">
        </form>
    </div>
</div>

<script>

    var questionsArea = document.getElementById("multipleChoiceQuestion");
    var numIp = document.getElementById("numOfOptions");
    var idQuestion = document.getElementById("idQuestion");

    function displayQuestion(questionDesc, idQuestion, type) {
        questionsArea.innerHTML = questionDesc;
        idQuestion.value = idQuestion;
        var element = document.getElementById("checkEssay");
        if (type == "multipleChoice") {
            element.style.display = "block";
        } else {
            element.style.display = "none";
        }
    }

    function createMultipleChoiceOptions() {
        const container = document.getElementById('optionsContainer');
        container.innerHTML = ''; // Xóa nội dung hiện tại trong container

        const numOfOptions = document.getElementById('numOfOptions').value;

        for (let i = 1; i <= numOfOptions; i++) {
            const optionDiv = document.createElement('div');
            optionDiv.classList.add('option');

            const answerLabel = document.createElement('label');
            answerLabel.textContent = 'Đáp án ' + i + ': ';

            const answerInput = document.createElement('input');
            answerInput.type = 'text';
            answerInput.name = 'answer' + i;

            const answerCheckbox = document.createElement('input');
            answerCheckbox.type = 'checkbox';
            answerCheckbox.name = 'correctAnswer'+i;

            optionDiv.appendChild(answerLabel);
            optionDiv.appendChild(answerInput);
            optionDiv.appendChild(answerCheckbox);

            container.appendChild(optionDiv);
        }
    }
</script>
</body>
</html>
