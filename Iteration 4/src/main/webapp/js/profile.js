var $myAddressForm = $('#myAddressForm');
var myRoles = ['<span class="glyphicon glyphicon-user"></span> Regular User', '<span class="glyphicon glyphicon-wrench"></span> 管理员'];

var myProfileDetailAPI = contextPath + '/api/profile/detail';
var updateMyProfileAPI = contextPath + '/api/profile/update';
var getMyAddressAPI = contextPath + '/api/address/all';
var updateMyAddressAPI = contextPath + '/api/address/update';

var myUploader = null;

function updateMyProfileCallback(data, status, newNickname, newAvatar) {
    $('#myProfileStatus').removeClass('hidden');
    if (data.result == 'success') {
        $('#myProfileStatus').html(shortSuccessText('Modify Success！', true));
        setTimeout(function () {
            $('#myProfileDialog').modal('hide');
        }, 1000);
        $('#myNavbarNickname').text(newNickname);
        $('#myNavbarAvatar').attr('src', showUserAvatar(newAvatar));
    }
    else {
        $('#btnUpdateMyProfile').removeAttr('disabled');
        if (data.msg)
            $('#myProfileStatus').html(shortFailText(data.msg, true));
        else if (status != 'success')
            $('#myProfileStatus').html(shortFailText(status, true));
        else
            $('#myProfileStatus').html(shortFailText('Unknown Error', true));
    }
}

function updateMyAddressCallback(data, status) {
    $('#myAddressStatus').removeClass('hidden');
    if (data.result == 'success') {
        $('#myAddressStatus').html(shortSuccessText('Modify Success！'));
        setTimeout(function () {
            $('#myAddressDialog').modal('hide');
        }, 1000);
    }
    else {
        $('#btnUpdateMyAddress').removeAttr('disabled');
        if (data.msg)
            $('#myAddressStatus').html(shortFailText(data.msg));
        else if (status != 'success')
            $('#myAddressStatus').html(shortFailText(status));
        else
            $('#myAddressStatus').html(shortFailText('Unknown Error'));
    }
}

function bindMyFileUploadButton() {
    myUploader = new ss.SimpleUpload({
        button: $('#btnUpdateMyAvatar'),
        url: uploadImageAPI,
        name: 'image',
        maxSize: maxFileSize,
        allowedExtensions: imageExtensions,
        responseType: 'json',
        onSubmit: function () {
            $('#myProfileUploadTips').html(shortProcessingText('Uploading pictures...'));
        },
        onSizeError: function() {
            $('#myProfileUploadTips').html(shortFailText('The size of the picture should be less than 2M'));
        },
        onExtError: function() {
            $('#myProfileUploadTips').html(shortFailText('Only JPG、PNG、BMP、GIF available'));
        },
        onComplete: function (filename, response) {
            uploadCallback($('#myProfileUploadTips'), $('#myAvatar'), $('#myImageShow'), defaultUserAvatar, response);
        },
        onError: function (filename, errorType, status, statusText, response) {
            uploadCallback($('#myProfileUploadTips'), $('#myAvatar'), $('#myImageShow'), defaultUserAvatar, tryJSONParse(response), statusText);
        }
    });
}

function addMyAddressInput() {
    $myAddressForm.append('<div class="form-group address-div"> \
                            <div class="col-sm-11"> \
                                <input type="text" class="form-control address-input" placeholder="Delivery Address"> \
                            </div> \
                            <button type="button" class="btn btn-danger delete-address-btn"><span class="glyphicon glyphicon-trash"></span></button> \
                        </div>');
}

$(document).ready(bindMyFileUploadButton);

