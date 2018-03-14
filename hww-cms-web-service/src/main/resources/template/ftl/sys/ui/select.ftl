<#macro select
value="" id="" name="" 
>
<select<#rt/>
 <#if id!=""> id="${id}"</#if><#rt/>
 <#if name!=""> name="${name}"</#if><#rt/>>
<option value=""<#if value==""> selected="selected"</#if>>请选择</option><#t/>
<option value="0"<#if value=="0"> selected="selected"</#if>>未激活</option><#t/>
<option value="1"<#if value=="1"> selected="selected"</#if>>已激活</option><#t/>
<option value="-1"<#if value=="-1"> selected="selected"</#if>>已删除</option><#t/>
</select>
</#macro>