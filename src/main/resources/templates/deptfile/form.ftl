<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>部门文件</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="frm" method="post" action="${ctx!}/file/edit" enctype="multipart/form-data" >
                        <input type="hidden" id="id" name="id" value="${file.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">文号：</label>
                            <div class="col-sm-8">
                                <input id="number" name="number" class="form-control" value="${bean.description}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">等级：</label>
                            <div class="col-sm-8">
                                <select name="level" class="form-control">
                                    <option value="1" <#if bean.level == 1>selected="selected"</#if>>普通</option>
                                    <option value="2" <#if bean.level == 2>selected="selected"</#if>>重要</option>
                                    <option value="3" <#if bean.level == 3>selected="selected"</#if>>非常重要</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">描述：</label>
                            <div class="col-sm-8">
                                <input id="description" name="description" class="form-control" value="${bean.description}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">发布时间：</label>
                            <div class="col-sm-8">
                                <input id="pushTime" name="pushTime" readonly="readonly" class="laydate-icon form-control layer-date" value="${bean.pushTime}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">简介：</label>
                            <div class="col-sm-8">
                                <input id="remark" name="remark" class="form-control" value="${bean.remark}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">文件：</label>
                            <div class="col-sm-8">
                                <input type="file" name="file" id="file"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>


<!-- 全局js -->
<script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${ctx!}/assets/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx!}/assets/js/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>
<script src="${ctx!}/assets/js/plugins/layer/laydate/laydate.js"></script>
<script src="${ctx!}/assets/js/jquery.form.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        laydate({
            elem: '#pushTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });

        $("#frm").validate({
            rules: {
                number: {
                    required: true
                },
                level: {
                    required: true
                },
                description: {
                    required: true
                },
                pushTime: {
                    date: true,
                    required: true
                },
                telephone: {
                    required: true
                },
                email: {
                    email: true,
                    required: true
                },
                remark: {
                    required: true
                },
                file: {
                    required: true
                }
            },
        messages: {},
        submitHandler:function(form){
            var options = {
                url: "${ctx!}/deptfile/edit",
                type : 'POST',
                dataType : 'json',
                success : function(msg) {
                    layer.msg(msg.message, {time: 2000},function(){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index);
                    });
                },
                error: function(data) {
                    alert("上传失败！");
                }
            };
            $("#frm").ajaxSubmit(options);
        }
    });
    });
</script>

</body>

</html>
