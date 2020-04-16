<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#if post_id??>
        <#assign p = c.findBy(posts "id" post_id)>
        <#if p?? && p != "null">
            <@c.post p false/>
        <#else>
            <h1>No such post</h1>
        </#if>
    <#else>
            <h1>No such post</h1>
    </#if>
</@c.page>
