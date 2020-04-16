<#-- @ftlvariable name="viewedUser" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <#if viewedUser??>
            <div class="caption">User</div>
            <table>
                <tbody>
                    <tr>
                        <td class="id">${viewedUser.id}</td>
                        <td class="login">${viewedUser.login}</td>
                        <td class="creationTime">${viewedUser.creationTime}</td>
                        <td class="disabled">
                            <#if viewedUser.disabled == true>
                                true
                            <#else>
                                false
                            </#if>
                        </td>
                    </tr>
                </tbody>
            </table>
        <#else>
            <h1>No such user</h1>
        </#if>
    </div>
</@c.page>
