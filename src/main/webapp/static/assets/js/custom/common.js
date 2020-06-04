var commonUtil = {
    initTable: function (obj) {
        var id = obj.id || "table";
        var table = $('#' + id).bootstrapTable({
            treeShowField: obj.treeShowField,
            url: obj.url,
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            // striped: true,
            search: obj.search,
            height: obj.height,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            detailView: obj.detailView,
            detailFormatter: commonUtil.detailFormatter,
            pagination: obj.pagination,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'id',
            maintainSelected: true,
            pageSize: obj.pageSize,
            pageList: obj.pageList,
            responseHandler: function (resp) {
                return {
                    "total": resp.total, // 总页数
                    "rows": resp.list // 数据
                };
            },
            columns: obj.columns,
            onClickCell: obj.onClickCell,
            onExpandRow: obj.onExpandRow
        });
        return table;
    },
    // 数据表格展开内容
    detailFormatter: function (index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }
};


//为jquery添加原型方法，用于表单序列化
$.fn.serialiseJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.name);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value]
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });

    return serializeObj;
};