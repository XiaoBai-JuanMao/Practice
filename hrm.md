# hrm 基于servlet的人事管理系统

## 设计目标：
1. 按照软件工程流程，完成项目开发和项目考核验收表的撰写。
2. 了解软件开发流程，包括需求分析、系统设计、编码实现和测试等。
3. 掌握基于Servlet、JQuery的开发技术
4. 使用mvc设计模式
5. 掌握druid数据库连接池的使用以及mysql数据库的增删查改

## 系统功能介绍：
### 1. 用户管理功能包括：
    添加用户、(对用户名实现Ajax验证)
    用户可以是管理员或者普通用户;
    查询用户、可以查询所有用户；
    根据用户名和用户状态进行模糊查询;
    删除用户;修改用户（只针对超级管理员有权限操作，如果是普通用户，它只有查看用户数据功能）
### 2. 职位管理功能包括：
    添加职位；查询职位，可以查询所有职位或根据职位名称进行模糊查询；
    删除职位；修改职位
### 3. 员工管理的功能包括：
    添加员工；注意添加身份证号（数据校验(ajax)）
    查询员工，可以查询所有员工或根据员工姓名，身份证号、手机号、性别、职位、部门进行模糊查询；
    删除员工；修改员工。
### 4. 下载中心的功能包括：
    上传文件、查询文件、可以查询所有文件或根据文件标题进行模糊查询、预览文件内容、删除文件、下载文件。

## 设计方案：
    UserDao：提供对user_inf表的基本操作
    DeptDao：提供对dept_inf表的基本操作
    JobDao：提供对job_inf表的基本操作
    EmployeeDao：提供对employee_inf表的基本操作
    DocumentDao：提供对doucment_inf表的基本操作

    Dept和Employee之间存在1对N的关系
    Employee和Dept之间存在N对1的关系
    Employee和Job之间存在N对1的关系
    User和Document之间存在1对N的关系

## 系统框架：
  MVC设计模式，三层架构，servlet

## 实现过程：
  1. 用户登录时，后台通过用户名查询用户，若用户存在且密码正确，把用户存入Session中。
  2. 模糊查询，使用模糊查询语句select * from 表 where 元素 like ?。
  3. 添加操作时验证某个元素是否已存在，使用ajax异步请求验证登录名是否存在。
  4. 添加和修改页面为同一页面，后台通过是否有id参数判断是否为修改页面，并把val返回给前端，前端页面通过判断val值判断哪种操作，若为修改操作，则接受数据并显示。
  5. 修改和删除用户时，通过前端判断用户名是否为’超级管理员’来确定是否能完成该操作。
  6. 上传功能使用表单的二进制提交enctype="multipart/form-data"，后台通过判断是否为普通表单类型isFormField()来分别取字段和文件。
  7. 预览功能通过使用jquery.media.js来实现jdf文件的预览。