<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="../layout/page_head.jsp" />

<jsp:include page="../layout/content_navbar_admin.jsp" />

<div class="container">
    <h1>Book Management
        <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addDialog">
            <span class="glyphicon glyphicon-plus"></span>
        </button>
    </h1>
    <div class="alert alert-info fade in" role="alert"><span class="glyphicon glyphicon-time"></span> Loading book details...</div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Book ID</th>
                    <th>Book Name</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Time of creation</th>
                    <th>Time of modification</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
            <!-- Table data will be loaded by js. -->
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="addDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addDialogLabel">Add Books</h4>
            </div>
            <div class="modal-body">
                <div class="media">
                    <div class="media-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label for="addBookName" class="col-sm-2 control-label">Book Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="addBookName" placeholder="Book Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addBookAuthor" class="col-sm-2 control-label">Author</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="addBookAuthor" placeholder="Author">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addBookPress" class="col-sm-2 control-label">Publisher</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="addBookPress" placeholder="Publisher">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addBookPrice" class="col-sm-2 control-label">Price</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <span class="input-group-addon">&#65509;</span>
                                        <input type="text" class="form-control" id="addBookPrice" placeholder="Price">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addBookStock" class="col-sm-2 control-label">Stock</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="addBookStock" placeholder="Stock">
                                        <span class="input-group-addon"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Category</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static"><span class="glyphicon glyphicon-info-sign"></span> Please go to "Category Management" to add category information for this book. </p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="media-right">
                        <img class="media-object book-image" id="addBookImageShow">
                        <p class="tip-under-image" id="addBookUploadTips"></p>
                        <button type="button" class="btn btn-block btn-primary" id="addBookUploadBtn">Upload Picture of the Book</button>
                        <input type="text" class="form-control hidden" id="addBookImage">
                    </div>
                </div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea class="form-control" rows="5" id="addBookDescription" placeholder="Brief Introduction of the Book"></textarea>
                        </div>
                    </div>
                </form>
                <p id="addBookStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btnAddBook">Add</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="updateDialog" tabindex="-1" role="dialog" aria-labelledby="updateDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="updateDialogLabel">Book Details</h4>
            </div>
            <div class="modal-body">
                <div class="media">
                    <div class="media-body">
                        <form class="form-horizontal">
                            <input type="text" class="form-control hidden" id="updateBookId">
                            <div class="form-group">
                                <label for="updateBookName" class="col-sm-2 control-label">Book Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="updateBookName" placeholder="Book Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateBookAuthor" class="col-sm-2 control-label">Author</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="updateBookAuthor" placeholder="Author">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateBookPress" class="col-sm-2 control-label">Publisher</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="updateBookPress" placeholder="Publisher">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateBookPrice" class="col-sm-2 control-label">Price</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <span class="input-group-addon">&#65509;</span>
                                        <input type="text" class="form-control" id="updateBookPrice" placeholder="Price">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateBookStock" class="col-sm-2 control-label">Stock</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="updateBookStock" placeholder="Stock">
                                        <span class="input-group-addon"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Category</label>
                                <div class="col-sm-10">
                                    <p class="form-control-static" id="bookCategories"></p>
                                </div>
                            </div>
                            <p><span class="glyphicon glyphicon-info-sign"></span> Please go to "Category Management" to add category information for this book.</p>
                        </form>
                    </div>
                    <div class="media-right">
                        <img class="media-object book-image" id="updateBookImageShow">
                        <p class="tip-under-image" id="updateBookUploadTips"></p>
                        <button type="button" class="btn btn-primary btn-block" id="updateBookUploadBtn">Change Picture of the Book</button>
                        <button type="button" class="btn btn-danger btn-block" id="deleteBookImageBtn">Delete Picture of the Book</button>
                        <input type="text" class="form-control hidden" id="updateBookImage">
                    </div>
                </div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea class="form-control" rows="5" id="updateBookDescription" placeholder="Brief Introduction of the Book"></textarea>
                        </div>
                    </div>
                </form>
                <div class="row" id="timeDetailText"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" id="btnUpdateBook">Save</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="deleteDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteDialogLabel">Delete Book</h4>
            </div>
            <div class="modal-body">
                <p id="deleteText"></p>
                <input type="text" class="form-control hidden" id="deleteBookId">
                <p id="deleteBookStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btnDeleteBook">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../profile.jsp" />

<jsp:include page="../layout/content_footer.jsp" />

<jsp:include page="../layout/common_js.jsp" />

<script src="<s:url value="/js/admin/book.js"/>"></script>
<script src="<s:url value="/js/profile.js"/>"></script>

<jsp:include page="../layout/page_end.jsp" />

