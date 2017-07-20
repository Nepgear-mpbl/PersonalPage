$(document).ready(function () {
    $('#guset-name-btn').on('click', function () {
        $('#guest-name-input').modal({
            relatedTarget: this,
            dimmer:false,
            onConfirm: function (e) {
                if (e.data !== '') {
                    $.post('guest/' + e.data.toString(), {}, function (retJson) {
                        if (retJson.status) {
                            location.reload();
                        } else {
                            alert("Unknown Error!");
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