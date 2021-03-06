<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/css/style.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/wbox/wbox/wbox.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/jquery.autocomplete.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/bootstrap-3.3.7/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/bootstrap-3.3.7/css/bootstrap-theme.min.css" />
<script type="text/javascript" src="${ctx}/styles/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/wbox/wbox.js"></script>
<script type="text/javascript" src="${ctx}/styles/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/styles/bootstrap-3.3.7/js/bootstrap.min.js" />
<script type="text/javascript" charset="UTF-8">
    $(document).ready(function() {
        var _action = $('#inputForm').prop('action');
        _action = _action.split('${ctx}/')[1].replace('/update','') + '/' + $('#id').val();
        $.extend($.validationEngineLanguage.allRules,{"ajaxCodeCheck": {
            "url": "${ctx}/wfc/common/check",
            "extraData": "dt=" + _action,
            "alertText": "* &#x8FD9;&#x4E2A;&#x7F16;&#x53F7;&#x5DF2;&#x88AB;&#x4F7F;&#x7528;",
            "alertTextLoad": "* &#x6B63;&#x5728;&#x786E;&#x8BA4;&#x7F16;&#x53F7;&#x662F;&#x5426;&#x5DF2;&#x88AB;&#x4F7F;&#x7528;&#xFF0C;&#x8BF7;&#x7A0D;&#x7B49;&#x3002;"}
        });
        //$('#inputForm').validationEngine();

        $('input[displayMode=auto]').blur(function () {
            if('${process.name}'.indexOf('flowDms_') > -1) {
                $.getJSON('${ctx}/file/get',{'fileCode':$(this).val()},function (data) {
                    for (var i = 0; i < data.size; i++) {
                        var _file = data.data[i];
                        $('[name=' + _file.key + ']').val( _file.value);
                    }
                });
            }
        });
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
</script>