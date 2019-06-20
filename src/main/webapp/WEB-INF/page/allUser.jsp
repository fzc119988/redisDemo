<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%pageContext.setAttribute("path", request.getContextPath());%>

<!DOCTYPE HTML>
<html>
<head>
<title>用户列表</title>
<link
    href="${path}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
    rel="stylesheet">
<script type="text/javascript"
    src="${path}/static/bootstrap-3.3.7-dist/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript"
    src="${path}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>

<body>

    <div class="container">
        <!-- 标题 -->
        <div class="row">
            <div class="col-md-12">
                <h1>用户管理</h1>
            </div>
        </div>
        <!-- 按钮 -->
        <div class="row">
            <div class="col-md-4 col-md-offset-8">
                <a class="btn btn-primary" href="${path}/user/toAddUser">新增</a>
            </div>
        </div>
        <!-- 表格  -->
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>id</th>
                        <th>用户名</th>
                        <th>年龄</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach items="${pageInfo.list }" var="user">
                        <tr>
                        <td>${user.id}</td>
                        <td>${user.username }</td>
                            <td>${user.age }</td>
                            <!-- <td><a  class="edit">编辑</a> <a
                                 class="delete">删除</a></td> -->
                            <td>
                                <a type="button"  href="${path}/user/getUser?id=${user.id}" class="btn btn-info btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                    编辑</a>
                                <a type="button"  href="${path}/user/delUser?id=${user.id}" class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>
                                    删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        
</body>
</html>