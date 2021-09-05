<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: phili
  Date: 11/24/2020
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    <title>Admin - Home</title>
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

            <div class="container-fluid">
                <form class="user" action="${pageContext.request.contextPath}/getUsers"  name="accounts" method="post" >
                <div class="row text-center justify-content-center align-items-center">
                    <div class="col align-self-center"><button class="btn btn-primary active btn-lg" type="submit">Grab User Table</button></div>
                </div>
                </form>
                <div class="row align-items-center">
                    <% String msg = (String) request.getAttribute("table");
                        out.println(Objects.requireNonNullElse(msg, "")); %>
                </div>

                </div>
            </div>
        </div>
</div>


        <footer class="bg-white sticky-footer"></footer>
    <a class="border rounded d-inline scroll-to-top" href="#page-top"><i class="fas fa-angle-up"></i></a>

<script src="static/js/jquery.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/js/bs-init.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
<script src="static/js/theme.js"></script>
</body>

</html>
