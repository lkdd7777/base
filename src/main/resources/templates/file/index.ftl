<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>公共文件</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>列表</h5>
                </div>
                <div class="ibox-content">
                    <p>
                        	<#--<@shiro.hasPermission name="web:file:add">-->
                                <button class="btn btn-success " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button>
                            <#--</@shiro.hasPermission>-->
                    </p>
                    <hr>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Card View -->
                            <div class="example-wrap">
                                <div class="example">
                                    <table id="table_list"></table>
                                </div>
                            </div>
                            <!-- End Example Card View -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

<!-- Bootstrap table -->
<script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${ctx!}/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- Peity -->
<script src="${ctx!}/assets/js/plugins/peity/jquery.peity.min.js"></script>

<script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>



<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        //初始化表格,动态从服务器加载数据
        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/file/list",
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: true,
            //每页显示的记录数
            pageSize: 10,
            //当前第几页
            pageNumber: 1,
            //记录数可选列表
            pageList: [5, 10, 15, 20, 25],
            //是否启用查询
            search: true,
            //是否启用详细信息视图
            detailView:true,
            detailFormatter:detailFormatter,
            //表示服务端请求
            sidePagination: "server",
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [{
                title: "文件名",
                field: "name",
                formatter: function (value, row, index) {
                    console.info(row);
                    return value +row.suffix;
                }
            },{
                title: "文号",
                field: "number"
            },{
                title: "等级",
                field: "level",
                formatter: function (value, row, index) {
                    if (value == 1)
                        return '<span class="badge badge-primary">普通</span>';
                    else if(value == 2)
                        return '<span class="badge badge-warning">重要</span>';
                    else if(value == 3)
                        return '<span class="badge label-danger">非常重要</span>';
                }
            },{
                title: "发布时间",
                field: "pushTime",
            },{
                title: "上传时间",
                field: "createTime",
            },{
                title: "上传人",
                field: "user",
                formatter: function(value, row, index) {
                    return value.nickName;;
                }
            },{
                title: "描述",
                field: "description",
            },{
                title: "预览",
                field: "route",
                formatter: function(value, row, index) {
                    //return "<a href='${ctx!}/"+value+"' target = '_blank'>查看</a>";
                    return "<a href='#' onclick='view(\""+value+"\")'>查看</a>";
                }
            },{
                title: "操作",
                field: "empty",
                formatter: function (value, row, index) {
                    var operateHtml = '<@shiro.hasPermission name="web:file:download"><button class="btn btn-primary btn-xs" type="button" onclick="download(\''+row.id+'\')"><i class="fa fa-edit"></i>&nbsp;下载</button> &nbsp;</@shiro.hasPermission>';
                    operateHtml = operateHtml + '<@shiro.hasPermission name="web:file:deleteBatch"><button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.id+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;</@shiro.hasPermission>';
                    return operateHtml;
                }
            }]
        });
    });

    function download(id){
        var url = '${ctx!}/file/download/'+id;
        window.open(url);
    }
    function add(){
        layer.open({
            type: 2,
            title: '文件添加',
            shadeClose: true,
            shade: false,
            area: ['893px', '600px'],
            content: '${ctx!}/file/add',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    function del(id){
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx!}/file/logicDelete/" + id,
                success: function(msg){
                    layer.msg(msg.message, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>备注:</b> ' + row.remark + '</p>');
        return html.join('');
    }

    function view(value){
        var fileUrl = "http://127.0.0.1:8080/"+value;
        var url = "http://127.0.0.1:8012/onlinePreview?url=" + encodeURIComponent(fileUrl);

        var winHeight = window.document.documentElement.clientHeight-10;
        window.open(url, "_blank", "height=" + winHeight
                + ",top=80,left=80,toolbar=no, menubar=no, scrollbars=yes, resizable=yes");
    }
</script>




</body>

</html>
