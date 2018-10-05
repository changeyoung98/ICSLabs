<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<jsp:include page="../layout/page_head.jsp" />

<jsp:include page="../layout/content_navbar_admin.jsp" />

<div class="container">
    <h1>Order Query</h1>
    <div class="alert alert-info fade in" role="alert"><span class="glyphicon glyphicon-time"></span> Loading Order Information...</div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User</th>
                    <th>Number of Items</th>
                    <th>Total Amount</th>
                    <th>Total Price</th>
                    <th>State</th>
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
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="detailDialogLabel">Order Details</h4>
            </div>
            <div class="modal-body">
                <div class="row" id="orderSummaryText"></div>
                <hr>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Item ID</th>
                                <th>Book</th>
                                <th>Price</th>
                                <th>Amount</th>
                                <th>Total</th>
                                <th>Time of Creation</th>
                                <th>Time of Modification</th>
                            </tr>
                        </thead>
                        <tbody id="detailTable">
                        <!-- Table data will be loaded by js. -->
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../profile.jsp" />

<jsp:include page="../layout/content_footer.jsp" />

<jsp:include page="../layout/common_js.jsp" />

<script src="<s:url value="/js/admin/order.js"/>"></script>
<script src="<s:url value="/js/profile.js"/>"></script>

<jsp:include page="../layout/page_end.jsp" />
