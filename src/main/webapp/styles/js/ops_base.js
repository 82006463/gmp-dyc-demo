var Ops = {};

Ops.submit = function () {
    if ($('#inputForm').validationEngine('validate')) {
        return true;
    }
    return false;
}

Ops.up = function (thisTag) {
    var _tr = $(thisTag).parent().parent();
    if(_tr.prevAll().length>1) {
        _tr.prev().before(_tr);
    }
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
    _tr.find('td:eq(' + (_tr.find('td').length - 1) + ')').append("<a class='btnDel' onclick='return Ops.removeTr(this,1);'></a><a onclick='return Ops.up(this);' title='&#x4E0A;&#x79FB;'>&#x4E0A;</a><a onclick='return Ops.down(this);' title='&#x4E0B;&#x79FB;'>&#x4E0B;</a>");
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

//设置Select标签对应的隐藏域
Ops.setVal = function (selectTag) {
    $('[name=' + $(selectTag).prop('name') + '_val]').val($(selectTag).find('option:selected').text());
}