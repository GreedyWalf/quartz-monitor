<#assign ctx=Request.contextPath/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>定时任务执行列表</title>
</head>

<style>
    .layui-table-view .layui-form-checkbox {
        margin-top: 8px;
    }
</style>

<body>

<div class="demoTable" style="margin-top: 20px;">
    <div class="layui-inline" style="margin-right: 10px;">
        <input style="width: 250px;" class="layui-input" name="taskScheduler.jobName" id="jobName" placeholder="任务名">
    </div>

    执行状态：
    <div class="layui-form layui-inline">
        <select id="status" name="status" lay-verify="required">
            <option value=""></option>
            <option value="0">正在执行</option>
            <option value="1">执行成功</option>
            <option value="2">执行失败</option>
        </select>
    </div>


    <button class="layui-btn" data-type="reload" id="search">搜索</button>

    <div class="layui-row" style="margin-top: 20px;">
        <#--<input type="button" class="layui-btn layui-btn-sm" id="btnAdd" value="新增"/>-->
        <#--<input type="button" class="layui-btn layui-btn-normal layui-btn-sm" id="btnModify" value="修改"/>-->
        <#--<input type="button" class="layui-btn layui-btn-danger layui-btn-sm" id="btnDelete" value="删除"/>-->
    </div>

    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <table class="layui-hide" id="allArticleListTb" lay-filter="article"></table>
        </div>
    </div>
</div>

</body>
</html>

<script src="${ctx}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/layui/layui.js"></script>

<script>
    //加载弹出层组件
    layui.config({
        dir: '${ctx}/static/layui/'
    }).use(['layer', 'form', 'element', 'table', 'upload', 'laydate'], function () {
        var layer = layui.layer;
        var form = layui.form;
        var element = layui.element;
        var table = layui.table;
        var laydate = layui.laydate;
        var $ = layui.$;

        var table_data = [];

        laydate.render({
            elem: '#createStartTime'
        });

        laydate.render({
            elem: '#createEndTime'
        });

        //第一个实例
        table.render({
            elem: '#allArticleListTb',
            height: 500,
            url: '${ctx}/taskDetailList',
            page: true,
            cols: [[
                {checkbox: true, fiexed: true, unresize: true},
                {field: 'detailId', title: '任务明细编号'},
                {field: 'jobId', title: '任务编号'},
                {field: 'jobName', title: '任务名称'},
                {field: 'threadId', title: '线程id'},
                {field: 'startTime', title: '开始时间', width: 200},
                {field: 'endTime', title: '结束时间', width: 200},
                {field: 'totalTime', title: '执行耗时(ms)'},
                {field: 'status', title: '状态', templet: "#statusTb"},
                {field: 'createTime', title: '创建时间', templet: "#createTimeTb"}
            ]],
            id: 'taskSchedulerListTb',
            limits: [5, 10],
            where: {
                'createStartTime': $("#createStartTime").val(),
                'createEndTime': $("#createEndTime").val(),
                'jobName': $("#jobName").val(),
                'status': $("#status").val()
            },
            request: {
                pageName: "pageNum",
                limitName: "pageSize"
            }, done: function (res, curr, count) {
                table_data = res.data;
                console.log("数据渲染成功！");
            }
        });


        //存储选中行的userId
        var selectJobIds = [];
        table.on('checkbox(article)', function (obj) {
            if (obj.checked === true) {
                if (obj.type === 'one') {
                    selectJobIds.push(obj.data.jobId);
                } else {
                    for (var i = 0; i < table_data.length; i++) {
                        selectJobIds.push(table_data[i].userId);
                    }
                }
            } else {
                if (obj.type === 'one') {
                    for (var i = 0; i < selectJobIds.length; i++) {
                        if (selectJobIds[i] === obj.data.jobId) {
                            selectJobIds.remove(selectJobIds[i]);
                        }
                    }
                } else {
                    selectJobIds = [];
                }
            }

            console.log("-->>");
            console.log(selectJobIds);
        });


        //搜索用户
        $("#search").click(function () {
            selectJobIds = [];
            var status = $("#status").val();
            var jobName = $("#jobName").val();
            var createStartTime = $("#createStartTime").val();
            var createEndTime = $("#createEndTime").val();
            table.reload("taskSchedulerListTb", {
                page: {
                    curr: 1
                },
                method: 'post',
                where: {
                    'createStartTime': createStartTime,
                    'createEndTime': createEndTime,
                    'jobName': jobName,
                    'status': status

                }
            });
        });


        //新增
        $("#btnAdd").on('click', function () {
            layer.open({
                title: '添加任务',
                type: 2,
                anim: 1,
                area: ['700px', '600px'],
                content: '${ctx}/showTaskForm',
                resize: false,
                cancel: function () {
                    console.log("-->取消了");
                }
            });
        });

        //修改用户
        $("#btnModify").on('click', function () {
            if (selectJobIds == null || selectJobIds.length <= 0) {
                layer.msg('请选择一行记录', {icon: 2});
                return false;
            }

            if (selectJobIds.length > 1) {
                layer.msg('只能选择一行记录', {icon: 2});
                return false;
            }

            layer.open({
                title: '修改用户',
                type: 2,
                anim: 1,
                area: ['700px', '600px'],
                content: '${ctx}/showTaskForm?jobId=' + selectJobIds[0],
                resize: false,
                cancel: function () {
                    console.log("-->取消了");
                }
            });
        });


        //删除用户
        $("#btnDelete").on('click', function () {
            if (selectJobIds == null || selectJobIds.length === 0) {
                layer.msg('请勾选需要删除的记录', {icon: 2});
                return false;
            }

            $.ajax({
                type: 'POST',
                url: '${ctx}/delete',
                contentType: "application/x-www-form-urlencoded",
                data: {"jobIds": selectJobIds.join(",")},
                success: function (data) {
                    console.log(data);
                    if (data && data.status === "SUCCESS") {
                        layer.msg('删除成功！', {icon: 1, time: 2000}, function () {
                            $("#search").click();
                        });
                    } else {
                        layer.msg(data.msg || '删除出现错误！', {icon: 2, time: 2000});
                    }
                }
            });
        });


        //数组添加remove方法
        Array.prototype.remove = function (val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
    });
</script>


<script type="text/html" id="statusTb">
    {{# if(d.status=='0'){ }}
    <div>正在执行</div>
    {{# }else if(d.status){ }}
    <div style="color:green;">执行成功</div>
    {{# }else{ }}
    <div style="color:red;">执行结束</div>
    {{# } }}
</script>

<script type="text/html" id="createTimeTb">
    {{# if(d.createTime!==null){ }}
    <div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>
    {{# } }}
</script>


<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="view">查看</a>
</script>