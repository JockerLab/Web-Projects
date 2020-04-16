<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="currentUser" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th class="admin">Admin</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="user_admin"><p style="margin: 0.1rem"></p><a href="javascript:void(0);"></a></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $admin = $(".users .admin");
            var $noData = $(".users .noData");
            ajax({
                data: {
                    action: "findAll"
                },
                success: function(response) {
                    $noData.hide();
                    response["users"].forEach(function(user) {
                        var $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        if (response["currentUser"] == null || response["currentUser"]["admin"] === false) {
                            $admin.hide();
                            $tr.find(".user_admin").hide();
                        }
                        if (user["admin"] === true) {
                            $tr.find(".user_admin p").text("true");
                            $tr.find(".user_admin a").text("disable");
                        } else {
                            $tr.find(".user_admin p").text("false");
                            $tr.find(".user_admin a").text("enable");
                        }
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find("tr a").attr("data-userId", user["id"]);
                        $tr.find("tr .user_id").attr("data-userId", user["id"]);
                        $tr.find("tr .user_login").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".user_id, .user_login").click(function() {
                        ajax({
                            data: {
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            success: function (response) {
                                notify(response["user"].creationTime);
                            }
                        })
                    });
                    $(".users .user_admin a").click(function() {
                        if ($(this).text() === "enable") {
                            $(this).parent().find("p").text("true");
                            $(this).text("disable");
                            $(this).attr("value", true);
                        } else {
                            $(this).parent().find("p").text("false");
                            $(this).text("enable");
                            $(this).attr("value", false);
                        }
                        ajax({
                            data: {
                                action: "setAdmin",
                                userId: $(this).attr("data-userId"),
                                value: $(this).attr("value")
                            },
                            success: function (responseLink) {
                                if (responseLink["usersRedirect"]) {
                                    location.href = "/users";
                                }
                            }
                        });
                    });
                }
            });
        })
    </script>
</@c.page>
