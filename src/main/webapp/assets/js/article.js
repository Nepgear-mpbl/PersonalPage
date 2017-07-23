$(document).ready(function () {
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