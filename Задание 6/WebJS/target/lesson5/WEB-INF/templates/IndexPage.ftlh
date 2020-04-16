<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#-- @ftlvariable name="usersList" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <link rel="stylesheet" type="text/css" href="../../css/post.css">
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body"></div>
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
                        <span class="creationTime"></span>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            <article>
                No data
            </article>
        </div>
    </div>

    <script>
        $(function () {
            var $noData = $(".articles .noData");
            $noData.find("article").text("Loading...");
            ajax({
                url: "/index",
                data: {
                    action: "findAll"
                },
                success: function (response) {
                    response["articles"].forEach(function (article) {
                        $noData.hide();
                        var $article = $($(".articles template").prop("content")).clone();
                        $article.find(".title").text(article["title"]);
                        var login = "";
                        response["usersList"].forEach(function (user) {
                            if (user["id"] === article["userId"]) {
                                login = user["login"];
                            }
                        });
                        $article.find(".information").text("By " + login);
                        $article.find(".body").text(article["text"]);
                        $article.find(".creationTime").text(article["creationTime"]);
                        $(".articles").append($article);
                    })
                }
            });
        })
    </script>
</@c.page>
