<#assign ctx=Request.contextPath/>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>新增用户</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/layui/css/layui.css">
</head>

<style>
    .layui-form-label {
        width: 150px;
    }

    .layui-input-block {
        margin-left: 200px;
    }
</style>

<body>
<div class="layui-row" style="margin-top: 20px;">
    <div class="layui-col-xs11">
        <form class="layui-form" id="addDeptForm">
            <div class="layui-form-item">
                <label class="layui-form-label">任务编号：</label>
                <div class="layui-input-block">
                    <input type="text" name="jobId" value="${(taskScheduler.jobId)!}" <#if taskScheduler??>disabled style="background:#eee;"</#if> required lay-verify="required" placeholder="请输入用户编号" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">任务名称：</label>
                <div class="layui-input-block">
                    <input type="text" name="jobName" value="${(taskScheduler.jobName)!}" <#if taskScheduler??>disabled style="background:#eee;</#if> required lay-verify="required" placeholder="请输入任务名称" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">执行类全路径：</label>
                <div class="layui-input-block">
                    <input type="text" name="beanClass" value="${(taskScheduler.beanClass)!}" <#if taskScheduler??>disabled style="background:#eee;</#if> placeholder="请输入执行目标类" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">执行类实例名：</label>
                <div class="layui-input-block">
                    <input type="text" name="springId" value="${(taskScheduler.springId)!}" <#if taskScheduler??>disabled style="background:#eee;</#if> placeholder="请输入执行类实例名" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">目标方法：</label>
                <div class="layui-input-block">
                    <input type="text" name="methodName" value="${(taskScheduler.methodName)!}" <#if taskScheduler??>disabled style="background:#eee;</#if> placeholder="请输入执行目标方法" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">定时任务表达式：</label>
                <div class="layui-input-block">
                    <input type="text" name="cronExpression" value="${(taskScheduler.cronExpression)!}" placeholder="请输入执行目标方法" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">支持并发：</label>
                <div class="layui-input-block">
                    <select name="isConcurrent" lay-verify="required">
                        <option value=""></option>
                        <option value="0" <#if taskScheduler?? && taskScheduler.isConcurrent=='0'>selected</#if> >是</option>
                        <option value="1" <#if taskScheduler?? && taskScheduler.isConcurrent=='1'>selected</#if> >否</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">任务描述：</label>
                <div class="layui-input-block">
                    <textare id="description" name="description" placeholder="请输入任务描述" autocomplete="off"  class="layui-textarea"></textare>
                </div>
            </div>

            <div class="layui-form-item" style="text-align: center; margin-top: 65px;">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" type="button" id="btnEnter" style="margin-right: 30px;">
                        确定
                    </button>
                    <button type="reset" class="layui-btn layui-btn-primary" style="margin-left: 20px;">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="${ctx}/static/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-3.2.1.min.js"></script>
<script>
    //加载弹出层组件
    layui.config({
        dir: '${ctx}/static/layui/'
    }).use(['layer', 'form', 'element', 'upload'], function () {
        var layer = layui.layer;
        var form = layui.form;
        var element = layui.element;
        var parent$ = window.parent.layui.jquery;
        var $ = layui.$;
        var upload = layui.upload;

        //保存或修改任务
        $("#btnEnter").click(function () {
            var $deptForm = $("#addDeptForm");
            var jobId = $deptForm.find("input[name='jobId']").val();
            var jobName = $deptForm.find("input[name='userName']").val();
            var beanClass = $deptForm.find("input[name='beanClass']").val();
            var springId = $deptForm.find("input[name='springId']").val();
            var methodName = $deptForm.find("input[name='methodName']").val();
            var cronExpression = $deptForm.find("input[name='cronExpression']").val();
            var isConcurrent = $deptForm.find("input[name='isConcurrent']:checked").val();
            var description = $("#description").val();


            var param = {
                jobId: jobId,
                jobName: jobName,
                beanClass: beanClass,
                springId: springId,
                methodName: methodName,
                cronExpression: cronExpression,
                isConcurrent: isConcurrent,
                description: description
            };

            $.post('${ctx}/saveOrUpdateJob', param, function (data) {
                if (data && data.status === "SUCCESS") {
                    layer.msg('保存成功！', {icon: 1, time: 1000}, function () {
                        //点击搜索重新加载页面
                        parent.$("#search").click();
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    });
                } else {
                    layer.msg(data.msg || '保存出现错误！', {icon: 2, time: 2000});
                }
            });
        });
    })
</script>
</body>

</html>