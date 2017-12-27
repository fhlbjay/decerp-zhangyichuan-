<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="ui_tb_h30">
    <div class="ui_flt" style="height: 30px; line-height: 30px;">
        共有
        <span class="ui_txt_bold04">${result.totalCount}</span>
        条记录，当前第
        <span class="ui_txt_bold04">${result.currentPage}/${result.totalPage}</span>
        页
    </div>
    <div class="ui_frt">
        <input type="button" value="首页" class="ui_input_btn01 btn_page" data-page="1"/>
        <input type="button" value="上一页" class="ui_input_btn01 btn_page" data-page="${result.prevPage}"/>
        <input type="button" value="下一页" class="ui_input_btn01 btn_page" data-page="${result.nextPage}"/>
        <input type="button" value="尾页" class="ui_input_btn01 btn_page" data-page="${result.totalPage}"/>

        <select name="pageSize" class="ui_select02">
            <option value="3" ${result.pageSize == 3 ? "selected='selected'":""} >3</option>
            <option value="5" ${result.pageSize == 5 ? "selected='selected'":""} >5</option>
            <option value="10" ${result.pageSize == 10 ? "selected='selected'":""} >10</option>
        </select>
        转到第<input type="text" name="currentPage" value="${qo.currentPage}" class="ui_input_txt01"/>页
        <input type="submit" class="ui_input_btn01" value="跳转"/>
    </div>
</div>
</div>