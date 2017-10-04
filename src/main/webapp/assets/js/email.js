$(document).ready(function () {
    $('#email-btn').on('click', function () {
        layer.prompt({title:'邮箱地址'},function (value, index, elem) {
            var postData = {"address": value};
            $.post('/email/uploadEmail', postData, function (retJson) {
                if (retJson.status) {
                    layer.msg(retJson.message);
                } else {
                    layer.msg(retJson.message);
                }
            });
            layer.close(index);
        });
    });
    $('.remove').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        layer.confirm('确定删除吗?', function (index) {
            $.post(url, {}, function (retJson) {
                if (retJson.status) {
                    layer.msg(retJson.message);
                    setTimeout('location.reload()', 1000);
                } else {
                    layer.msg(retJson.message);
                }
            });
            layer.close(index);
        });
    });
});