<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: phili
  Date: 10/25/2020
  Time: 7:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Registration</title>
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
        margin: 1%;
        color: red;
        font-size: 1rem;
        display: block;
        margin-top: 5px;
        align-self: flex-start;
    }

    input.error {
        margin: 1%;
        align-self: center;
        border: 1px dashed red;
        font-weight: 300;
        color: red;
    }
</style>

<body class="bg-gradient-primary">
<div class="container">
    <div class="card shadow-lg o-hidden border-0 my-5">
        <div class="card-body p-0">
            <div class="row">
                <div class="col-lg-5 d-none d-lg-flex">
                    <div class="flex-grow-1 bg-register-image" style="background-image: url(https://netimesmagazine.co.uk/wp-content/uploads/2020/04/NU-SDGs-1024x1024.jpg);"></div>
                </div>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h4 class="text-dark mb-4">Create an Account!</h4>
                        </div>
                        <form class="user" action="${pageContext.request.contextPath}/CreateAccount"  name="register" method="post" >

                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0"><label for="firstname"></label><input class="form-control form-control-user" type="text" id="firstname" placeholder="First Name" name="firstname" required></div>

                                <div class="col-sm-6"><label for="lastname"></label><input class="form-control form-control-user" type="text" id="lastname" placeholder="Last Name" name="lastname" required></div>
                        </div>

                            <div class="form-group row">
                                <div class="col-sm-6 mb-3 mb-sm-0"><label for="username"></label><input class="form-control form-control-user" type="text" id="username" placeholder="Username" name="username" required></div>
                                <div class="col-sm-6"><label for="phone"></label><input class="form-control form-control-user" type="tel"  id="phone" placeholder="Phone Number" name="phone" required></div>
                            </div>
                            <div class="row">
                                <% String msg = (String) request.getAttribute("message");
                                    out.println(Objects.requireNonNullElse(msg, "")); %>
                            </div>

                            <div class="form-group"><label for="email"></label><input class="form-control form-control-user" type="email" id="email" aria-describedby="email" placeholder="Email Address" name="email" required></div>

                            <div class="form-group">
                                <label for="password"></label><input  class="form-control form-control-user" type="password" id="password" placeholder="Password" name="password" required>
                                <div data-toggle="tooltip" class="custom-control text-primary custom-switch" title="Turn on for admin account"><input type="checkbox" class="custom-control-input" name="acc_type" id="acc_type" /><label class="custom-control-label" for="acc_type">Admin Account</label></div>

                            </div>

                            <button class="btn btn-primary btn-block text-white btn-user" type="submit">Register Account</button>
                            <hr>
                        </form>

                        <div class="text-center"><a class="small" href="login.jsp">Already have an account?</a></div>
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
        }, "Must contain an Uppercase");

        $.validator.addMethod("checkLower", function(value, element) {
            if (this.optional(element)) {
                return true;
            }
            return /[a-z]/.test(value);
        }, "Must contain a LowerCase");

        $.validator.addMethod("checkNumber", function(value, element) {
            if (this.optional(element)) {
                return true;
            }
            return /[0-9]/.test(value);
        }, "At Least 1 Digit");
        $.validator.addMethod("checkEmail", function(value, element) {
            if (this.optional(element)) {
                return true;
            }
            return /^([a-zA-Z0-9_.+-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value);
        }, "Please enter A valid Email");


        $.validator.addMethod("checkTel", function(value, element) {
            if (this.optional(element)) {
                return true;
            }
            return /[0-9]{2}-[0-9]{4}-[0-9]{7}/.test(value);
        }, "Enter number in form xx-xxxx-xxxxxxx");

        $("form[name='register']").validate({
            rules: {
                firstname: "required",
                lastname: "required",

                email: {
                    required: true,
                    email: true,
                    checkEmail: true
                },
                password: {
                    required: true,
                    minlength: 8,
                    maxlength: 15,
                    checkUpper: true,
                    checkNumber: true,
                    checkLower: true
                },
                phone:{
                    required: true,
                    checkTel: true
                }

            },
            // Specify validation error messages
            messages: {
                firstname: "Firstname is Required",
                lastname: "Lastname is Required",
                username: "Username is Required",



                password: {
                    required: "Please provide a password",
                    minlength: "At least 8 characters long",
                    maxlength: "Up to 15 characters",

                },
                email: "Please enter a valid email address"
            },

            submitHandler: function(form) {
                form.submit('/CreateAccount');
            }
        });
    });
</script>


</body>
</html>
