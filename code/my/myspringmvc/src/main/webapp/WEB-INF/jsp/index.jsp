<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 2024/11/13
  Time: 9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP页面</title>
</head>
<body>
<h1>手写Springmvc框架</h1>
<h2>用户名：${username}</h2>
<a href="<%=request.getContextPath()%>/detail">订单详情页面</a>
</body>
</html>
