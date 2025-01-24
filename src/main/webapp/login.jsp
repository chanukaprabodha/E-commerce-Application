<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>TechSphere - Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css" rel="stylesheet">
  <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%
  String message = (String) request.getParameter("message");
  String error = (String) request.getParameter("error");
%>

<%
  if (message != null) {
%>

<div style="color: green"><%= message %></div>

<%}%>

<%
  if (error != null) {
%>

<div style="color: red"><%= error %></div>

<%}%>

<div class="container" id="container">
  <div class="form-container sign-up-container">
    <form action="login" method="post">
      <h1>Create Account</h1>
      <span>or use your email for registration</span>
      <input type="hidden" name="action" value="register" />
      <input type="text" id="userName" name="userName" placeholder="Name" />
      <input type="email" id="email" name="email" placeholder="Email" />
      <input type="password" id="password" name="password" placeholder="Password" />
      <select id="role" name="role">
        <option value="" disabled selected>Role</option>
        <option value="customer">Customer</option>
        <option value="admin">Admin</option>
      </select>
      <button>Sign Up</button>
    </form>
  </div>

  <div class="form-container sign-in-container">
    <form action="login" method="post">
      <h1>Sign in</h1>
      <div class="social-container">
        <a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
        <a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
        <a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
      </div>
      <span>or use your account</span>
      <input type="hidden" name="action" value="login" />
      <input type="text" id="un" name="un" placeholder="UserName" />
      <input type="password" id="pw" name="pw" placeholder="Password" />
      <a href="#">Forgot your password?</a>
      <button>Sign In</button>
    </form>
  </div>
  <div class="overlay-container">
    <div class="overlay">
      <div class="overlay-panel overlay-left">
        <h1>Welcome Back!</h1>
        <p>
          To keep connected with us please login with your personal info
        </p>
        <button class="ghost" id="signIn">Sign In</button>
      </div>
      <div class="overlay-panel overlay-right">
        <h1>Hello, Friend!</h1>
        <p>Enter your personal details and start journey with us</p>
        <button class="ghost" id="signUp">Sign Up</button>
      </div>
    </div>
  </div>
</div>
<script>
  const signUpButton = document.getElementById('signUp');
  const signInButton = document.getElementById('signIn');
  const container = document.getElementById('container');

  signUpButton.addEventListener('click', () => {
    container.classList.add('right-panel-active');
  });

  signInButton.addEventListener('click', () => {
    container.classList.remove('right-panel-active');
  });
</script>
</body>
</html>