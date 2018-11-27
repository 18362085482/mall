<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<body>
<h2>Hello World!11111</h2>
<h2>Hello World!11111</h2>
<h2>Hello World!11111</h2>

<form name="form1" action="/user/login.do" method="post" enctype="application/x-www-form-urlencoded">
    <input type="text" name="username">
    <input type="password" name="password">
    <input type="submit" value="登录">
</form>

springMVC上传文件
<form name="form2" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="springmvc上传文件">
</form>

富文本图片上传文件
<form name="form3" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="富文本图片上传文件">
</form>

</body>
</html>
