<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=post/>
    <#if user??>
        <div class="form">
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="post"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Comment</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Send">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <#if post.comments??>
        <#list post.comments?reverse as comment>
            <div class="comment">
                <div class="information">${comment.user.login}</div>
                <div class="body">
                    ${comment.text?no_esc}
                </div>
            </div>
        </#list>
    </#if>
</@c.page>
