<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html lang="en">
<html>
<head>
  <title>Forgot Passwprd</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <link rel="stylesheet" type="text/css" href="../../resources/css/login_style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
  <script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css"
      integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">

</head>

<body class="body">


<div class="login-page">
  <div class="form">

    <form action="reset_password" method="post">
      <input type="hidden" name="token" value="${param.token}">
      <lottie-player
          src="https://assets4.lottiefiles.com/datafiles/XRVoUu3IX4sGWtiC3MPpFnJvZNq7lVWDCa8LSqgS/profile.json"
          background="transparent" speed="1" style="justify-content: center;" loop autoplay></lottie-player>
      <input type="password" name="password" id="password" placeholder="&#xf007;  password"/>
      <input type="password" placeholder="&#xf023;  new password" oninput="checkPasswordMatch(this);"/>
      <c:if test="${msg ne null}">
        <p class="txt-pink my-2"><c:out value="${msg}"/></p>
      </c:if>
      <br>
      <br>
      <button>Change Password</button>
      <p class="message"></p>
    </form>

  </div>
</div>

<script>
  function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
      fieldConfirmPassword.setCustomValidity("Passwords do not match!");
    } else {
      fieldConfirmPassword.setCustomValidity("");
    }
  }
</script>
</body>
</html>