$('#myProfileDialog').on('show.bs.modal', function (event) {
    $('#myUsername').val('');
    $('#myPassword').val('');
    $('#myPasswordConfirm').val('');
    $('#myNickname').val('');
    $('#myBalance').text('');
    $('#myRole').text('');
    $('#myAvatar').val('');
    $('#myImageShow').attr('src', defaultUserAvatar);
    $('#myProfileUploadTips').html(supportImgHTML);
    $('#myProfileStatus').removeClass('hidden');
    $('#myProfileStatus').html(shortProcessingText('Loading self-info...', true));
    $('#btnUpdateMyProfile').attr('disabled', 'disabled');
    $.get(myProfileDetailAPI, function (data, status) {
        $('#myUsername').val(data.username);
        $('#myNickname').val(data.nickname);
        $('#myBalance').html('&#65509;' + (data.balance / 100.0).toFixed(2));
        $('#myRole').html(myRoles[parseIntEx(data.role)]);
        $('#myAvatar').val(data.avatar);
        $('#myImageShow').attr('src', showUserAvatar(data.avatar));
        $('#myProfileStatus').addClass('hidden');
        $('#btnUpdateMyProfile').removeAttr('disabled');
    }).fail(function (xhr, status, error) {
        $('#myProfileStatus').html(shortFailText('Fail to load self-info！Error：' + error, true));
    });
});

$('#myProfileDialog').on('shown.bs.modal', function () {
    $('#myUsername').focus();
});

$('#btnDeleteMyAvatar').click(function () {
    $('#myAvatar').val('');
    $('#myImageShow').attr('src', defaultUserAvatar);
    $('#myProfileUploadTips').html(supportImgHTML);
});

$('#btnUpdateMyProfile').click(function () {
    $('#btnUpdateMyProfile').attr('disabled', 'disabled');
    var postData = {
        'username': $('#myUsername').val(),
        'password': $('#myPassword').val(),
        'passwordConfirm': $('#myPasswordConfirm').val(),
        'nickname': $('#myNickname').val(),
        'avatar': $('#myAvatar').val()
    };
    $.post(updateMyProfileAPI, postData, function (data, status) {
        updateMyProfileCallback(data, status, $('#myNickname').val(), $('#myAvatar').val());
    }).fail(function (xhr, status, error) {
        updateMyProfileCallback(tryJSONParse(xhr.responseText), error, $('#myNickname').val(), $('#myAvatar').val());
    });
});

$('#myAddressDialog').on('show.bs.modal', function (event) {
    $('#myAddressStatus').removeClass('hidden');
    $('#myAddressStatus').html(shortProcessingText('Loading delivery address ...'));
    $('.address-div').remove();
    $('#btnUpdateMyAddress').attr('disabled', 'disabled');
    $.get(getMyAddressAPI, function (data, status) {
        $('#myAddressStatus').addClass('hidden');
        if (data.length == 0) {
            addMyAddressInput();
        } else {
            var i = 0;
            for (i = 0; i < data.length; ++i)
                addMyAddressInput();
            i = 0;
            $('.address-input').each(function () {
                $(this).val(data[i]);
                ++i;
            });
        }
        $('#btnUpdateMyAddress').removeAttr('disabled');
    }).fail(function (xhr, status, error) {
        $('#myAddressStatus').html(shortFailText('Fail to load delivery address！Error：' + error));
    });
});

$('#myAddressDialog').on('shown.bs.modal', function () {
    $('.address-input').focus();
});

$('#btnAddMyAddress').click(function () {
    addMyAddressInput();
    $('.address-input').focus();
});

$(document).on('click', '.delete-address-btn', function () {
    $(this).parent().remove();
    $('.address-input').focus();
});

$('#btnUpdateMyAddress').click(function () {
    $('#btnUpdateMyAddress').attr('disabled', 'disabled');
    var addresses = [];
    $('.address-input').each(function () {
        var address = $(this).val().trim();
        if (isNotEmptyString(address))
            addresses.push(address);
    });
    var postData = {'addresses': JSON.stringify(addresses)};
    $.post(updateMyAddressAPI, postData, updateMyAddressCallback).fail(function (xhr, status, error) {
        updateMyAddressCallback(tryJSONParse(xhr.responseText), error);
    });
});