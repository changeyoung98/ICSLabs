<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="../layout/page_head.jsp" />

<jsp:include page="../layout/content_navbar_admin.jsp" />

<div class="container">
    <h1>User Management
        <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#addDialog">
            <span class="glyphicon glyphicon-plus"></span>
        </button>
    </h1>
    <div class="alert alert-info fade in" role="alert"><span class="glyphicon glyphicon-time"></span> Loading user information...</div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>User Name</th>
                    <th>Nickname</th>
                    <th>Balance</th>
                    <th>Role</th>
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
                <h4 class="modal-title" id="addDialogLabel">Add User</h4>
            </div>
            <div class="modal-body">
                <div class="media">
                    <div class="media-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label for="addUserUsername" class="col-sm-2 control-label">User Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="addUserUsername" placeholder="User Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addUserPassword" class="col-sm-2 control-label">Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="addUserPassword" placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addUserPasswordConfirm" class="col-sm-2 control-label">Certificate</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="addUserPasswordConfirm" placeholder="Certificate">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addUserNickname" class="col-sm-2 control-label">Nickanme</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="addUserNickname" placeholder="Nickname">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addUserBalance" class="col-sm-2 control-label">Balance</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <span class="input-group-addon">&#65509;</span>
                                        <input type="text" class="form-control" id="addUserBalance" placeholder="Account Balance">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Role</label>
                                <div class="col-sm-10">
                                    <label class="radio-inline">
                                        <input type="radio" name="addUserRole" id="addUserRole0" value="0" checked="checked"><span class="glyphicon glyphicon-user"></span> Regular User
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="addUserRole" id="addUserRole1" value="1"><span class="glyphicon glyphicon-wrench"></span> Manager
                                    </label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="media-right">
                        <img class="media-object user-image" id="addUserImageShow">
                        <p class="tip-under-image" id="addUserUploadTips"></p>
                        <button type="button" class="btn btn-block btn-primary" id="addUserUploadBtn">Upload User Profile Picture</button>
                        <input type="text" class="form-control hidden" id="addUserAvatar">
                    </div>
                </div>
                <p id="addUserStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btnAddUser">Add</button>
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
                <h4 class="modal-title" id="updateDialogLabel">Modify User Information</h4>
            </div>
            <div class="modal-body">
                <div class="media">
                    <div class="media-body">
                        <form class="form-horizontal">
                            <input type="text" class="form-control hidden" id="updateUserId">
                            <div class="form-group">
                                <label for="updateUserUsername" class="col-sm-2 control-label">User Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="updateUserUsername" placeholder="User Name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateUserPassword" class="col-sm-2 control-label">Password</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="updateUserPassword" placeholder="Reset Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateUserPasswordConfirm" class="col-sm-2 control-label">Certificate</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="updateUserPasswordConfirm" placeholder="Certificate Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateUserNickname" class="col-sm-2 control-label">Nickname</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="updateUserNickname" placeholder="Nickname">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="updateUserBalance" class="col-sm-2 control-label">Balance</label>
                                <div class="col-sm-10">
                                    <div class="input-group">
                                        <span class="input-group-addon">&#65509;</span>
                                        <input type="text" class="form-control" id="updateUserBalance" placeholder="Account Balance">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Role</label>
                                <div class="col-sm-10">
                                    <label class="radio-inline">
                                        <input type="radio" name="updateUserRole" id="updateUserRole0" value="0"><span class="glyphicon glyphicon-user"></span> Regular User
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="updateUserRole" id="updateUserRole1" value="1"><span class="glyphicon glyphicon-wrench"></span> Manager
                                    </label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="media-right">
                        <img class="media-object user-image" id="updateUserImageShow">
                        <p class="tip-under-image" id="updateUserUploadTips"></p>
                        <button type="button" class="btn btn-primary btn-block" id="updateUserUploadBtn">Modify User Profile Picture</button>
                        <button type="button" class="btn btn-danger btn-block" id="deleteUserImageBtn">Delete User Profile Picture</button>
                        <input type="text" class="form-control hidden" id="updateUserAvatar">
                    </div>
                </div>
                <hr>
                <div class="row" id="timeDetailText"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" id="btnUpdateUser">Update</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="addressDialog" tabindex="-1" role="dialog" aria-labelledby="addressDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="addressDialogLabel">
                    <span id="addressDialogLabelText">Manage Delivery Address</span>
                    <button type="button" class="btn btn-success btn-sm" id="btnAddAddress">
                        <span class="glyphicon glyphicon-plus"></span>
                    </button>
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="addressForm">
                    <input type="text" class="form-control hidden" id="addressUserId">
                </form>
                <p id="addressStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-warning" id="btnUpdateAddress">Save</button>
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
                <h4 class="modal-title" id="deleteDialogLabel">Delete User</h4>
            </div>
            <div class="modal-body">
                <p id="deleteText"></p>
                <input type="text" class="form-control hidden" id="deleteUserId">
                <p id="deleteUserStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btnDeleteUser">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../profile.jsp" />

<jsp:include page="../layout/content_footer.jsp" />

<jsp:include page="../layout/common_js.jsp" />

<script src="<s:url value="/js/admin/user.js"/>"></script>
<script src="<s:url value="/js/profile.js"/>"></script>

<jsp:include page="../layout/page_end.jsp" />

