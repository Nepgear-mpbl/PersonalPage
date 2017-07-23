$(document).ready(function () {
    $('#guset-name-btn').on('click', function () {
        $('#guest-name-input').modal({
            relatedTarget: this,
            dimmer:false,
            onConfirm: function (e) {
                var postData={"name":e.data};
                if (e.data !== '') {
                    $.post('/guest', postData, function (retJson) {
                        if (retJson.status) {
                            layer.msg(retJson.message);
                            setTimeout('location.reload()', 1000);
                        } else {
                            layer.msg(retJson.message);
                        }
                    });
                }else{
                }
            },
            onCancel: function (e) {
            }
        });
    });
});