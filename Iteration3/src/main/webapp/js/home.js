$container = $('#bookContainer');

var allBooksAPI = contextPath + '/api/books/all';
var searchBookAPI = contextPath + '/api/books/search';
var bookDetailAPI = contextPath + '/api/books/detail';
var addToCartAPI = contextPath + '/api/cart/add';

function addBookBlock(id, name, image, price) {
    $container.append('<div class="col-xs-6 col-sm-4 col-md-3" id="book-' + id + '"> \
            <div class="thumbnail"> \
                <img class="book-image" src="' + showBookImage(image) + '"> \
                <div class="caption"> \
                    <h4>' + escapeHtml(name) + '</h4> \
                    <p>&#65509;' + parseFloat(price).toFixed(2) + '</p> \
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#detailDialog" data-id="' + id + '">查看详情</button> \
                </div> \
            </div> \
        </div>');
}

function addToCartCallback(data, status) {
    $('#detailStatus').removeClass('hidden');
    if (data.result == 'success') {
        $('#detailStatus').html(shortSuccessText('Add Success！', true));
        setTimeout(function () {
            $('#detailDialog').modal('hide');
        }, 1000);
        updateCartCount();
    }
    else {
        $('#btnAddToCart').removeAttr('disabled');
        if (data.msg)
            $('#detailStatus').html(shortFailText(data.msg, true));
        else if (status != 'success')
            $('#detailStatus').html(shortFailText(status, true));
        else
            $('#detailStatus').html(shortFailText('Unknown Error', true));
    }
}

$(document).ready(function () {
    updateCartCount();
    $.get(allBooksAPI, function (data, status) {
        var bookCount = data.length;
        for (var i = 0; i < bookCount; ++i) {
            addBookBlock(data[i][0], data[i][1], data[i][2], data[i][3] / 100.0);
        }
        $('.alert-info').alert('close');
    }).fail(function (xhr, status, error) {
        $('.alert').remove();
        $('#bookMainContainer').append(errorAlertHTML('Fail to load information！<br>Error：' + error));
    });
});

function doSearch() {
    var category = $('#selectCategory').val();
    var keyword = $('#searchBookInput').val();
    $.get(searchBookAPI, {'category': category, 'keyword': keyword}, function (data, status) {
        $container.empty();
        var bookCount = data.length;
        if (bookCount > 0) {
            for (var i = 0; i < bookCount; ++i) {
                addBookBlock(data[i][0], data[i][1], data[i][2], data[i][3] / 100.0);
            }
        }
        else {
            $container.append('No result');
        }
    }).fail(function (xhr, status, error) {
        $container.empty();
        $container.append('No result');
    });
}

$('#searchBookInput').keydown(function (event) {
    if (event.keyCode == 13)
        return false;
});

$('#selectCategory').change(doSearch);

$('#searchBookInput').on('input', function (event) {
    doSearch();
});

$('#detailDialog').on('show.bs.modal', function (event) {
    var detailId = $(event.relatedTarget).data('id');
    $('#bookDetailId').val(detailId);
    $('#bookDetailImage').attr('src', defaultBookImage);
    $('#bookDetailName').text('');
    $('#bookDetailAuthor').text('');
    $('#bookDetailPress').text('');
    $('#bookDetailPrice').text('');
    $('#bookDetailCategories').text('');
    $('#bookDetailDescription').text('');
    $('#detailStatus').removeClass('hidden');
    $('#detailStatus').html(shortProcessingText('Loading book details...', true));
    $('#addQuantity').val(1);
    $('#btnAddToCart').attr('disabled', 'disabled');
    $.get(bookDetailAPI + '?id=' + detailId, function (data, status) {
        $('#bookDetailImage').attr('src', showBookImage(data.image));
        $('#bookDetailName').text(data.name);
        $('#bookDetailAuthor').text('Author：' + (isNotEmptyString(data.author) ? data.author : 'No author info'));
        $('#bookDetailPress').text('Publisher：' + (isNotEmptyString(data.press) ? data.press : 'No publisher info'));
        $('#bookDetailPrice').html('&#65509;' + (data.price / 100.0).toFixed(2));
        $('#bookDetailDescription').text(data.description);
        $('#detailStatus').addClass('hidden');
        $('#bookDetailCategories').text('Category：');
        var categoryCount = data.categories.length;
        if (categoryCount > 0) {
            for (var i = 0; i < categoryCount; ++i)
                $('#bookDetailCategories').append('<span class="label label-success large-label">' + escapeHtml(data.categories[i]) + '</span> ');
        } else {
            $('#bookDetailCategories').append('No category info');
        }
        $('#btnAddToCart').removeAttr('disabled');
    }).fail(function (xhr, status, error) {
        $('#detailStatus').html(shortFailText('Fail to load book details！Error：' + error, true));
    });
});

$('#detailDialog').on('shown.bs.modal', function (event) {
    $('#addQuantity').focus();
});

$('#addQuantity').keydown(function (event) {
    if (event.keyCode == 13)
        return false;
});

$('#btnAddToCart').click(function () {
    $('#btnAddToCart').attr('disabled', 'disabled');
    var bookId = $('#bookDetailId').val();
    var quantity = $('#addQuantity').val();
    var postData = {
        'bookId': bookId,
        'quantity': quantity
    };
    $.post(addToCartAPI, postData, addToCartCallback).fail(function (xhr, status, error) {
        addToCartCallback(tryJSONParse(xhr.responseText), error);
    });
});
