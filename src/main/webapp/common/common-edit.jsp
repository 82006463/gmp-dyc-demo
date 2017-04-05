<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/css/style.css" media="all" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/jquery.autocomplete.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" />
<script type="text/javascript" src="${ctx}/styles/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js"></script>
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

        //模糊搜索
        $('input[fuzzy]').each(function () {
            var _fuzzy = $(this).attr('fuzzy');
            if(!_fuzzy) {
                _fuzzy = $(this).prop('fuzzy');
            }
            if(_fuzzy) {
                var _param = {};
                _param.code = _fuzzy;
                $(this).AutoComplete({
                    'data': "${ctx}/wfc/common/select",
                    'ajaxDataType': 'json',
                    'ajaxParams': _param,
                    'width': 'auto',
                    'maxHeight': 300,
                    'maxItems': 20,
                    'async': true
                }).AutoComplete('show');
            }
        });
    });

    var Ops = {};
    Ops.submit = function () {
        if ($('#inputForm').validationEngine('validate')) {
            return true;
        }
        return false;
    }

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

    //添加表中的行
    Ops.addTr = function (thisObj) {
        var _table = $(thisObj).parent().parent().parent().parent();
        var _tr = _table.find('tr:eq(1)').clone(true);
        _tr.find(':input').removeAttr('readonly').val('');
        _tr.find('td:eq(' + (_tr.find('td').length - 1) + ')').append('<a class="btnDel" onclick="return Ops.removeTr(this,1);"></a>');
        _table.append(_tr);
        return false;
    }

    //删除tr1
    Ops.removeTr = function (thisObj, level) {
        var _parent = $(thisObj).parent();
        for(var i=0; i<level; i++) {
            _parent = _parent.parent();
        }
        _parent.remove();
        return false;
    }
</script>