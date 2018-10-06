<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<div class="modal fade" id="registerDialog" tabindex="-1" role="dialog" aria-labelledby="registerDialogLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="registerDialogLabel">Register</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" id="registerUsername" placeholder="User name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" class="form-control" id="registerPassword" placeholder="Password">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" class="form-control" id="registerPasswordConfirm" placeholder="Certificate">
                        </div>
                    </div>
                </form>
                <p id="registerStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" id="btnRegister">Register</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Return</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="loginDialog" tabindex="-1" role="dialog" aria-labelledby="loginDialogLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="loginDialogLabel">Login</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="text" class="form-control" id="loginUsername" placeholder="User name">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-12">
                            <input type="password" class="form-control" id="loginPassword" placeholder="Password">
                        </div>
                    </div>
                </form>
                <p id="loginStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnLogin">Login</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Return</button>
            </div>
        </div>
    </div>
</div>