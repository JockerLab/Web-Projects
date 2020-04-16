<#macro header>
<header>
    <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            <@userlink user=user nameOnly=true/>
            |
            <a href="#">Logout</a>
        <#else>
            <a href="#">Enter</a>
            |
            <a href="#">Register</a>
        </#if>
    </div>
    <nav>
        <ul>
            <@headerLink "/index" "Index"/>
            <@headerLink "/misc/help" "Help"/>
            <@headerLink "/users" "Users"/>
        </ul>
    </nav>
</header>
</#macro>

<#macro headerLink link text>
    <#if pageUri == link>
        <li><a href="${link}" style="border-bottom: 2px solid #3B5998">${text}</a></li>
    <#else>
        <li><a href="${link}">${text}</a></li>
    </#if>
</#macro>

<#macro sidebar>
<aside>
    <#list posts as p>
    <section>
        <div class="header">Post #${p.id?c}</div>
        <div class="body">
            <#if p.text?length gt 250>
                <p>${p.text[0..250]}...</p>
            <#else>
                <p>${p.text}</p>
            </#if>
        </div>
        <div class="footer"><a href="/post?post_id=${p.id?c}">View all</a></div>
    </section>
    </#list>
</aside>
</#macro>

<#macro footer>
<footer>
    <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
</footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly == true>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <link rel="stylesheet" type="text/css" href="/css/handle.css">
        <a class="handle" href="/user?handle=${user.handle}" style="color: ${user.color}">${user.handle}</a>
    </#if>
</#macro>

<#macro post p short>
    <link rel="stylesheet" type="text/css" href="/css/post.css">
    <article>
        <div class="title"><a href="/post?post_id=${p.id?c}">${p.title}</a></div>
        <#assign u = findBy(users, "id", p.user_id)>
        <div class="information">By <@userlink u false/></div>
        <div class="body">
            <#if short == true>
                <#if p.text?length gt 250>
                    <p>${p.text[0..250]}...</p>
                <#else>
                    <p>${p.text}</p>
                </#if>
            <#else>
                <p>${p.text}</p>
            </#if>
        </div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="/css/normalize.css">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/css/datatable.css">
    <link rel="stylesheet" type="text/css" href="/css/user.css">
</head>
<body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
</body>
</html>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findPrev items key id>
    <#assign last = "">
    <#list items as item>
        <#if item[key]==id>
            <#return last/>
        </#if>
        <#assign last = item/>
    </#list>
</#function>

<#function countPosts u>
    <#assign cnt = 0>
    <#list posts as p>
        <#if p.user_id==u.id>
            <#assign cnt = cnt + 1>
        </#if>
    </#list>
    <#return cnt>
</#function>