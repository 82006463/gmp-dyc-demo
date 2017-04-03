<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/css/style.css" media="all" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" />
<script type="text/javascript" src="${ctx}/styles/js/jquery-1.8.3.min.js"></script>
<script src="${ctx}/styles/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/styles/plugin/js/jquery.validationEngine.js" type="text/javascript"></script>
<script src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" charset="UTF-8">
    $(function() {
        var _action = $('#inputForm').prop('action');
        _action = _action.split('${ctx}/')[1].replace('/update','') + '/' + $('#id').val();
        $.extend($.validationEngineLanguage.allRules,{"ajaxCodeCheck": {
            "url": "${ctx}/wfc/common/check",
            "extraData": "dt=" + _action,
            "alertText": "* &#x8FD9;&#x4E2A;&#x7F16;&#x53F7;&#x5DF2;&#x88AB;&#x4F7F;&#x7528;",
            "alertTextLoad": "* &#x6B63;&#x5728;&#x786E;&#x8BA4;&#x7F16;&#x53F7;&#x662F;&#x5426;&#x5DF2;&#x88AB;&#x4F7F;&#x7528;&#xFF0C;&#x8BF7;&#x7A0D;&#x7B49;&#x3002;"}
        });
        //$('#inputForm').validationEngine();
    });

    function submitForm() {
        if ($('#inputForm').validationEngine('validate')) {
            $('#inputForm').submit();
            return true;
        }
        return false;
    }

    var Ops = {};
    Ops.up = function (thisTag) {
        var _tr = $(thisTag).parent().parent();
        _tr.prev().before(_tr);
        return false;
    }
    Ops.down = function (thisTag) {
        var _tr = $(thisTag).parent().parent();
        _tr.next().after(_tr);
        return false;
    }
</script>