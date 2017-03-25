<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<link type="text/css" rel="stylesheet" href="${ctx}/styles/css/style.css" media="all" />
<link type="text/css" rel="stylesheet" href="${ctx}/styles/plugin/css/validationEngine.jquery.css" />
<script type="text/javascript" src="${ctx}/styles/js/jquery-1.8.3.min.js"></script>
<script src="${ctx}/styles/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/styles/plugin/js/jquery.validationEngine.js" type="text/javascript"></script>
<script src="${ctx}/styles/plugin/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function() {
        $('#inputForm').validationEngine();
    });

    function submitForm() {
        if($('#inputForm').validationEngine('validate')) {
            $('#inputForm').submit();
        }
        return false;
    }
</script>