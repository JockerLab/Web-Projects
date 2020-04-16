<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Enable/Disable</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login"><a href="/user/${user.id}">${user.login}</a></td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="disabled">
                        <form method="post" action="">
                            <input type="hidden" name="action" value="disable"/>
                            <input type="hidden" id="userId" name="userId" value="${user.id}"/>
                            <#if user.disabled == true>
                                <input type="submit" id="disable" name="disable" value="Enable">
                            <#else>
                                <input type="submit" id="disable" name="disable" value="Disable">
                            </#if>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
