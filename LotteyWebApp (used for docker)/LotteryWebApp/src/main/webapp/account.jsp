<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="static/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="static/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="static/fonts/fontawesome5-overrides.min.css">
</head>


<body id="page-top">
<div id="wrapper">
    <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
            <nav class="navbar navbar-light navbar-expand bg-primary shadow mb-4 topbar static-top">
                <div class="container-fluid"><button class="btn btn-link d-md-none rounded-circle mr-3" id="sidebarToggleTop" type="button"><i class="fas fa-bars"></i></button>
                    <ul class="nav navbar-nav flex-nowrap ml-auto">
                        <li class="nav-item dropdown d-sm-none no-arrow"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#"><i class="fas fa-search"></i></a>
                            <div class="dropdown-menu dropdown-menu-right p-3 animated--grow-in" aria-labelledby="searchDropdown">
                            </div>
                        </li>


                        <li class="nav-item dropdown no-arrow">
                            <div class="nav-item dropdown no-arrow"><a class="dropdown-toggle nav-link" data-toggle="dropdown" aria-expanded="false" href="#"><span class="d-none d-lg-inline mr-2 text-white small"><%=session.getAttribute("firstname")%> <%=session.getAttribute("lastname") %></span></a>
                                <div class="dropdown-menu shadow dropdown-menu-right animated--grow-in"><a class="dropdown-item" href="#"><strong><%=session.getAttribute("email")%></strong></a>
                                    <div class="dropdown-divider"></div>
                                    <form class="user" action="${pageContext.request.contextPath}/UserLogin"  name="login" method="post">
                                        <button class="btn btn-primary active btn-block btn-sm text-center text-primary" type="submit" style="background: rgba(78,115,223,0);color: rgb(0,0,0);border-style: none;"><i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout</button>
                                        <input type="hidden" name="sign_out" value="sign_out"/>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>


            <div class="row justify-content-end" style="margin: 2%">
                <div class="col-auto">
                    <div class="alert alert-success" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button><span><strong>Successfully&nbsp;</strong>Logged in.</span></div>
                </div>

                <div class="col-auto">
                    <% StringBuilder msg = (StringBuilder) request.getAttribute("infoResponse");
                        out.println(Objects.requireNonNullElse(msg, "")); %>
                </div>
            </div>

            <form class="user" action="${pageContext.request.contextPath}/AddUserNumbers" method="post" >
                <div class="row justify-content-center align-items-center">
                    <div class="col-auto"><label for="num1"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num1" id="num1" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                    <div class="col-auto"><label for="num2"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num2" id="num2" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                    <div class="col-auto"><label for="num3"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num3" id="num3" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                    <div class="col-auto"><label for="num4"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num4" id="num4" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                    <div class="col-auto"><label for="num5"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num5" id="num5" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                    <div class="col-auto"><label for="num6"></label><input class="border rounded-0 border-primary shadow-sm form-control-lg" name="num6" id="num6" type="number" required style="width: 89px;" min="0" max="60" placeholder="0"></div>
                </div>


            <div class="row justify-content-center" style="margin: 2%;">
                <div class="col-auto"><button  onclick="myFunction()" type="button" class="btn btn-primary active btn-lg">Lucky Dip</button></div>
            <div class="col-auto"><button class="btn btn-primary active btn-lg" type="submit">Submit Numbers</button></div>
        </div>
            </form>

            <form class="user" action="${pageContext.request.contextPath}/GetUserNumbers" method="post" >
            <div class="row justify-content-center align-items-center">
                <div class="col text-center align-self-center"><button class="btn btn-primary active btn-lg" type="submit">Get Draws</button></div>
            </div>
            </form>

            <div class="row no-gutters justify-content-center align-items-center" style="margin: 2%;">
                <div class="col align-self-center" style="min-width: 30%;max-width: 30%;">

                    <% StringBuilder tab = (StringBuilder) request.getAttribute("lot_table");
                        out.println(Objects.requireNonNullElse(tab, "")); %>

                </div>
            </div>

            <form class="user" action="${pageContext.request.contextPath}/GetUserNumbers" method="post" >
            <div class="row justify-content-center align-items-center">
                <div class="col-auto text-center align-self-center my-auto">
                    <button class="btn btn-success btn-icon-split" type="submit"><span class="text-white-50 icon"><i class="fas fa-check"></i></span><span class="text-white text">Check Numbers</span></button></div>
                <input type="hidden" name="check_lottery" value="check_lottery"/>
            </div>
            </form>

        </div>
        </div>
    </div>

<script src="static/js/jquery.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
<script src="static/js/theme.js"></script>


<script>
    function randomInteger() {
        return Math.floor(Math.random() * (60 + 1));
    }

    function myFunction() {
        document.getElementById("num1").value = randomInteger();
        document.getElementById("num2").value = randomInteger();
        document.getElementById("num3").value = randomInteger();
        document.getElementById("num4").value = randomInteger();
        document.getElementById("num5").value = randomInteger();
        document.getElementById("num6").value = randomInteger();
    }
</script>
</body>
</html>
