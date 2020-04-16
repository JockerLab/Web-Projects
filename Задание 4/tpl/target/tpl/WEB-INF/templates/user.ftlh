<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "common.ftl" as c/>

<@c.page>
    <#if handle??>
        <#assign viewedUser=c.findBy(users, "handle", handle)!/>
        <#assign key = "handle">
        <#assign value = handle>
    </#if>
    <#if user_id??>
        <#assign viewedUser=c.findBy(users, "id", user_id)!/>
        <#assign key = "id">
        <#assign value = user_id>
    </#if>
    <#if (handle?? || user_id??) && viewedUser.id??>
        <div class="data">
            <@c.userlink user=viewedUser nameOnly=false/>
            <ul>
                <li>id: ${viewedUser.id}</li>
                <li>handle: ${viewedUser.handle}</li>
                <li>name: ${viewedUser.name}</li>
                <li><a href="/posts?user_id=${viewedUser.id}">posts</a>: ${c.countPosts(viewedUser)}</li>
            </ul>
            <img src="/img/user.jpg" alt="User Photo" title="UserPhoto"/>
        </div>
        <div class = "arrows">
            <#assign prev = c.findPrev(users, key, value)>
            <#if prev != "">
                <a href="/user?handle=${prev.handle}">←</a>
            <#else>
                <p>←</p>
            </#if>
            <#assign next = c.findPrev(users?reverse, key, value)>
            <#if next != "">
                <a href="/user?handle=${next.handle}">→</a>
            <#else>
                <p>→</p>
            </#if>
        </div>
    <#else>
        <h1>No such user</h1>
    </#if>
</@c.page>
