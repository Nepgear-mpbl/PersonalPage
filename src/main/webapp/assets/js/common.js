$(document).ready(function () {
    $('#guset-name-btn').on('click', function () {
        layer.prompt({title:'喜欢的id就行'},function (value, index, elem) {
            var postData = {"name": value};
            $.post('/guest', postData, function (retJson) {
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