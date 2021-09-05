<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login</title>
      <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
      <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
      <link rel="stylesheet" href="static/fonts/fontawesome-all.min.css">

  </head>
  <style>
      label {
          width: 300px;
          font-weight: bold;
          display: inline-block;
          margin-top: 20px;
      }

      label span {
          font-size: 1rem;
      }
      label.error {
          color: red;
          font-size: 1rem;
          display: block;
          margin: 5px 1% 1%;
          align-self: flex-start;
      }

      input.error {
          margin: 1.5%;
          align-self: center;
          border: 1px dashed red;
          font-weight: 300;
          color: red;
      }
  </style>

  <body class="bg-gradient-primary">
  <div class="container">
      <div class="row justify-content-center">
          <div class="col-md-9 col-lg-12 col-xl-10">
              <div class="card shadow-lg o-hidden border-0 my-5">
                  <div class="card-body p-0">
                      <div class="row">
                          <div class="col-lg-6 d-none d-lg-flex">
                              <div class="flex-grow-1 bg-login-image" style="background-image: url(https://www.bleepstatic.com/content/posts/2020/09/07/Newcastle-University-headpic.jpg);"></div>
                          </div>
                          <div class="col-lg-6">
                              <div class="p-5">
                                  <div class="text-center">
                                      <h4 class="text-dark mb-4">Welcome Back!</h4>
                                  </div>

                                  <form class="user" action="${pageContext.request.contextPath}/UserLogin"  name="login" method="post">
                                      <div class="form-group"><label for="username"></label><input class="form-control form-control-user" type="text" id="username" aria-describedby="Enter Username" placeholder="Username" name="username" required></div>
                                      <div class="form-group"><label for="password"></label><input class="form-control form-control-user" type="password" id="password" placeholder="Password" name="password" required></div>
                                      <button class="btn btn-primary btn-block text-white btn-user" type="submit">Login</button>
                                      <hr>
                                  </form>

                                  <div class="text-center"><a class="small" href="index.jsp">Dont Have an account? Register Here!</a></div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  </div>

  <script src="static/js/jquery.min.js"></script>
  <script src="static/bootstrap/js/bootstrap.min.js"></script>
  <script src="static/js/chart.min.js"></script>
  <script src="static/js/bs-init.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
  <script src="static/js/theme.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>

  <script>
      $(function() {
          $.validator.addMethod("checkUpper", function(value, element) {
              if (this.optional(element)) {
                  return true;
              }
              return /[A-Z]/.test(value);
          }, "Please enter a valid password");

          $.validator.addMethod("checkLower", function(value, element) {
              if (this.optional(element)) {
                  return true;
              }
              return /[a-z]/.test(value);
          }, "Please enter a valid password");

          $("form[name='login']").validate({
              rules: {
                  username: {required: true},
                  password: {
                      required: true,
                      minlength: 8,
                      maxlength: 15,
                      checkUpper: true,
                      checkNumber: true,
                      checkLower: true
                  },
              },
              // Specify validation error messages
              messages: {
                  username: "Username is Required",
                  password: {
                      required: "Please provide a password",
                      minlength: "Please enter a valid password",
                      maxlength: "Please enter a valid password",
                  },
              },
              submitHandler: function(form) {
                  form.submit('/UserLogin');
              }
          });
      });
  </script>


  </body>
</html>
