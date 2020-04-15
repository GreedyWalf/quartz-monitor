<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <%
        String path = request.getContextPath();
        String rootPath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + "/";
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        request.setAttribute("basePath", basePath);
        request.setAttribute("rootPath", rootPath);
        pageContext.setAttribute("newLineChar", "\n");
    %>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <script type="text/javascript" src="<%=basePath %>/Contents/common/jQuery/jquery-1.8.3.min.js"></script>

    <style type="text/css">
        .list_table {
            border: 1px solid #CCCCCC;
            border-collapse: collapse;
            color: #333333;
            margin: 0 0 0;
            width: 100%;
            text-align: center;
        }

        .list_table tbody td {
            border-top: 1px solid #CCCCCC;
            margin: 0 0 0;
            text-align: center;
        }

        .list_table th {
            line-height: 1.2em;
            vertical-align: top;
        }

        .list_table td {
            line-height: 2em;
            font-size: 12px;
            vertical-align: central;
            align: center;
        }

        .list_table td input {
            width: 90%;
        }

        .list_table tbody tr:hover th,.list_table tbody tr:hover td {
            background: #EEF0F2;
        }

        .list_table thead tr {
            background: none repeat scroll 0 0 #09f;
            color: #fff;
            font-weight: bold;
            border-bottom: 1px solid #CCCCCC;
            border-right: 1px solid #CCCCCC;
        }

        .datagrid-mask {
            background: #ccc;
        }

        .datagrid-mask-msg {
            border-color: #95B8E7;
        }

        .datagrid-mask-msg {
            background: #ffffff
            center;
        }

        .datagrid-mask {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            opacity: 0.3;
            filter: alpha(opacity = 30);
            display: none;
        }

        .datagrid-mask-msg {
            position: absolute;
            top: 50%;
            margin-top: -20px;
            padding: 12px 5px 10px 30px;
            width: auto;
            height: 16px;
            border-width: 2px;
            border-style: solid;
            display: none;
        }

    </style>
</head>

<title>定时任务监控</title>
<body class="bgray">
<form id="addForm" method="post">

    <table class="list_table">
        <thead>
        <tr>
            <td>id</td>
            <td style="width: 100px;">name</td>
            <td style="width: 100px;">group</td>
            <td style="width: 100px;">状 态</td>
            <td >cron表达式</td>
            <td style="width: 100px;">描 述</td>
            <td style="width: 100px;">同步否</td>
            <td >类路径</td>
            <td style="width: 100px;">spring id</td>
            <td style="width: 100px;">方法名</td>
            <td style="width: 100px;">操作</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="job" items="${taskList}">
            <tr>
                <td>${job.job_id }</td>
                <td>${job.job_name }</td>
                <td>${job.job_group }</td>
                <td>${job.job_status }
                    <c:choose>
                        <c:when test="${job.job_status=='1' }">
                            <a href="javascript:;"
                               onclick="changeJobStatus('${job.job_id}','stop')">停止</a>&nbsp;
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:;"
                               onclick="changeJobStatus('${job.job_id}','start')">开启</a>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${job.cron_expression }</td>
                <td>${job.description }</td>
                <td>${job.is_concurrent }</td>
                <td>${job.bean_class }</td>
                <td>${job.spring_id }</td>
                <td>${job.method_name }</td>
                <td><a href="javascript:;" onclick="updateCron('${job.job_id}')">更新cron</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td>n</td>
            <td><input type="text" name="jobName" id="jobName"></input></td>
            <td><input type="text" name="jobGroup" id="jobGroup"></input></td>
            <td>0<input type="hidden" name="jobStatus" value="0"></input></td>
            <td><input type="text" name="cronExpression"
                       id="cronExpression"></input></td>
            <td><input type="text" name="description" id="description"></input></td>
            <td><select name="isConcurrent" id="isConcurrent">
                <option value="1">1</option>
                <option value="0">0</option>
            </select></td>
            <td><input type="text" name="beanClass" id="beanClass"></input></td>
            <td><input type="text" name="springId" id="springId"></input></td>
            <td><input type="text" name="methodName" id="methodName"></input></td>
            <td><input type="button" onclick="add()" value="保存" /></td>
        </tr>
        </tbody>
    </table>
</form>
<script>
    function validateAdd() {
        if ($.trim($('#jobName').val()) == '') {
            alert('name不能为空！');
            $('#jobName').focus();
            return false;
        }
        if ($.trim($('#jobGroup').val()) == '') {
            alert('group不能为空！');
            $('#jobGroup').focus();
            return false;
        }
        if ($.trim($('#cronExpression').val()) == '') {
            alert('cron表达式不能为空！');
            $('#cronExpression').focus();
            return false;
        }
        if ($.trim($('#beanClass').val()) == '' && $.trim($('#springId').val()) == '') {
            $('#beanClass').focus();
            alert('类路径和spring id至少填写一个');
            return false;
        }
        if ($.trim($('#methodName').val()) == '') {
            $('#methodName').focus();
            alert('方法名不能为空！');
            return false;
        }
        return true;
    }

    function add() {
        if (validateAdd()) {
            showWaitMsg();
            $.ajax({
                type : "POST",
                async : false,
                dataType : "JSON",
                cache : false,
                url : "${basePath}task/add.do",
                data : $("#addForm").serialize(),
                success : function(data) {
                    hideWaitMsg();
                    if (data.flag) {
                        location.reload();
                    } else {
                        alert(data.msg);
                    }
                }//end-callback
            });//end-ajax
        }
    }

    function changeJobStatus(jobId, cmd) {
        showWaitMsg();
        $.ajax({
            type : "POST",
            async : false,
            dataType : "JSON",
            cache : false,
            url : "${basePath}task/changeJobStatus.do",
            data : {
                jobId : jobId,
                cmd : cmd
            },
            success : function(data) {
                hideWaitMsg();
                if (data.flag) {
                    location.reload();
                } else {
                    alert(data.msg);
                }
            }//end-callback
        });//end-ajax
    }

    function updateCron(jobId) {
        var cron = prompt("输入cron表达式！", "")
        if (cron) {
            showWaitMsg();
            $.ajax({
                type : "POST",
                async : false,
                dataType : "JSON",
                cache : false,
                url : "${basePath}task/updateCron.do",
                data : {
                    jobId : jobId,
                    cron : cron
                },
                success : function(data) {
                    hideWaitMsg();
                    if (data.flag) {
                        location.reload();
                    } else {
                        alert(data.msg);
                    }
                }//end-callback
            });//end-ajax
        }
    }

    function showWaitMsg(msg) {
        if (msg) {

        } else {
            msg = '正在处理，请稍候...';
        }
        var panelContainer = $("body");
        $("<div id='msg-background' class='datagrid-mask' style=\"display:block;z-index:10006;\"></div>").appendTo(panelContainer);
        var msgDiv = $("<div id='msg-board' class='datagrid-mask-msg' style=\"display:block;z-index:10007;left:50%\"></div>").html(msg).appendTo(
            panelContainer);
        msgDiv.css("marginLeft", -msgDiv.outerWidth() / 2);
    }

    function hideWaitMsg() {
        $('.datagrid-mask').remove();
        $('.datagrid-mask-msg').remove();
    }
</script>
</body>
</html>