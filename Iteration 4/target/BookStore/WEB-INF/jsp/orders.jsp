<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="./layout/page_head.jsp" />

<jsp:include page="./layout/content_navbar_other.jsp" />

<div class="container">
    <h1>My History Orders</h1>
    <div class="alert alert-info fade in" role="alert"><span class="glyphicon glyphicon-time"></span> Loading Order info...</div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Items number</th>
                    <th>Total amount</th>
                    <th>Total Price</th>
                    <th>Time of Creation</th>
                    <th>Time of Modification</th>
                    <th>Details</th>
                </tr>
            </thead>
            <tbody id="mainTable">
            <!-- Table data will be loaded by js. -->
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="detailDialog" tabindex="-1" role="dialog" aria-labelledby="detailDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="detailDialogLabel">Order Details</h4>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Book</th>
                                <th>Price</th>
                                <th>Amount</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody id="detailTable">
                        <!-- Table data will be loaded by js. -->
                        </tbody>
                    </table>
                </div>
                <p id="detailStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Return</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="deleteDialog" tabindex="-1" role="dialog" aria-labelledby="deleteDialogLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteDialogLabel">Delete Record</h4>
            </div>
            <div class="modal-body">
                <p id="deleteText">Are you sure to delete this record？</p>
                <input type="text" class="form-control hidden" id="deleteOrderId">
                <p id="deleteStatus"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" id="btnDeleteOrder">Delete</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="./profile.jsp" />

<jsp:include page="./layout/content_footer.jsp" />

<jsp:include page="./layout/common_js.jsp" />

<script src="<s:url value="/js/user.js"/>"></script>
<script src="<s:url value="/js/orders.js"/>"></script>
<script src="<s:url value="/js/profile.js"/>"></script>

<jsp:include page="./layout/page_end.jsp" />
