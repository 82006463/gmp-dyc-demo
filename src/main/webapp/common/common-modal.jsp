<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">电子签名</h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <span class="input-group-addon">账号</span>
                    <input type="text" id="base_username" name="base_username" value="" class="form-control">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">密码</span>
                    <input type="text" id="base_password" name="base_password" value="" class="form-control">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">意见</span>
                    <textarea rows="5" id="base_reason" name="base_reason" class="form-control"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="modal-close">关闭</button>
                <button type="button" class="btn btn-primary" onclick="return Ops.checkUser();">提交</button>
                <button type="submit" id="modal-submit" style="display: none;"></button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div>