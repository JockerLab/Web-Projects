<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="article_id"></td>
                    <td class="article_title"></td>
                    <td class="hidden"><input class="button" type="button"></td>
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
            var $noData = $(".articles .noData");

            ajax({
                data: {
                    action: "findMyArticles"
                },
                success: function (response) {
                    response["myArticles"].forEach(function (article) {
                        $noData.hide();
                        var $tr = $($(".articles template").prop("content")).clone();
                        $tr.find(".article_id").text(article["id"]);
                        var title = article["title"];
                        if (title.length > 70) {
                            title = title.substring(0, 70) + "...";
                        }
                        $tr.find(".article_title").text(title);
                        $tr.find(".hidden .button").attr("name", article["id"]);
                        var value = "";
                        if (article["hidden"] === true) {
                            value = "Show";
                        } else {
                            value = "Hide";
                        }
                        $tr.find(".hidden .button").attr("value", value);
                        $(".articles tbody").append($tr);
                    });
                    $(".articles .hidden .button").click(function () {
                        if ($(this).attr("value") === "Show") {
                            $(this).attr("value", "Hide");
                        } else {
                            $(this).attr("value", "Show");
                        }
                        ajax({
                            data: {
                                action: "changeHidden",
                                articleId: $(this).attr("name")
                            },
                            success: function (response) {
                                var hide = response["currentHide"];
                                $(this).attr("name", hide);
                            }
                        });
                        return false;
                    });
                }
            });
        })
    </script>
</@c.page>
