<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <ul style="list-style-type: none">
        <#if user_id??>
            <#list posts?reverse as p>
                <#if p.user_id == user_id>
                    <#assign foundUser = true>
                    <li>
                        <@c.post p true/>
                    </li>
                </#if>
            </#list>
            <#if foundUser?? && foundUser == true>
                <#else>
                <h1>No such user</h1>
            </#if>
        <#else>
            <h1>No such user</h1>
        </#if>
    </ul>
</@c.page>
