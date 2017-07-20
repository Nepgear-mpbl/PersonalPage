$(document).ready(function () {
    var isPicSelected=false;
    $('#img-input').change(function () {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#preview-img').attr("src", e.target.result);
        };
        reader.readAsDataURL(file);
        isPicSelected=true;
    });
    $('#img-upload-btn').click(function () {
        if(!isPicSelected){
            alert("select image");
            return;
        }
        if($('#title-input').val()===''){
            alert("input title");
            return;
        }
        if($('#intro-input').val()===''){
            alert("input introduction");
            return;
        }
        var formData = new FormData($('#img-upload-form')[0]);
        console.log(formData);
        $.ajax({
            url: 'uploadPicture',
            type: 'POST',
            xhr: function () {
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
                }
                return myXhr;
            },
            beforeSend:function () {
                $('progress').show();
            },
            success: function (retJson) {
                $('progress').hide();
                $('#img-upload-btn').popover({
                    content: retJson.message.toString()
                }).popover('open');
                setTimeout(function(){
                    $('#img-upload-btn').popover('close');
                    location.reload();
                },1000);
            },
            error: function () {
                $('progress').hide();
                alert("fail");
            },
            data: formData,
            cache: false,
            contentType: false,
            processData: false
        });
    });
    function progressHandlingFunction(e) {
        $('progress').attr({value: e.loaded, max: e.total});
    }
});


