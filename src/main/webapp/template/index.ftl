<#assign ctx=Request.contextPath/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>定时任务监控</title>
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css">
    <script src="${ctx}/static/js/pace.mini.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/themes/blue/pace-theme-minimal.css">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo"></div>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">定时任务管理</a>
                    <dl class="layui-nav-child blog-manage-nav">
                        <dd><a href="javascript:;" id="task-manage" data-type="tabAdd">任务管理</a></dd>
                        <dd><a href="javascript:;" id="task-execute-manage" data-type="tabAdd">执行明细</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body" style="padding: 15px;">
        <div class="layui-tab" lay-allowclose="true" lay-filter="mainTb">
            <ul class="layui-tab-title" id="mainTbUl"></ul>
            <div class="layui-tab-content" style="height: 150px;"></div>
        </div>
    </div>

    <div class="layui-footer">
        ©bluewhale - 吾日三省吾身，为人谋而不忠乎！
    </div>
</div>
<script src="${ctx}/static/layui/layui.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;
        var $ = layui.jquery;

        //第一次加载时，默认打开第一个菜单列表
        firstLoad();

        $(".layui-side-scroll .layui-nav-item").find("a:first").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            if ($(this).parents("li").hasClass("layui-nav-itemed")) {
                $(this).parents("li").removeClass("layui-nav-itemed")
            } else {
                $(this).parents("li").addClass("layui-nav-itemed")
            }
        });

        //菜单点击事件
        $('.blog-manage-nav').find("a").click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var $this = $(this);
            var dataType = $this.attr("data-type");
            if ($this.attr("id") === "task-manage") {
                var content;
                $.ajax({
                    url: '${ctx}/taskManage',
                    type: 'GET',
                    async: true,
                    success: function (data) {
                        content = data;
                        active[dataType] ? active[dataType].call(this, $this, content) : '';
                    }
                });
            } else if ($this.attr("id") === "task-execute-manage") {
                var content;
                $.ajax({
                    url: '${ctx}/taskDetailManage',
                    type: 'GET',
                    async: true,
                    success: function (data) {
                        content = data;
                        active[dataType] ? active[dataType].call(this, $this, content) : '';
                    }
                });
            }
        });

        // 触发事件
        var active = {
            tabAdd: function (othis, content) {
                //清除所有tab
                clearAllTab();

                var tabName = othis.text();
                var tabId = othis.attr("id");
                var $tabli = $("li[lay-id='" + tabId + "']");
                //如果已经存在
                if ($tabli.length > 0) {
                    element.tabDelete('mainTb', id);
                }

                //新增一个Tab项
                element.tabAdd('mainTb', {
                    title: tabName,
                    content: content,
                    id: tabId
                });
                $("li[lay-id='" + tabId + "']").click();
            }
        };


        function firstLoad() {
            var $taskManage = $("#task-manage");
            var content = "";
            if ($taskManage.length > 0) { //部门管理
                $.ajax({
                    url: '${ctx}/taskManage',
                    type: 'GET',
                    async: true,
                    success: function (data) {
                        content = data;
                        active['tabAdd'].call(this, $taskManage, content);
                        $taskManage.parents("dd").eq(0).addClass("layui-this");
                    }
                });
            }
        }

        function clearAllTab() {
            $("#mainTbUl").html("");
            $(".layui-tab-content").html("");
        }
    });
</script>
</body>
</html>