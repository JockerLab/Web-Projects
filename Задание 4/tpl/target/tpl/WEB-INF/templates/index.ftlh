<#import "common.ftl" as c/>

<@c.page>
<ul style="list-style-type: none">
        <#list posts?reverse as p>
        <li>
                <@c.post p true/>
        </li>
        </#list>
</ul>
</@c.page>
