var uploadImageAPI = contextPath + '/api/upload';
var maxFileSize = 2048;
var imageExtensions = ['jpg', 'jpeg', 'png', 'bmp' ,'gif'];
var supportImgHTML = '<span class="glyphicon glyphicon-info-sign"></span>  JPG、PNG、BMP、GIF Available，no more than 2M';

function uploadCallback(msgElement, inputField, imgShow, defaultImg, response, statusText) {
    if (response.result == 'success') {
        msgElement.html(shortSuccessText('Upload Success!'));
        inputField.val(response.param);
        imgShow.attr('src', uploadedImage(response.param));
    } else {
        inputField.val('');
        imgShow.attr('src', defaultImg);
        msgElement.html(shortFailText(response.msg || statusText || 'Upload Failure'));
    }
}
